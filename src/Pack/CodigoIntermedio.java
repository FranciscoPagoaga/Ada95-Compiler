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

    public void complete(ArrayList<Label> list, Label label) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setLabelName(label.toString());
        }
    }

    public ArrayList<Label> merge(ArrayList<Label> list1, ArrayList<Label> list2) {
        ArrayList<Label> newList = new ArrayList();
        newList.addAll(list1);
        newList.addAll(list2);
        return newList;
    }
    
    
    //
    ArrayList<Nodo> padres = new ArrayList<>();
    ArrayList<Nodo> padresOrdenados = new ArrayList<>();
    ArrayList<Nodo> asignaciones = new ArrayList<>();
    
    IntermediateExpression ie = new IntermediateExpression();
   
    
    
    
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
            System.out.println(padresOrdenados.get(i).getNumNodo());
        }
    }
    
    
    public  void TraverseMathE(Nodo nodo){//busca el nodo E
        
        
        
        switch (nodo.getNombre()){
            
            case "ASSIGNMENT":
            case "MATHEMATICAL_EXPRESSION":
            case "OPSUM":
            case "OPMULT":
            case "PARENTHESIS":
                //guardar cada padre en el orden que se encuentra
                padres.add(nodo);
            
            
            //por default a los hijos
            default:
                for (Nodo hijos : nodo.getHijos()) {
                    TraverseMathE(hijos);
                }
            
        }
       
    }
    
    
    
    
    
    
    public void GenerandoCod(){
        String e1_lugar = "error soy e1.lugar";
        String e2_lugar = "error soy e2.lugar";
        Temporal temp = null;
        Quadruple quad= null;
        Nodo MathE = null;
     
        for (int i = 0; i < padresOrdenados.size(); i++) {
            // generando codigo nodo a nodo en el orden ya dado por el ordenamiento
            switch (padresOrdenados.get(i).getNombre()){
                case "MATHEMATICAL_EXPRESSION":
                    //sube el temporal
                    //asigna a su temporal el temporal que tiene el hijo
                    padresOrdenados.get(i).setE_lugar(padresOrdenados.get(i).getHijos().get(0).getE_lugar());
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
                    
                    
                    //si hay cuadruplos
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
                
                case "ASSIGNMENT":
                break;


                default:
                    System.out.println("algo mal generado en codigo intermedio de MathExpression");
            
            }
            
        }
        
        //por ultimo Asignar, el nodo assign se encuentra en la penultima posicion
        String e_lugar = MathE.getE_lugar();
        String id_valex = padresOrdenados.get(padresOrdenados.size()-2).getHijos().get(0).getValor();
                
        //se genera un cuadruplo
        quad = new Quadruple(Quadruple.Operations.ASSIGN,e_lugar ,"",id_valex );
        
        ie.operations.add(quad);
        
        
    }   
    
    

    
}
