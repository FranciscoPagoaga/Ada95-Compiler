/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pack;

import CodIntermedio.IntermediateExpression;
import CodIntermedio.IntermediateStatement;
import CodIntermedio.Label;
import CodIntermedio.Quadruple;
import CodIntermedio.Temporal;
import CodIntermedio.Quadruple.Operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author pc
 */
public class CodigoIntermedio {
    private BufferedWriter out;
    private StringBuilder file;
    private SemanticAnalysis instance;
    Nodo AssignActual = null;
    Temporal temporal = null;
    IntermediateExpression ie = new IntermediateExpression();
   
    //label global
    Label label = new Label();
    
    //codigo para mas adelante quizas
    private IntermediateStatement program;
    private ArrayList<String> stringsTable;
    private ArrayList<Double> doublesTable;
    private String scope;
    private ArrayList<String> scopesTable;
    private Boolean hasElse=false;

    public CodigoIntermedio(File outputFile, SemanticAnalysis instance) throws IOException {
        this.out = new BufferedWriter(new FileWriter(outputFile));
        this.setInstance(instance);
        stringsTable = new ArrayList();
        doublesTable = new ArrayList();
        scopesTable = new ArrayList();
        this.scope = "";
        Scope.resetCount();
    }

    public IntermediateStatement getProgram() {
        return program;
    }

    public void setProgram(IntermediateStatement program) {
        this.program = program;
    }

    public SemanticAnalysis getInstance() {
        return instance;
    }

    public void setInstance(SemanticAnalysis instance) {
        this.instance = instance;
    }

    public ArrayList<String> getScopesTable() {
        return scopesTable;
    }

    public void setScopesTable(ArrayList<String> scopesTable) {
        this.scopesTable = scopesTable;
    }

    public void createFile(String content) throws IOException {
        out.write(content);
        out.flush();
        out.close();
    }

    public ArrayList<String> getStringsTable() {
        return stringsTable;
    }

    public void GenerandoCod(Nodo padre){
        scope = "_"+padre.getHijos().get(0).getValor();
        scopesTable.add(scope);
        TraverseFunctions(padre, scope);
    }

    public void TraverseFunctions(Nodo nodo,String scope){
        for (int i = nodo.getHijos().size()-1; i >= 0; i--) {
            Nodo hijo = nodo.getHijos().get(i); 
            switch(hijo.getNombre()){
                case "CONTENT":
                    // current_function = nodo.getHijos().get(0).getValor();
                    label = new Label(scope);
                    Quadruple quad = new Quadruple(label);
                    ie.operations.add(quad);
                    Traverse(hijo, scope);
                    if (nodo.getNombre().equals("Inicio")) {
                        quad = new Quadruple(Quadruple.Operations.CLOSE);                    
                        ie.operations.add(quad);
                    }else{
                        quad = new Quadruple(Quadruple.Operations.FUNCTION_END,scope,"","");                    
                        ie.operations.add(quad);
                    }
                    break;
                case "DECLARATIONS":
                    Traverse(hijo, scope);
                    break;
                
                default:
                    TraverseFunctions(hijo, scope);
                    break;
            }
        }
    }

