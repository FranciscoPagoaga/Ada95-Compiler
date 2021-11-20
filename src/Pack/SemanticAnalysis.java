package Pack;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.util.ArrayList;

public class SemanticAnalysis {
    private SymbolTable symbolTable;
    private boolean has_error;
    private String scope;
    private String current_id;
    private boolean returnProc;

    public SemanticAnalysis(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
        has_error= false;
        scope = "";
        returnProc = false;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void Traverse(Nodo nodo, String scopeActual){
        scope = scopeActual;
        if(!scopeActual.equals("")){
            symbolTable.activateWithScope(scopeActual);
        }
        switch (nodo.getNombre()){
            case "VARIABLE_DECLARATION":
                addVariableDeclaration(nodo, scopeActual);
            break;
            
            case "Inicio":
                addFunctionBlock(nodo, "");
                break;
            case "FUNCTION_BLOCK":
            case "PROCEDURE_BLOCK":
                addFunctionBlock(nodo, scopeActual);
            break;
            case "ASSIGNMENT":
                typeValidation(nodo);
            break;
            case "BooleanExp":
                typeValidation(nodo);
            default:
                for (Nodo hijos : nodo.getHijos()) {
                    Traverse(hijos, scopeActual);
                }
            break;
        }
        // if(nodo.getNombre().equals("VARIABLE_DECLARATION")){
        //     addVariableDeclaration(nodo);
        // }else if(nodo.getNombre().equals("FUNCTION_BLOCK")||nodo.getNombre().equals("PROCEDURE_BLOCK")){
        //     addFunctionBlock(nodo);
        // }else{
        //     for (Nodo hijos : nodo.getHijos()) {
        //         Traverse(hijos);
        //     }
        // }
    }

    // Itera sobre el nodo VARIABLE_DECLARATION para agregar id con sus tipos
    public void addVariableDeclaration(Nodo nodo, String scopeActual){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //Se debe iterar todos menos el ultimo tipo, debido a que no es un id 

        for (int i = 0; i <= nodo.getHijos().size()-2; i++) {
            Nodo actualNode = nodo.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), scopeActual, tipo, 0);
            //agrega variable a lista de simbolos, retorna true si se pudo, false si ya existia
            if (!this.symbolTable.addSymbol(tmpvnode)) {
                System.out.println("El identificador ya esta declarado");
            }
        }
    }

    public void addFunctionBlock(Nodo nodo, String scopeActual){
        String returnType="void";// si es un procedure el tipo de valor de retorno es void
        Nodo parametros=null;
        //Nodo id_list = null;
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
                        scopeHijos = scopeActual +"."+ hijo.getValor();
                    }
                    break;
                case "RETURN_TYPE": //solo en FUNCTION_BLOCK
                    returnType = hijo.getValor();
                break;
                case "DECLARATIONS":
                    Traverse(hijo, scopeActual);
                break;
                case "MAIN_PARAMETERS":
                    parametros = hijo; // parametros.parameters 
                break;
                case "CONTENT":
                    Traverse(hijo, scopeActual);
                    validateReturn(hijo, nodo.getNombre());
                break;
            }
        }
        
        
        //la funcion mantiene una lista de parametros
        FunctionTableNode tmpfnode = new FunctionTableNode(nodo.getHijos().get(0).getValor(), scopeActual, returnType, scopeHijos);
      
        //se agrega cada parametro a tmpfnode
        for (int i = 0; i < parametros.getHijos().size(); i++) {
            Nodo actualNode = parametros.getHijos().get(i);//EVERY PARAMETER
            AddParameters(tmpfnode, actualNode);// return arraylist
        }
        
        //asignar parametros
        
        
        
        //añadir funcion a la tabla de simbolos mas general
        if (!this.symbolTable.addSymbol(tmpfnode)) {
            System.out.println("esta funcion ya existe");
        }
    }
    
    
    /*
    public ArrayList<VariableTableNode> addParameters(Nodo nodo){
        FuntionTableNode tmpfnode = FunctionTableNode()
        
        for (Nodo Hijo : nodo.getHijos()) {
            
        }
    }*/
    
    public void AddParameters(FunctionTableNode tmpfnode, Nodo nodo/*each parameter_specification*/){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //parameter mode es el penultimo nodo
        String parameter_mode = nodo.getHijos().get(nodo.getHijos().size()-2).getValor();
        
        int parameter_mode_int ; 
        
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
                parameter_mode_int = 0;// en caso de null ahora 1
                break;
        }
        
        Nodo ID_LIST = nodo.getHijos().get(0);
        
        
        //iterar sobre ID_LIST
        for (int i = 0; i < ID_LIST.getHijos().size(); i++) {
            Nodo actualNode = ID_LIST.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), "s0", tipo,parameter_mode_int );
            //agrega variable a lista de simbolos, retorna true si se pudo, false si ya existia
            
            /*if (!this.symbolTable.addSymbol(tmpvnode)) {
                System.out.println("El identificador ya esta declarado");
            }else{
                //si agrega
                params.add(tmpvnode);
            }*/
            
            //params.add(tmpvnode);
            
            //asinar directamente al arraylist de variables dentro de la funcion, si no esta repetida (falta validar)
            //tmpfnode.getParams().add(tmpvnode);
            
            //intento de validar 
            //recorrer los nombres de los parametros
            
            
            
            tmpfnode.getParams().add(tmpvnode);
        }
    }
    
    public String typeValidation(Nodo nodo){
        //variable type para guardar validacion
        String type = "";
        for (Nodo hijos : nodo.getHijos()){
            switch(hijos.getNombre()){
                case "NUM":
                    // Verifica si el valor es tipo int o float
                    System.out.println(hijos.getValor());
                    if (type != ""){
                        if (type.equals(numType(hijos.getValor()))) {
                            return type;//si type y el valor de num son del mismo tipo, retorna el tupo 
                        } else {//si no, marca error Y dice que no son tipos compatibles
                            System.out.println("No son tipos compatibles");
                            has_error=true;
                            return "Integer";//retorna int por general
                        }
                    }else{
                        type = numType(hijos.getValor());//si type no tiene valor, lo asigna
                    }
                break;
                
                case "ID":
                    if (type != ""){
                        if (type.equals("Integer")) {
                            return type;
                        } else {
                            System.out.println("No son tipos compatibles");
                            has_error=true;
                            return "Integer";
                        }
                    }else{
                        type = "Integer";
                    }
                    
                // if((VariableTableNode)symbolTable.findSymbol(hijos.getValor(), scope).get){
                    
                    // }
                
                
                case "Call_subroutine":
                    
                break;
                case "MATHEMATICAL_EXPRESSION":
                    type = typeValidation(hijos);
                break;
                case "OPREL":
                case "OPSUM":
                case "OPMULT":
                case "PARENTHESIS": // case todos los tipos que no almacenan valor
                    System.out.println("op: " + hijos.getNombre());
                    if(type !=""){
                        System.out.println(type);
                        if (type.equals(typeValidation(hijos))) {// manda el nodo a la misma funcion para buscar que valor retorna recursivamente
                            return type;
                        }else{
                            has_error=true;
                            System.out.println("No son valores validos");//si no son equivalentes tira error
                            return type;
                        }
                    }
                    type = typeValidation(hijos);
                break;
            }
        }
        return type;
    }

    public void hasReturn(Nodo nodo){
        for (Nodo hijo : nodo.getHijos()) {// Se encarga de realizar una busqueda en profundidad en content
            System.out.println(hijo.getNombre());
            if(hijo.getNombre().equals("RETURN_STATEMENT")){//  si encuentra en sus hijos, tira true
                System.out.println("Encuentra Return");
                returnProc = true;
            }else{
                hasReturn(hijo);//si no, busca en profundidad hasta que posiblemente encuentre uno
            }
        }
    }

    public String numType(String num){
        if(num.matches("[-+]?[0-9]*")){
            return "Integer";
        }
        return "Float";
    }

    public void validateReturn(Nodo nodo, String type){
        hasReturn(nodo);
        if(returnProc && type.equals("PROCEDURE_BLOCK")){ //validates if procedure has return
            has_error=true;
            System.out.println("Procedimientos no pueden tener return");
        } else if(!returnProc && type.equals("FUNCTION_BLOCK")){// or if function has block
            has_error=true;
            System.out.println("Las Funciones deben de tener return " + type);
        }
        returnProc = false;
    }
}
