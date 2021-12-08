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
    Temporal temporal = new Temporal();
    ArrayList<Nodo> padres = new ArrayList<>();
    ArrayList<Nodo> padresOrdenados = new ArrayList<>();
    ArrayList<Nodo> asignaciones = new ArrayList<>();
    IntermediateExpression ie = new IntermediateExpression();
   
    //label global
    Label label = new Label();
    
    //codigo para mas adelante quizas
    private IntermediateStatement program;
    private ArrayList<String> stringsTable;
    private ArrayList<Double> doublesTable;
    private String current_function;
    private String scope;

    public CodigoIntermedio(File outputFile, SemanticAnalysis instance) throws IOException {
        this.out = new BufferedWriter(new FileWriter(outputFile));
        this.instance = instance;
        stringsTable = new ArrayList();
        doublesTable = new ArrayList();
        this.scope = "";
        Scope.resetCount();
    }

    public void createFile(String content) throws IOException {
        out.write(content);
        out.flush();
        out.close();
    }

  

    public ArrayList<Label> merge(ArrayList<Label> list1, ArrayList<Label> list2) {
        ArrayList<Label> newList = new ArrayList();
        newList.addAll(list1);
        newList.addAll(list2);
        return newList;
    }
    
    
 
    
    
    
    /*
    public void revertirPadres (){
        reverse(padres);
    }
    
    public void reverse(ArrayList<Nodo> padres) {//no esta funcionando
        //recorremos el arraylist al reves
        for (int i = padres.size()-1; i >= 0; i--) {
            padresRevertidos.add(padres.get(i));
        }
        
        
    }
    */
    
    public ArrayList<Nodo> order(ArrayList<Nodo> padres) {
        // ordena los padres despues de la ejecucion de traverse
        //el orden correcto para generar el codigo intermedio esta dado por numNodo
        for (int i = 0; i < padres.size()-1; i++) {
           
            for (int j = i+1; j < padres.size(); j++) {
                if (padres.get(i).getNumNodo()>padres.get(j).getNumNodo()) {
                    Nodo temp = padres.get(j);
                    padres.set(j, padres.get(i));
                    padres.set(i, temp);
                }
            }
            
        }
        
        return padres;
        
        
    }
   
    
    public void ordenarPadres (){//ejecuta el orden
        padresOrdenados = order(padres);
    }
    
    
    
    public void printpadresOrdenados(){//para pruebas
        for (int i = 0; i < padresOrdenados.size(); i++) {
            System.out.println(padresOrdenados.get(i).getNombre());
            
        }
        System.out.println("-----fin toda una assig o while -------------------");
    }
    
    // public  void TraverseAssign(Nodo nodo){//busca el ASSIGNMENT
        
        
        
    //     switch (nodo.getNombre()){
            
    //         case "ASSIGNMENT":
    //             //ejecutar una MathE
    //             AssignActual = nodo;
    //             TraverseMathE(nodo);
    //             exec_mathE();
                
    //         default:
    //             for (Nodo hijos : nodo.getHijos()) {
    //                 TraverseAssign(hijos);
    //             }
            
    //     }
       
    // }
    
    
    // encuentra los nodos necesarios para formar una mathE
    // public  void TraverseMathE(Nodo nodo){ 
        
        
        
    //     switch (nodo.getNombre()){
            
            
    //         case "MATHEMATICAL_EXPRESSION":
    //         case "OPSUM":
    //         case "OPMULT":
    //         case "PARENTHESIS":
    //             //guardar cada padre en el orden que se encuentra
    //             padres.add(nodo);
            
            
    //         //por default a los hijos
    //         default:
    //             for (Nodo hijos : nodo.getHijos()) {
    //                 TraverseMathE(hijos);
    //             }
            
    //     }
        
        
        
        
        
        
        
       
    // }
    
    //se forma el codigo intermedio para una MathE
    public void exec_mathE (){
    
        //ejecucion de mathE
        String e1_lugar = "error soy e1.lugar";
        String e2_lugar = "error soy e2.lugar";
        Temporal temp = null;
        Quadruple quad= null;
        Nodo MathE = null;
        
        
        
        //se ordenan los padres que se encontraroon previemanete, sin esto no se sabe el orden de precedencia ni asociatividad
        ordenarPadres();
        
        //print de prueba
        printpadresOrdenados();
        
        
        for (int i = 0; i < padresOrdenados.size(); i++) {
            // generando codigo nodo a nodo en el orden ya dado por el ordenamiento
            switch (padresOrdenados.get(i).getNombre()){
                case "MATHEMATICAL_EXPRESSION":
                    //sube el temporal
                    //asigna a su temporal el temporal que tiene el hijo
                    
                    
                    if (padresOrdenados.get(i).getHijos().get(0).getNombre().equals("NUM") || padresOrdenados.get(i).getHijos().get(0).getNombre().equals("ID")) {
                        // caso a= 1 or a = id;
                        padresOrdenados.get(i).setE_lugar(padresOrdenados.get(i).getHijos().get(0).getValor());
                        
                    }else{
                        //caso a= temporal n
                        padresOrdenados.get(i).setE_lugar(padresOrdenados.get(i).getHijos().get(0).getE_lugar());
                        
                    }
                   
                    
                    
                    //save MathE
                    MathE = padresOrdenados.get(i);
                break;
                case "OPSUM":
                  
                    temp = new Temporal();
                    padresOrdenados.get(i).setE_lugar(temp.getTemporal());//E.lugar = temporalnuevo();
                    
                    
                    //si hay cuadruplos significa que el primer E.lugar ya subio por tanto hay que buscarlos
                    if(ie.operations.size()>0){
                        
                        
                     
                    
                        //conseguir e1.lugar
                        switch(padresOrdenados.get(i).getHijos().get(0).getNombre()){
                            case "OPSUM":
                            case "OPMULT":
                            case "PARENTHESIS":
                                e1_lugar = padresOrdenados.get(i).getHijos().get(0).getE_lugar();
                             
                            break;
                            default:
                                e1_lugar = padresOrdenados.get(i).getHijos().get(0).getValor();

                        }
                        
                        
                         //conseguir e2.lugar
                        switch(padresOrdenados.get(i).getHijos().get(1).getNombre()){
                            case "OPSUM":
                            case "OPMULT":
                            case "PARENTHESIS":
                                e2_lugar = padresOrdenados.get(i).getHijos().get(1).getE_lugar(); 
                               
                            break;
                            default:
                                e2_lugar = padresOrdenados.get(i).getHijos().get(1).getValor();

                        }
                        
                      
                        //se genera un cuadruplo 
                        quad = new Quadruple(Quadruple.Operations.ADD,e1_lugar ,e2_lugar,padresOrdenados.get(i).getE_lugar() );
                    
                    }else{
                        //agrega el primer cuadruplo con id's o num's
                        
                        quad = new Quadruple(Quadruple.Operations.ADD, padresOrdenados.get(i).getHijos().get(0).getValor(),padresOrdenados.get(i).getHijos().get(1).getValor(),padresOrdenados.get(i).getE_lugar() );
                    }
                    
                    
                    
                    ie.seteLugar(temp);
                    
                    //se agrega una fila en la tabla de cuadruplos
                    //operations se guarda en IntermediateForm.java 
                    ie.operations.add(quad);
                    
                 
                    
                    
                      
                break;    
                case "OPMULT":
                    temp = new Temporal();
                    padresOrdenados.get(i).setE_lugar(temp.getTemporal());//E.lugar = temporalnuevo();
                    
                    
                    //si hay cuadruplos significa que el primer E.lugar ya subio por tanto hay que buscarlos
                    if(ie.operations.size()>0){
                    
                        //conseguir e1.lugar
                        switch(padresOrdenados.get(i).getHijos().get(0).getNombre()){
                            case "OPSUM":
                            case "OPMULT":
                            case "PARENTHESIS":
                                e1_lugar = padresOrdenados.get(i).getHijos().get(0).getE_lugar();
                               
                            break;
                            default:
                                e1_lugar = padresOrdenados.get(i).getHijos().get(0).getValor();

                        }
                        
                        //conseguir e2.lugar
                        switch(padresOrdenados.get(i).getHijos().get(1).getNombre()){
                            case "OPSUM":
                            case "OPMULT":
                            case "PARENTHESIS":
                                e2_lugar = padresOrdenados.get(i).getHijos().get(1).getE_lugar();
                               
                            break;
                            default:
                                e2_lugar = padresOrdenados.get(i).getHijos().get(1).getValor();

                        }
                        
                     
                        //se genera un cuadruplo
                        quad = new Quadruple(Quadruple.Operations.MUL,e1_lugar ,e2_lugar,padresOrdenados.get(i).getE_lugar() );
                    
                    }else{
                        //agrega el primer cuadruplo
                        
                        quad = new Quadruple(Quadruple.Operations.MUL, padresOrdenados.get(i).getHijos().get(0).getValor(),padresOrdenados.get(i).getHijos().get(1).getValor(),padresOrdenados.get(i).getE_lugar() );
                    }
                    
                    
                    
                    ie.seteLugar(temp);
                    
                    //se agrega una fila en la tabla de cuadruplos
                    //operations se guarda en IntermediateForm.java 
                    ie.operations.add(quad);
                    
                   
                break;
                case "PARENTHESIS":
                    //sube el temporal
                    //asigna a su temporal el temporal que tiene el hijo
                    padresOrdenados.get(i).setE_lugar(padresOrdenados.get(i).getHijos().get(0).getE_lugar());
                      
                    
                break;   
                
                //case "ASSIGNMENT":
                //break;


                default:
                    System.out.println("algo mal generado en codigo intermedio de MathExpression");
            
            }
            
            
           
            
            
        }
        
        //por ultimo Asignar, el nodo assign es una variable global
        String e_lugar = MathE.getE_lugar();
        String id_valex = AssignActual.getHijos().get(0).getValor();
                
        //se genera un cuadruplo
        quad = new Quadruple(Quadruple.Operations.ASSIGN,e_lugar ,"",id_valex );
        
        //el cuadruplo de guarda en la tabla 
        ie.operations.add(quad);
        
        
        //se limpia el arraylist de padres para que este listo a la nueva asignacion
        padres.clear();
    
    
    }
  
    //---
    public  void TraverseBooleanE_While(Nodo nodo){ 
        
        
        
        switch (nodo.getNombre()){
            
            
            case "WHILE_BLOCK":
            case "BooleanExp":
            case "OPREL":
            case "AND":
            case "OR" :
                padres.add(nodo);
            
            
            //por default a los hijos
            default:
                for (Nodo hijos : nodo.getHijos()) {
                   TraverseBooleanE_While(hijos);
                }
            
        }
        
       
    }
    
    
    public void complete(ArrayList<Label> list, Label label) {
        
        for (int i = 0; i < list.size(); i++) {
            Quadruple talvezEncontro;
            talvezEncontro = findQuad(list.get(i));
            if (talvezEncontro!=null) {
                //reemplaza en la linea vacia
               
                talvezEncontro.seteLugar(""+label.getLabelName());
            }else{
              System.out.println("find quad no encontro la linea");  
            }
        }
        
        
    }
    
    public Quadruple findQuad (Label label){//encontrar la linea que se va a rellenar
        
        for (int i = 0; i < ie.operations.size(); i++) {
            if(ie.operations.elementAt(i).getLabel().getLabelName() == label.getLabelName()){
                return ie.operations.elementAt(i);
            }
        }
        
        return null;
        
    }
    
    // public void exec_BooleanE (){
    //     Quadruple quad= null;
    //     Nodo booleanExp_Padre= null;
    //     Label Mcuad = null;
    //     //se ordenan los padres que se encontraroon previemanete, sin esto no se sabe el orden de precedencia ni asociatividad
    //     ordenarPadres();
        
    //     printpadresOrdenados();
        
    //     for (int i = 0; i < padresOrdenados.size(); i++) {
    //         Nodo nodo_actual = padresOrdenados.get(i);
    //         switch(nodo_actual.getNombre()){
    //             case "OPREL"://case id OPREL id
                    
    //                 //guardar M.cuad
    //                 if(ie.operations.size()>0){//solo para OPREL derecho
    //                     Mcuad = new Label() ;
                        
                        
    //                 }
                    
    //                 // se genera el if a< b goto ______ con la linea 100
    //                 quad = new Quadruple("IF"+nodo_actual.getValor(), nodo_actual.getHijos().get(0).getValor(),nodo_actual.getHijos().get(1).getValor()," "/*got_______*/);
                    
    //                 //se crea E.listaVerdadera = CreaLista(sigCuad)
    //                 nodo_actual.getExpression().getTrue().add(quad.getLabel());//con la linea 100
    //                 quad.getLabel().addUno();
    //                 ie.operations.add(quad);//añadir el if
                    
                    
                    
    //                 //se genera el goto _______ con la 101
    //                 quad = new Quadruple(Quadruple.Operations.GOTO, "",""," ");
    //                 //se crea E.lista falsa = CreaLista(sigCuad+1)
    //                 nodo_actual.getExpression().getFalse().add(quad.getLabel());//con la linea 101
    //                 quad.getLabel().addUno();
    //                 ie.operations.add(quad);//añadir el goto___
                    
    //                 break;
                    
    //             case "BooleanExp":
                    
                    
    //                 // comprobamos que sea BooleanExp con un solo nodo
    //                 if(nodo_actual.getHijos().size()==1){
    //                     //subo id oprel id 
    //                     // o que es lo mismo Subir la Expression en el nodo
    //                     nodo_actual.setExpression (nodo_actual.getHijos().get(0).getExpression());
                        
                        
    //                 }
                    
                    
                    
                    
    //                 break;
    //             case "AND"://reduce el and con el hijo izq y el hijo der
                    
    //                 //el nodo anterior a este reduce el and
    //                 booleanExp_Padre = padresOrdenados.get(i-1);
                    
                    
                    
    //                 //completa E1.lista verdadera con M.cuad
                    
    //                 complete(booleanExp_Padre.getHijos().get(0).getExpression().getTrue(),Mcuad);
                    
                    
    //                 //E.listaVerdadera = E2.listaVerdadera
    //                 //System.out.println("E2.listaV"+booleanExp_Padre.getHijos().get(2).getExpression().getTrue().get(0).getLabelName());
                    
    //                 booleanExp_Padre.getExpression().setTrue(booleanExp_Padre.getHijos().get(2).getExpression().getTrue());
    //                 //System.out.println("E.listaV"+booleanExp_Padre.getExpression().getTrue().get(0).getLabelName());
                    
                    
                    
    //                 //E.listaFalsa = fusiona(E1.listaFalsa, E2.listaFalsa)
                    
    //                 booleanExp_Padre.getExpression().setFalse(merge(booleanExp_Padre.getHijos().get(0).getExpression().getFalse(),booleanExp_Padre.getHijos().get(2).getExpression().getFalse()));
                    
                    
    //                 //here ok
                    
                    
                    
    //                 break;
                    
    //             case "OR":
    //                 //el nodo anterior a este reduce el OR
    //                 booleanExp_Padre = padresOrdenados.get(i-1);
                    
    //                 //completa E1.listafalsa con M.cuad
                    
    //                 complete(booleanExp_Padre.getHijos().get(0).getExpression().getFalse(), Mcuad);
                    
                    
    //                 //E.listaVerdadera = fusiona(E1.listaVerdadera, E2.listaVerdadera)
    //                 booleanExp_Padre.getExpression().setTrue(merge(booleanExp_Padre.getHijos().get(0).getExpression().getTrue(),booleanExp_Padre.getHijos().get(2).getExpression().getTrue()));
                    
    //                  //E.listaFalsa = E2.listaFalsa
                    
    //                 booleanExp_Padre.getExpression().setFalse(booleanExp_Padre.getHijos().get(2).getExpression().getFalse());
                    
                    
                    
    //                 //print de todos los cuadruplos
    //                 for (int j = 0; j < ie.operations.size(); j++) {
    //                     System.out.println(ie.operations.elementAt(j).getLabel().getLabelName());
    //                 }
                    
                    
                    
    //                 break;
                    
    //             case "WHILE_BLOCK":
    //                 break;
    //             default:
    //                 System.out.println("algo mal generado en codigo intermedio de BooleanExp");
    //         }
    //     }
        
    //     padres.clear();
        
        
        
    // }

    public void Traverse(Nodo nodo){
        String siguiente;
        for (Nodo hijo : nodo.getHijos()) {
            switch(hijo.getNombre()){
                case "IF_BLOCK":
                //crea que necesitare condicion para saber si el siguiente no esta asignado ya 
                    label = new Label();
                    siguiente = "etiq"+label.getLabelName();
                    hijo.setSiguiente(siguiente);
                    ifBlock(hijo);
                    break;
                case "WHILE_BLOCK":
                //crea que necesitare condicion para saber si el siguiente no esta asignado ya 
                    label = new Label();
                    siguiente = "etiq"+label.getLabelName();
                    hijo.setSiguiente(siguiente);
                    whileBlock(hijo);
                    break;
                case "FOR_BLOCK":
                    //for block pendiente
                    break;
                default:
                    Traverse(hijo);
                break;
            }
        }
    }

    public void whileBlock(Nodo nodo){
        label = new Label();
        String comienzo = "etiq"+label.getLabelName();
        label = new Label();
        String verdadero = "etiq"+label.getLabelName();
        Quadruple quad = new Quadruple(Quadruple.Operations.LABEL, "","",comienzo);//Crea etiqueta de comienzo en caso que se cumpla la condicion regresar
        ie.operations.add(quad);
        for (Nodo hijo : nodo.getHijos()) {
            switch (hijo.getNombre()) {
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);//setea verdadero
                    hijo.setFalso(nodo.getSiguiente());//setea falso, debido a que es while es el siguiente
                    boolExp(hijo);//se ejecuta la expresion booleana
                    quad = new Quadruple(Quadruple.Operations.LABEL, "","",verdadero);//crea etiqueta de verdader
                    ie.operations.add(quad);
                    break;
                case "CONTENT":
                    hijo.setSiguiente(nodo.getSiguiente());
                    Traverse(hijo);//crea codigo despues de etiqueta de verdadero
                    quad = new Quadruple(Quadruple.Operations.GOTO, "","",comienzo);
                    ie.operations.add(quad);
                    break;
            }
        }
    }

    public void ifBlock(Nodo nodo){
        label = new Label();
        String verdadero = "etiq"+label.getLabelName();
        label = new Label();
        String falso = "etiq"+label.getLabelName();  //If siempre crea nuevas etiquetas en caso verdadero  y falso
        int i=-1;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch (hijo.getNombre()) {
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);
                    hijo.setFalso(falso);//asigna caso verdadero y falso en su hijo antes de ejecutar
                    boolExp(hijo);
                    break;
                case "CONTENT":
                    hijo.setSiguiente(nodo.getSiguiente());
                    Quadruple quad = new Quadruple(Quadruple.Operations.LABEL, "","",verdadero);//asigna label de verdadero
                    ie.operations.add(quad);
                    Traverse(hijo);//crea codigo en caso de verdadero
                    if (nodo.getHijos().size()-1>i){//revisa la posibilidad de existir un elsif o else para crear etiquetas y gotos para salir de demas casos
                        quad = new Quadruple(Quadruple.Operations.GOTO, "","",nodo.getSiguiente());
                        ie.operations.add(quad);
                        quad = new Quadruple(Quadruple.Operations.LABEL, "","",falso);
                        ie.operations.add(quad);
                    }
                    break;
                case "ELSIF_BLOCK":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente al bloque elsif para saber donde sigue
                    elsifBLock(hijo);
                break;
                case "ELSE":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente al bloque else para saber donde sigue
                    Traverse(hijo);
                    break;
                default:
                    break;
            } 
        }
    }

    public void elsifBLock(Nodo nodo){
        label = new Label();
        String verdadero = "etiq"+label.getLabelName();//Crear caso verdader
        label = new Label();
        String falso = "etiq"+label.getLabelName();//Crear caso falso
        int i=-1;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch(hijo.getNombre()){
                case "BooleanExp":
                    hijo.setVerdadero(verdadero);
                    hijo.setFalso(falso);//Setea los valores falsos y verdaderos en 'E' antes de ejecutar
                    boolExp(hijo);

                break;
                case "CONTENT":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente
                    Quadruple quad = new Quadruple(Quadruple.Operations.LABEL, "","",verdadero);//crea la etiqueta de verdadero
                    ie.operations.add(quad);//agrega etiqueta a lista
                    Traverse(hijo);//Deberia seguir agregando en Traverse
                    quad = new Quadruple(Quadruple.Operations.GOTO, "","",hijo.getSiguiente());//Etiqueta de goto
                    ie.operations.add(quad);
                    quad = new Quadruple(Quadruple.Operations.LABEL, "","",falso);//caso falso
                    ie.operations.add(quad);
                break;
                case "ELSIF_BLOCK":
                    hijo.setSiguiente(nodo.getSiguiente());//asigna siguiente antes de correr bloque elsif
                    elsifBLock(hijo);
                break;
            }
        }
    }

    public void boolExp(Nodo nodo){
        int i=-1;
        for (Nodo hijo : nodo.getHijos()) {
            i++;
            switch(hijo.getNombre()){
                case "BooleanExp":
                    if (i == 0) {
                        if (nodo.getHijos().size()>1) {   
                            if (nodo.getHijos().get(i+1).getNombre().equals("OR")) {//Hace las etiquetas dependiendo del caso OR
                                hijo.setVerdadero(nodo.getVerdadero());
                                label = new Label();
                                hijo.setFalso("etiq"+label.getLabelName());
                                System.out.println("Entra");
                            }else if(nodo.getHijos().get(i+1).getNombre().equals("AND")){//Hace las etiquetas dependiendo del caso AND
                                label = new Label();
                                hijo.setVerdadero("etiq"+label.getLabelName());
                                hijo.setFalso(nodo.getFalso());
                                System.out.println("Entra");
                            }
                        }else{//Caso que sea hijo unico, solo pasa etiquetas del padre
                            hijo.setVerdadero(nodo.getVerdadero());
                            hijo.setFalso(nodo.getFalso());
                        }
                    }
                    boolExp(hijo);
                break;
                case "OPREL":
                    temporal= new Temporal();
                    String temp1 = hijo.getHijos().get(0).getValor();
                    String temp2 = hijo.getHijos().get(1).getValor();
                    Quadruple quad = new Quadruple("IF"+hijo.getValor(),temp1,temp2,nodo.getVerdadero());//crea instruccion  de if
                    ie.operations.add(quad);
                    quad = new Quadruple(Quadruple.Operations.GOTO, "","",nodo.getFalso());//Etiqueta en caso falso
                    ie.operations.add(quad);
                    break;
                case "AND":
                    quad = new Quadruple(Quadruple.Operations.LABEL,"","",nodo.getHijos().get(i-1).getVerdadero());
                    ie.operations.add(quad);
                    nodo.getHijos().get(i+1).setVerdadero(nodo.getVerdadero());//asigna valores de falso y verdadero al hermano derecho antes de ejecutar
                    nodo.getHijos().get(i+1).setFalso(nodo.getFalso());
                break;
                case "OR":
                    quad = new Quadruple(Quadruple.Operations.LABEL,"","",nodo.getHijos().get(i-1).getFalso());
                    ie.operations.add(quad);
                    nodo.getHijos().get(i+1).setVerdadero(nodo.getVerdadero());//asigna valores de falso y verdadero al hermano derecho antes de ejecutar
                    nodo.getHijos().get(i+1).setFalso(nodo.getFalso());
                break;
            }
        }
    }

    public void GenerandoCod(Nodo padre){
        Traverse(padre);
        //TraverseBooleanE_While(padre);
        //exec_BooleanE();
        
        //se genera el codigo intermedio para varias asignaciones dentro de content
        //traverse assing llama a traverseMathE para que se ejecute una asignacion completa
        //TraverseAssign(padre);
       
        
        
        
        
     
        
        
    }   
    
    

    
}