    public void Traverse(Nodo nodo, String scope){
        Quadruple quad;
        Label siguiente;
        for (Nodo hijo : nodo.getHijos()) {
            switch(hijo.getNombre()){
                case "PROCEDURE_BLOCK":
                case "FUNCTION_BLOCK":
                    scopesTable.add(scope +"."+hijo.getHijos().get(0).getValor());
                    TraverseFunctions(hijo, scope +"."+hijo.getHijos().get(0).getValor());
                    break;    
                case "IF_BLOCK":
                    //crea que necesitare condicion para saber si el siguiente no esta asignado ya 
                    siguiente = new Label();
                    hijo.setSiguiente(siguiente);
                    ifBlock(hijo, scope);
                    quad = new Quadruple(hijo.getSiguiente());//crea etiqueta de verdader
                    ie.operations.add(quad);
                    break;
                case "WHILE_BLOCK":
                    //crea que necesitare condicion para saber si el siguiente no esta asignado ya 
                    siguiente = new Label();
                    hijo.setSiguiente(siguiente);
                    whileBlock(hijo, scope);
                    quad = new Quadruple(hijo.getSiguiente());//crea etiqueta de verdader
                    ie.operations.add(quad);                    
                    break;
                case "FOR_BLOCK":
                    if (nodo.getSiguiente()==null) {
                        siguiente = new Label();
                        hijo.setSiguiente(siguiente);
                    }else{
                        hijo.setSiguiente(nodo.getSiguiente());
                    }
                    forBlock(hijo, scope);
                    break;
                case "LOOP_BLOCK":
                    siguiente = new Label();
                    hijo.setSiguiente(siguiente);
                    loopBlock(hijo, scope);
                    quad = new Quadruple(hijo.getSiguiente());//crea etiqueta de verdader
                    ie.operations.add(quad);                    
                    break;
                case "EXIT_CYCLE":
                    hijo.getHijos().get(0).setVerdadero(nodo.getSiguiente());
                    boolExp(hijo.getHijos().get(0),scope);
                    break;
                case "RETURN_STATEMENT":
                    MathExp(hijo,scope);
                    quad = new Quadruple(Quadruple.Operations.ASSIGN,hijo.getHijos().get(0).getE_lugar(),"","RET",new Label(scope));
                    ie.operations.add(quad);
                    break;
                case "Call_Subroutine":
                    parameterCallValidation(hijo.getHijos().get(1), scope);
                    String id = hijo.getHijos().get(0).getValor();
                    int paramSize = hijo.getHijos().get(1).getHijos().size();
                    if (scope.split("\\.")[scope.split("\\.").length - 1].equals(id)) {
                        quad = new Quadruple(Quadruple.Operations.CALL,scope,Integer.toString(paramSize),"");
                    }else{
                        quad = new Quadruple(Quadruple.Operations.CALL,scope + "." +id,Integer.toString(paramSize),"");                        
                    }
                    ie.operations.add(quad);
                    break;
                case "PUT":
                    String value="";
                    if (hijo.getHijos().get(0).getNombre().equals("STR")) {
                        value = "\""+hijo.getHijos().get(0).getValor()+"\"";
                        if (!stringsTable.contains(hijo.getHijos().get(0).getValor())) {
                            stringsTable.add(hijo.getHijos().get(0).getValor());
                        }
                    }else{
                        if (hijo.getHijos().get(0).getNombre().equals("ID")) {
                            hijo.setE_lugar("_"+hijo.getHijos().get(0).getValor());                        
                        }else{
                            hijo.setE_lugar(hijo.getHijos().get(0).getValor());
                        }
                        value = hijo.getE_lugar();
                    }
                    quad = new Quadruple(Quadruple.Operations.PRINT,value,"","");
                    ie.operations.add(quad);
                    break;
                case "ASSIGNMENT":
                    MathExp(hijo, scope);
                    hijo.setE_lugar(hijo.getValor());
                    quad = new Quadruple(Quadruple.Operations.ASSIGN,hijo.getHijos().get(1).getE_lugar(),"",hijo.getHijos().get(0).getE_lugar());
                    ie.operations.add(quad);
                    break;
                case "GET":
                    if (hijo.getNombre().equals("ID")) {
                        hijo.setE_lugar("_"+hijo.getHijos().get(0).getValor());                        
                    }else{
                        hijo.setE_lugar(hijo.getHijos().get(0).getValor());
                    }
                    quad = new Quadruple(Quadruple.Operations.READ,"_"+hijo.getE_lugar(),"","");
                    ie.operations.add(quad);                    
                    break;
                default:
                    Traverse(hijo, scope);
                break;
            }
        }
    }

