package Pack;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.util.ArrayList;
import java.util.function.Function;

public class SemanticAnalysis {
    private SymbolTable symbolTable;
    private boolean has_error;
    private String scope;
    private String current_id;
    private String current_block;
    private boolean returnProc;
    private int for_counter;
    private int currentDirection = 0;

    public SemanticAnalysis(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
        has_error= false;
        scope = "";
        returnProc = false;
        for_counter=0;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void Traverse(Nodo nodo, String scopeActual){
        scope = scopeActual;
        int prevOffset;
        if(!scopeActual.equals("")){
            symbolTable.activateWithScope(scopeActual);//llamar antes de entrar a a la tabla
        }
        switch (nodo.getNombre()){
            case "RETURN_STATEMENT":
                validateOut(nodo, scopeActual);
            break;
            case "VARIABLE_DECLARATION":
                addVariableDeclaration(nodo, scopeActual);
            break;
            case "Inicio":
                prevOffset = currentDirection;
                addFunctionBlock(nodo, "");
                currentDirection = prevOffset;
            break;
            case "PUT":
                typeValidation(nodo, scopeActual);
                validateOut(nodo, scopeActual);
            break;
            case "GET":
                typeValidation(nodo, scopeActual);
                validateOut(nodo, scopeActual);
            break;
            case "Call_Subroutine":
                SymbolTableNode tmpNode = symbolTable.findActiveSymbol(nodo.getHijos().get(0).getValor());
                if (tmpNode != null) {
                    if (!(tmpNode instanceof FunctionTableNode)) {
                        System.out.println(scopeActual+":"+nodo.getHijos().get(0).getFila()+":"+nodo.getHijos().get(0).getColumna()+":"+ " El identificador usado no es de funcion");
                    }
                }else{
                    System.out.println(scopeActual+":"+nodo.getHijos().get(0).getFila()+":"+nodo.getHijos().get(0).getColumna()+":"+ " El identificador usado no existe");
                }
                validateOut(nodo, scopeActual);
            break;
            case "FUNCTION_BLOCK":
            case "PROCEDURE_BLOCK":
                prevOffset = currentDirection;
                addFunctionBlock(nodo, scopeActual);
                currentDirection = prevOffset;
            break;
            case "FOR_BLOCK":
                typeValidation(nodo, scope);
                Nodo tmp = new Nodo("");
                tmp.addHijo(nodo.getHijos().get(1));
                tmp.addHijo(nodo.getHijos().get(2));
                typeValidation(tmp, scope);
            break;
            case "ASSIGNMENT":
                validateIn(nodo, scopeActual);
                Nodo valNode = new Nodo("");
                valNode.addHijo(nodo.getHijos().get(1));
                validateOut(valNode, scopeActual);
                case "BooleanExp":
                typeValidation(nodo, scopeActual);
            break;
            default:
                for (Nodo hijos : nodo.getHijos()) {
                    Traverse(hijos, scopeActual);
                }
            break;
        }
    }

    public void validateExit(Nodo nodo, String scopeActual){
        for ( Nodo hijo : nodo.getHijos()) {
            switch (hijo.getNombre()){
                case "EXIT_CYCLE":
                    System.out.println("Exit when solo puede ser utilizado dentro de un ciclo loop");
                    has_error=true;
                break;
                case "LOOP_BLOCK":
                    validateLoop(hijo, scopeActual);
                break;
                default:
                    validateExit(hijo, scopeActual);
                break;
            }
        }
    }

    public void validateLoop(Nodo nodo, String scopeActual){
        Boolean exitFlag=false;
        for (Nodo hijo : nodo.getHijos().get(0).getHijos()) {
            switch (hijo.getNombre()) {
                case "EXIT_CYCLE":
                    exitFlag=true;
                    break;
                default:
                    Nodo n = new Nodo("");
                    n.addHijo(hijo);
                    validateExit(n, scopeActual);
                    break;
            }
        }
        if (!exitFlag) {
            has_error=true;
            System.out.println("Bloque loop debe contener instruccion Exit when "+ scopeActual);
        }
    }

    // Itera sobre el nodo VARIABLE_DECLARATION para agregar id con sus tipos
    public void addVariableDeclaration(Nodo nodo, String scopeActual){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //Se debe iterar todos menos el ultimo tipo, debido a que no es un id 

        for (int i = 0; i <= nodo.getHijos().size()-2; i++) {
            if(tipo.equals("Integer") || tipo.equals("Float")){
                currentDirection += 4;
            }
            else if (tipo.equals("Boolean")){
                currentDirection += 1;
            }
            Nodo actualNode = nodo.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), scopeActual, tipo, currentDirection, 0);//0 indica que no es un parametro
            System.out.println("Agregada variable " + actualNode.getValor() + " con offset de: " + currentDirection);
            //agrega variable a lista de simbolos, retorna true si se pudo, false si ya existia
            if (!this.symbolTable.addSymbol(tmpvnode)) {
                //System.out.println("El identificador \""+tmpvnode.Id+"\" en el scope "+tmpvnode.Scope+" ya esta declarado");
                has_error=true;
                System.out.println(scopeActual+":"+actualNode.getFila()+":"+actualNode.getColumna()+":"+ " El identificador \""+tmpvnode.Id+"\" ya esta declarado");
            }
        }
    }
    
    

    public void addFunctionBlock(Nodo nodo, String scopeActual){
        String id=""; 
        String returnType="void";// si es un procedure el tipo de valor de retorno es void
        Nodo parametros=null;
        Nodo declarations=null;
        //Nodo id_list = null;
        FunctionTableNode tmpfnode=null;
        String scopeHijos = "Missing ID";
        for (Nodo hijo : nodo.getHijos()) {
            switch(hijo.getNombre()){
                case "ID":
                    //el id se usa para 
                    if(nodo.getNombre().equals("Inicio")){
                        scope = hijo.getValor();//scope raiz
                        scopeHijos = hijo.getValor();
                    }
                    else{
                        id = hijo.getValor();
                        scopeHijos = scopeActual +"."+ hijo.getValor();
                    }
                    break;
                case "RETURN_TYPE": //solo en FUNCTION_BLOCK
                    returnType = hijo.getValor();
                    break;
                case "MAIN_PARAMETERS":
                    parametros = hijo;
                break;
                case "DECLARATIONS":    
                    declarations=hijo;
                break;
                case "FINAL_ID":
                    if (!id.equals(hijo.getValor())) {
                        //System.out.println("Las Funciones y Procedimientos deben de tener el mismo nombre al final: " + scopeHijos);
                        System.out.println(scopeHijos+":"+hijo.getFila()+":"+hijo.getColumna()+":"+ " Las Funciones y Procedimientos deben de tener el mismo nombre al final");
                        has_error= true;
                    }
                break;
                case "CONTENT":
                    currentDirection = 0;
                    tmpfnode = new FunctionTableNode(nodo.getHijos().get(0).getValor(),scopeActual, returnType, scopeHijos);//fila tipo funcion
                    if (!this.symbolTable.addSymbol(tmpfnode)) {
                        has_error=true;
                        //System.out.println("la funcion \""+tmpfnode.Id+"\" en el scope "+tmpfnode.Scope+" ya esta declarada");
                        System.out.println(scopeHijos+":"+nodo.getHijos().get(0).getFila()+":"+nodo.getHijos().get(0).getColumna()+":"+ "la funcion \""+tmpfnode.Id+"\" ya esta declarada");
                    }
                    if(parametros!=null){
                        for (int i = 0; i < parametros.getHijos().size(); i++) {
                            Nodo actualNode = parametros.getHijos().get(i);//EVERY PARAMETER
                            AddParameters(tmpfnode,actualNode,scopeHijos);
                        }
                    }
                    if(declarations!=null){
                        Traverse(declarations, scopeHijos);
                    }
                    Traverse(hijo, scopeHijos);
                    validateReturn(hijo,scopeHijos, returnType);
                    validateExit(nodo, scopeHijos);
                    if(!returnProc && !returnType.equals("void")){
                        //System.out.println("Las funciones deben de tener return " + scopeActual);
                        has_error=true;
                        System.out.println(scopeHijos+":"+nodo.getHijos().get(0).getFila()+":"+nodo.getHijos().get(0).getColumna()+":"+ "Las funciones deben de tener return ");
                    }
                    returnProc = false;
                break;
            }
        }
    }
    
    public void AddParameters(FunctionTableNode tmpfnode, Nodo nodo, String scopeActual/*each parameter_specification*/){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //parameter mode es el penultimo nodo
        String parameter_mode = nodo.getHijos().get(nodo.getHijos().size()-2).getValor();
        
        int parameter_mode_int; 
        
        switch (parameter_mode) {
            case "In":
                parameter_mode_int = 1;
                break;
            case "Out":
                parameter_mode_int = 2;
                break;
            case "In Out":
                parameter_mode_int = 3; 
                break;
            default:
                parameter_mode_int = 1;// en caso de null ahora 1 
                break;
        }
        
        Nodo ID_LIST = nodo.getHijos().get(0);
       
        
        //iterar sobre ID_LIST
        for (int i = 0; i < ID_LIST.getHijos().size(); i++) {
            if(tipo.equals("Integer") || tipo.equals("Float")){
                currentDirection += 4;
            }
            else if (tipo.equals("Boolean")){
                currentDirection += 1;
            }
            Nodo actualNode = ID_LIST.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), scopeActual, tipo, currentDirection, parameter_mode_int );
            System.out.println("Agregado parametro " + actualNode.getValor() + " con offset de: " + currentDirection);
            tmpfnode.Add(tipo);
            //se agregan los parametros en tabla de simbolos general
            if (!this.symbolTable.addSymbol(tmpvnode)) {
                has_error=true;
               //System.out.println("El identificador \""+tmpvnode.Id+"\" en el scope "+tmpvnode.Scope+" ya esta declarado");
               System.out.println(scopeActual+":"+actualNode.getFila()+":"+actualNode.getColumna()+":"+" El identificador \""+tmpvnode.Id+"\" ya esta declarado");
            }
        }
    }
    
    public String typeValidation(Nodo nodo, String scopeActual){
        //variable type para guardar validacion
        String type = "";
        SymbolTableNode tmpNode;
        for (Nodo hijos : nodo.getHijos()){
            switch(hijos.getNombre()){
                case "NUM":
                    // Verifica si el valor es tipo int o float 
                    if (type != ""){
                        if (type.equals(numType(hijos.getValor()))) {
                            return type;//si type y el valor de num son del mismo tipo, retorna el tupo 
                        } else {//si no, marca error Y dice que no son tipos compatibles
                            //System.out.println("No son tipos compatibles " + type);
                            System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "No son tipos compatibles "+type);
                            has_error=true;
                            return "Integer";//retorna int por general
                        }
                    }else{
                        type = numType(hijos.getValor());//si type no tiene valor, lo asigna
                    }
                break;

                case "BOOLEAN_VALUE":
                    if(type != ""){
                        if(type.equals("Boolean")){
                            return type;
                        }else{
                            //System.out.println("No son tipos compatibles "+ type);
                            System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "No son tipos compatibles "+type);
                            has_error=true;
                            return "Integer";
                        }
                    }else{
                        type = "Boolean";
                    }
                break;

                case "ID":
                    if(!scopeActual.equals("")){
                        symbolTable.activateWithScope(scopeActual);//llamar antes de entrar a a la tabla
                    }
                    tmpNode = symbolTable.findActiveSymbol(hijos.getValor());
                    if (tmpNode != null) {
                        if (tmpNode instanceof VariableTableNode){
                            String tmpType = ((VariableTableNode) tmpNode).getType();
                            if (type != ""){
                                if(type.equals(tmpType)){
                                    return type;
                                }else{
                                    //System.out.println("No son tipos compatibles " + tmpType);
                                    System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "No son tipos compatibles "+type);
                                    has_error=true;
                                    return "Integer";
                                }
                            }else{
                                type = tmpType;
                            }
                        }else{
                            has_error=true;
                            //System.out.println("El identificador utilizado es de Funcion");

                            //no pude probar el print
                            System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "El identificador utilizado es de Funcion"+type);
                        }
                    } else {
                        has_error=true;
                        //System.out.println("La variable "+ hijos.getValor() + " no existe");
                        System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "La variable \""+ hijos.getValor() +"\"  no existe");
                    }
                break;
                // if((VariableTableNode)symbolTable.findSymbol(hijos.getValor(), scope).get){
                    
                    // }
                
                
                case "Call_Subroutine":
                    String tmpType = validateSubroutine(hijos, scopeActual); 
                    if (type!="") {
                        if(type.equals(tmpType)){
                            return type;
                        }else{
                            //System.out.println("No son tipos compatibles " + tmpType);
                            System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "No son tipos compatibles "+tmpType);
                            has_error=true;
                            return "Integer";
                        }
                    }else{
                        type = tmpType;
                    }
                break;
                case "MATHEMATICAL_EXPRESSION":
                case "OPREL":
                case "OPSUM":
                case "OPMULT":
                case "PARENTHESIS": // case todos los tipos que no almacenan valor
                    if(type !=""){
                        // System.out.println(typeValidation(hijos,scopeActual));
                        if (type.equals(typeValidation(hijos,scopeActual))) {// manda el nodo a la misma funcion para buscar que valor retorna recursivamente
                            return type;
                        }else{
                            has_error=true;
                            //System.out.println("No son tipos compatibles "+ scopeActual);//si no son equivalentes tira error
                            System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "No son tipos compatibles");//si no son equivalentes tira error
                            return type;
                        }
                    }else{
                        type = typeValidation(hijos,scopeActual);
                    }
                break;
            }
        }
        return type;
    }

    public String numType(String num){
        if(num.matches("[-+]?[0-9]*")){
            return "Integer";
        }
        return "Float";
    }

    public void validateReturn(Nodo nodo, String scope, String returnType){
        for (Nodo hijo : nodo.getHijos()) {// Se encarga de realizar una busqueda en profundidad en content
            if(hijo.getNombre().equals("RETURN_STATEMENT")){//  si encuentra en sus hijos, tira true
                if(returnType.equals("void") && !returnProc){
                    //utiliza la bandera returnProc, para mandar error si encuentra return solo una vez
                    //en un procedimiento
                    has_error=true;
                    //System.out.println("Procedimientos no deben de tener return: " + scope);
                    System.out.println(scope+":"+hijo.getFila()+":"+hijo.getColumna()+":"+ "Procedimientos no deben de tener return");
                }else if(returnType!= "void"){
                    //si no es procedimiento, se asegura que los return contengan el tipo de dato correcto
                    String type = typeValidation(hijo, scope);
                    if(!type.equals(returnType)){
                        //si no son iguales, manda error de tipo
                        has_error=true;
                        //System.out.println("El valor retornado no es del tipo correcto: " + scope);
                        System.out.println(scope+":"+hijo.getFila()+":"+hijo.getColumna()+":"+ "El valor retornado no es del tipo correcto");
                    }
                }
                returnProc = true;
            }else{
                validateReturn(hijo, scope, returnType);//si no, busca en profundidad hasta que posiblemente encuentre uno
            }
        }
    }

    public String validateSubroutine(Nodo nodo, String scopeActual){
        String typeReturn = "";
        Nodo tmpnode = nodo.getHijos().get(0);
        if(!scopeActual.equals("")){
            symbolTable.activateWithScope(scopeActual);//llamar antes de entrar a a la tabla
        }
        SymbolTableNode tmpNode = symbolTable.findActiveSymbol(tmpnode.getValor());
        if (tmpNode != null) {
            if (tmpNode instanceof FunctionTableNode){
                typeReturn = ((FunctionTableNode) tmpNode).getReturn_type();
                if(!typeReturn.equals("void")){
                    validateParams((FunctionTableNode) tmpNode, nodo.getHijos().get(1),scopeActual);
                }else{
                    //System.out.println("El identificador usado es de Procedimiento");
                    System.out.println(scopeActual+":"+nodo.getFila()+":"+nodo.getColumna()+":"+ "El identificador usado es de Procedimiento");
                    has_error=true;
                    typeReturn="Integer";
                }
                
            }else{
                System.out.println("El identificador utilizado es de Variable");
                has_error=true;
            }
        } else {
            //System.out.println("La Funcion "+ tmpnode.getValor() + " no existe");
            System.out.println(scopeActual+":"+nodo.getFila()+":"+nodo.getColumna()+":"+ "La Funcion "+ tmpnode.getValor() + " no existe");
            has_error=true;
        }
        return typeReturn;
    }

    public void validateParams(FunctionTableNode funcNode, Nodo nodo, String scopeActual){
        if (nodo.getHijos().size()==funcNode.getParams().size()){
            for (int i = 0; i < nodo.getHijos().size(); i++) {
                Nodo tmpNodo = new Nodo("");
                tmpNodo.addHijo(nodo.getHijos().get(i));
                String type = typeValidation(tmpNodo, scopeActual);
                if(!type.equals(funcNode.getParams().get(i))){
                    has_error=true;
                    System.out.println("El parametro " + (i+1) + " en el llamado de la funcion " + funcNode.getId()+ " no es del tipo correcto: " + scopeActual);
                }
            }
        }else{
            has_error=true;
            System.out.println("Los parametros enviados no son los correctos");
        }
    }

    public void validateIn(Nodo nodo, String scopeActual){
        Nodo nodo2=nodo.getHijos().get(0);
        if(!scopeActual.equals("")){
            symbolTable.activateWithScope(scopeActual);//llamar antes de entrar a a la tabla
        }
        SymbolTableNode tmpNode = symbolTable.findActiveSymbol(nodo2.getValor());
        if (tmpNode != null) {
            if (tmpNode instanceof VariableTableNode){
                if (((VariableTableNode)tmpNode).getForm()==1) {
                    has_error=true;
                    System.out.println(scopeActual+":"+nodo2.getFila()+":"+nodo2.getColumna()+":"+ "No se puede asignar a parametro modo in ");
                }
            }
        }
   }

    public void validateOut(Nodo nodo, String scopeActual){
        for (Nodo hijos : nodo.getHijos()){
            switch(hijos.getNombre()){
                case "ID":
                    if(!scopeActual.equals("")){
                        symbolTable.activateWithScope(scopeActual);//llamar antes de entrar a a la tabla
                    }
                    SymbolTableNode tmpNode = symbolTable.findActiveSymbol(hijos.getValor());
                    if (tmpNode != null) {
                        if (tmpNode instanceof VariableTableNode){
                            if (((VariableTableNode)tmpNode).getForm()==2) {
                                has_error=true;
                                System.out.println(scopeActual+":"+hijos.getFila()+":"+hijos.getColumna()+":"+ "parametros modo out no pueden ser utilizados en operaciones ");
                            }
                        }
                    }
                    break;
                default:
                    validateOut(hijos, scopeActual);
                    break;
            }
        }
    }
}