    public void whileBlock(Nodo nodo, String scope){
        Label comienzo = new Label();
        Label verdadero = new Label();
        Quadruple quad = new Quadruple(comienzo);//Crea etiqueta de comienzo en caso que se cumpla la condicion regresar
        ie.operations.add(quad);
        for (Nodo hijo : nodo.getHijos()) {
            switch (hijo.getNombre()) {
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);//setea verdadero
                    hijo.setFalso(nodo.getSiguiente());//setea falso, debido a que es while es el siguiente
                    boolExp(hijo, scope);//se ejecuta la expresion booleana
                    quad = new Quadruple(verdadero);//crea etiqueta de verdader
                    ie.operations.add(quad);
                    break;
                case "CONTENT":
                    hijo.setSiguiente(comienzo);
                    Traverse(hijo, scope);//crea codigo despues de etiqueta de verdadero
                    quad = new Quadruple(Quadruple.Operations.GOTO, "","","",comienzo);
                    ie.operations.add(quad);
                    break;
            }
        }
    }

    public void ifBlock(Nodo nodo, String scope){
        Label verdadero = new Label();
        int pos = nodo.getHijos().size()-1;
        String nom = nodo.getHijos().get(pos).getNombre();
        Label falso;
        if (nom.equals("ELSIF_BLOCK") || nom.equals("ELSE_BLOCK") ) {
            falso = new Label();  //If siempre crea nuevas etiquetas en caso verdadero  y falso
            if (nom.equals("ELSE_BLOCK")) {
                hasElse=true;
            }
        }else{
            falso = nodo.getSiguiente();
        }
        int i=-1;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch (hijo.getNombre()) {
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);
                    hijo.setFalso(falso);//asigna caso verdadero y falso en su hijo antes de ejecutar
                    boolExp(hijo,scope);
                    break;
                case "CONTENT":
                    hijo.setSiguiente(nodo.getSiguiente());
                    Quadruple quad = new Quadruple(verdadero);//asigna label de verdadero
                    ie.operations.add(quad);
                    Traverse(hijo, scope);//crea codigo en caso de verdadero
                    if (nodo.getHijos().size()-1>i){//revisa la posibilidad de existir un elsif o else para crear etiquetas y gotos para salir de demas casos
                        quad = new Quadruple(Quadruple.Operations.GOTO, "","","",nodo.getSiguiente());
                        ie.operations.add(quad);
                        quad = new Quadruple(falso);
                        ie.operations.add(quad);
                    }
                    break;
                case "ELSIF_BLOCK":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente al bloque elsif para saber donde sigue
                    elsifBLock(hijo, scope);
                break;
                case "ELSE_BLOCK":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente al bloque else para saber donde sigue
                    Traverse(hijo, scope);                    
                    break;
                default:
                    break;
            } 
        }
    }

    public void elsifBLock(Nodo nodo, String scope){
        Label verdadero = new Label();//Crear caso verdader
        Label falso=null;
        if (!hasElse && !nodo.getHijos().get(nodo.getHijos().size()-1).getNombre().equals("ELSIF_BLOCK")) {
            falso = nodo.getSiguiente();//Crear caso falso
        }else{
            falso = new Label();
        }
        int i=0;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch(hijo.getNombre()){
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);
                    hijo.setFalso(falso);//Setea los valores falsos y verdaderos en 'E' antes de ejecutar
                    boolExp(hijo, scope);
                    break;
                case "CONTENT":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente
                    Quadruple quad = new Quadruple(verdadero);//crea la etiqueta de verdadero
                    ie.operations.add(quad);//agrega etiqueta a lista
                    Traverse(hijo, scope);//Deberia seguir agregando en Traverse
                    quad = new Quadruple(Quadruple.Operations.GOTO, "","","",hijo.getSiguiente());//Etiqueta de goto
                    ie.operations.add(quad);
                    if (hasElse && !nodo.getHijos().get(nodo.getHijos().size()-1).getNombre().equals("ELSIF_BLOCK")) {
                        quad = new Quadruple(falso);//caso falso
                        ie.operations.add(quad);
                    }else if((hasElse && nodo.getHijos().get(nodo.getHijos().size()-1).getNombre().equals("ELSIF_BLOCK"))){
                        quad = new Quadruple(falso);//caso falso
                        ie.operations.add(quad);
                    }else if(!hasElse && nodo.getHijos().get(nodo.getHijos().size()-1).getNombre().equals("ELSIF_BLOCK")){
                        quad = new Quadruple(falso);//caso falso
                        ie.operations.add(quad);                        
                    }
                    break;
                case "ELSIF_BLOCK":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente antes de correr bloque elsif
                    elsifBLock(hijo, scope);
                    break;
            }
        }
    }

    public void boolExp(Nodo nodo,String scope){
        int i=-1;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch(hijo.getNombre()){
                case "BooleanExp":
                    if (i == 0) {
                        if (nodo.getHijos().size()>1) {   
                            if (nodo.getHijos().get(i+1).getNombre().equals("OR")) {//Hace las etiquetas dependiendo del caso OR
                                hijo.setVerdadero(nodo.getVerdadero());
                                Label falso = label = new Label();
                                hijo.setFalso(falso);
                            }else if(nodo.getHijos().get(i+1).getNombre().equals("AND")){//Hace las etiquetas dependiendo del caso AND
                                Label verdadero = new Label();
                                hijo.setVerdadero(verdadero);
                                hijo.setFalso(nodo.getFalso());
                            }
                        }else{//Caso que sea hijo unico, solo pasa etiquetas del padre
                            hijo.setVerdadero(nodo.getVerdadero());
                            hijo.setFalso(nodo.getFalso());
                        }
                    }
                    boolExp(hijo, scope);
                break;
                case "OPREL":
                    MathExp(hijo,scope);
                    String temp1 = hijo.getHijos().get(0).getE_lugar();
                    String temp2 = hijo.getHijos().get(1).getE_lugar();
                    temporal= new Temporal();
                    Quadruple quad = null;//crea instruccion  de if
                    switch(hijo.getValor()){
                        case ">=":
                            quad = new Quadruple(Quadruple.Operations.IF_GEQ,temp1,temp2,"",nodo.getVerdadero());
                            break;
                        case "<=":
                            quad = new Quadruple(Quadruple.Operations.IF_LEQ,temp1,temp2,"",nodo.getVerdadero());
                            break;
                        case ">":
                            quad = new Quadruple(Quadruple.Operations.IF_GT,temp1,temp2,"",nodo.getVerdadero());
                            break;
                        case "=":
                            quad = new Quadruple(Quadruple.Operations.IF_EQ,temp1,temp2,"",nodo.getVerdadero());
                            break;
                        case "<":
                            quad = new Quadruple(Quadruple.Operations.IF_LT,temp1,temp2,"",nodo.getVerdadero());
                            break;
                        case "/=":
                            quad = new Quadruple(Quadruple.Operations.IF_NEQ,temp1,temp2,"",nodo.getVerdadero());
                            break;
                    }
                    ie.operations.add(quad);
                    if(nodo.getFalso()!=null){
                        quad = new Quadruple(Quadruple.Operations.GOTO, "","","",nodo.getFalso());//Etiqueta en caso falso
                        ie.operations.add(quad);
                    }
                    break;
                case "AND":
                    quad = new Quadruple(nodo.getHijos().get(i-1).getVerdadero());
                    ie.operations.add(quad);
                    nodo.getHijos().get(i+1).setVerdadero(nodo.getVerdadero());//asigna valores de falso y verdadero al hermano derecho antes de ejecutar
                    nodo.getHijos().get(i+1).setFalso(nodo.getFalso());
                break;
                case "OR":
                    quad = new Quadruple(nodo.getHijos().get(i-1).getFalso());
                    ie.operations.add(quad);
                    nodo.getHijos().get(i+1).setVerdadero(nodo.getVerdadero());//asigna valores de falso y verdadero al hermano derecho antes de ejecutar
                    nodo.getHijos().get(i+1).setFalso(nodo.getFalso());
                break;
            }
        }
    }

    public void forBlock(Nodo nodo, String scope){
        //para reducir codigo, solo va a saltar si es falso
        // orden de los hijos: id contador, inicio, fin, contenido
        // nunca deberia entrar a los else de error
            // setear lugar al contador
            
            nodo.getHijos().get(0).setE_lugar("_"+nodo.getHijos().get(0).getValor());

            // Si es id, su lugar ya existe. Si no, se asigna aqui
            if(nodo.getHijos().get(1).getNombre().equals("NUM")){
                nodo.getHijos().get(1).setE_lugar(nodo.getHijos().get(1).getValor());
            }else{
                nodo.getHijos().get(1).setE_lugar("_"+nodo.getHijos().get(1).getValor());
            }
            // Si es id, su lugar ya existe. Si no, se asigna aqui
            if(nodo.getHijos().get(2).getNombre().equals("NUM")){
                nodo.getHijos().get(2).setE_lugar(nodo.getHijos().get(2).getValor());
            }else{
                nodo.getHijos().get(2).setE_lugar("_"+nodo.getHijos().get(2).getValor());
            }
            // asignar valor inicial al contador
            Quadruple quad = new Quadruple(Quadruple.Operations.ASSIGN, nodo.getHijos().get(1).getE_lugar(),"",nodo.getHijos().get(0).getE_lugar());
            ie.operations.add(quad);
            //comienzo del loop
            Label comienzo = new Label();
            quad = new Quadruple(comienzo);//Crea etiqueta de comienzo en caso que se cumpla la condicion regresar
            ie.operations.add(quad);
            
            Label falso = new Label();
            // si no se cumple salir del loop
            quad = new Quadruple(Quadruple.Operations.IF_GEQ,nodo.getHijos().get(0).getE_lugar(),nodo.getHijos().get(2).getE_lugar(),"",falso);
            ie.operations.add(quad);
            Temporal temp = new Temporal();
            for (Nodo hijo : nodo.getHijos()) {
                switch (hijo.getNombre()) {
                    case "CONTENT":
                        hijo.setSiguiente(nodo.getSiguiente());
                        Traverse(hijo,scope);
                        //incrementar
                        quad = new Quadruple(Quadruple.Operations.ADD, nodo.getHijos().get(0).getE_lugar(), "1", temp.getTemporal());
                        ie.operations.add(quad);
                        //asignar
                        quad = new Quadruple(Quadruple.Operations.ASSIGN, temp.getTemporal(), "", nodo.getHijos().get(0).getE_lugar());
                        ie.operations.add(quad);
                        //regresar
                        quad = new Quadruple(Quadruple.Operations.GOTO, "","","",comienzo);
                        ie.operations.add(quad);

                        quad=new Quadruple(falso);
                        ie.operations.add(quad);
                        break;
                }
            }
    }

    public void loopBlock(Nodo nodo, String scope){
        Label comienzo = new Label();
        Quadruple quad = new Quadruple(comienzo);//Crea etiqueta de comienzo en caso que se cumpla la condicion regresar
        ie.operations.add(quad);
        nodo.getHijos().get(0).setSiguiente(nodo.getSiguiente());
        Traverse(nodo, scope);
        quad = new Quadruple(Quadruple.Operations.GOTO, "","","",comienzo);
        ie.operations.add(quad);
    }

    public void MathExp(Nodo nodo, String scope){
        for (Nodo hijo : nodo.getHijos()) {
            switch(hijo.getNombre()){
                case "ID":
                    hijo.setE_lugar("_"+hijo.getValor());                
                    break;                
                case "NUM":
                    hijo.setE_lugar(hijo.getValor());                
                    break;
                case "OPSUM":
                case "OPMULT":
                    MathExp(hijo, scope);
                    temporal = new Temporal();
                    hijo.setE_lugar(temporal.getTemporal());
                    Operations operation=null;
                    switch (hijo.getValor()) {
                        case "+":
                            operation = Quadruple.Operations.ADD;
                            break;
                        case "-":
                            operation = Quadruple.Operations.MIN;
                            break;
                        case "/":
                            operation = Quadruple.Operations.DIV;
                            break;
                        case "*":
                            operation = Quadruple.Operations.MUL;
                        break;                            
                    }
                    Quadruple quad = new Quadruple(operation,hijo.getHijos().get(0).getE_lugar(),hijo.getHijos().get(1).getE_lugar(),hijo.getE_lugar() );
                    ie.operations.add(quad);
                    break;
                case "MATHEMATICAL_EXPRESSION":
                case "PARENTHESIS":
                    MathExp(hijo,scope);
                    hijo.setE_lugar(hijo.getHijos().get(0).getE_lugar());
                    break;
                case "Call_Subroutine":
                    parameterCallValidation(hijo.getHijos().get(1),scope);
                    String id = hijo.getHijos().get(0).getValor();
                    int paramSize = hijo.getHijos().get(1).getHijos().size();
                    if (scope.split("\\.")[scope.split("\\.").length - 1].equals(id)) {
                        quad = new Quadruple(Quadruple.Operations.CALL,scope,Integer.toString(paramSize),"");
                    }else{
                        quad = new Quadruple(Quadruple.Operations.CALL,scope + "." +id,Integer.toString(paramSize),"");                        
                    }
                    ie.operations.add(quad);
                    temporal=new Temporal();
                    hijo.setE_lugar(temporal.getTemporal());
                    quad = new Quadruple(Quadruple.Operations.ASSIGN,"RET","",temporal.getTemporal());
                    ie.operations.add(quad);
            }
        }
    }

    public void parameterCallValidation(Nodo nodo,String scope){
        MathExp(nodo,scope);
        for (Nodo hijo : nodo.getHijos()) {
            Quadruple quad = new Quadruple(Quadruple.Operations.PARAM,hijo.getE_lugar(),"","");
            ie.operations.add(quad);
        }
    }
}
