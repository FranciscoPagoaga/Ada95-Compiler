/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodIntermedio;

import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class IntermediateStatement extends IntermediateForm {
    private ArrayList<Label> next;
    private ArrayList<Label> outerList;
   
    public IntermediateStatement() { 
        this.next = new ArrayList();
        this.outerList = new ArrayList();
    }


    public ArrayList<Label> getNext() {
        return next;
    }

    public void setNext(ArrayList<Label> next) {
        this.next = next;
    }

    public ArrayList<Label> getOuterList() {
        return outerList;
    }

    public void setOuterList(ArrayList<Label> outerList) {
        this.outerList = outerList;
    }
    
     public String buildIntermediateCode() {
        StringBuilder sb = new StringBuilder();
          
        for (int i = 0; i < operations.size(); i++) {
            Quadruple currentQuadruple = operations.elementAt(i);
            
            if(currentQuadruple.getType()!=null){
            
                switch(currentQuadruple.getType()) {
                    case ADD: {
                        sb.append(currentQuadruple.geteLugar()+ " := " + currentQuadruple.getOp1() + " + " + currentQuadruple.getOp2());
                        break;
                    }
                    case MIN: {
                        sb.append(currentQuadruple.geteLugar() + " := " + currentQuadruple.getOp1() + " - " + currentQuadruple.getOp2());
                        break;
                    }
                    case UMIN: {
                        sb.append(currentQuadruple.geteLugar() + " := -" + currentQuadruple.getOp1());
                        break;
                    }
                    case MUL: {
                        sb.append(currentQuadruple.geteLugar() + " := " + currentQuadruple.getOp1() + " * " + currentQuadruple.getOp2());
                        break;
                    }
                    case DIV: {
                        sb.append(currentQuadruple.geteLugar() + " := " + currentQuadruple.getOp1() + " / " + currentQuadruple.getOp2());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_GEQ: {
                        sb.append("if " + currentQuadruple.getOp1() + " >= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_LEQ: {
                        sb.append("if " + currentQuadruple.getOp1() + " <= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_GT: {
                        sb.append("if " + currentQuadruple.getOp1() + " > " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_LT: {
                        sb.append("if " + currentQuadruple.getOp1() + " < " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_NEQ: {
                        sb.append("if " + currentQuadruple.getOp1() + " <> " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    //por si acaso se deja la implementacion original
                    case IF_EQ: {
                        sb.append("if " + currentQuadruple.getOp1() + " = " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                        break;
                    }
                    case ASSIGN: {
                        sb.append(currentQuadruple.geteLugar() + " := " + currentQuadruple.getOp1());
                        break;
                    }
                    case PARAM: {
                        sb.append("params " + currentQuadruple.getOp1());
                        break;
                    }
                    case CALL: {
                        sb.append("call " + currentQuadruple.getOp1() + ", " + currentQuadruple.getOp2());
                        break;
                    }
                    case GOTO: {
                        sb.append("goto " + currentQuadruple.geteLugar());
                        break;
                    }
                    case PRINT: {
                        sb.append("print " + currentQuadruple.getOp1());
                        break;
                    }
                    case READ: {
                        sb.append("read " + currentQuadruple.getOp1());
                        break;
                    }
                    case LABEL: {
                        sb.append(currentQuadruple.getLabel().getLabelName() + ":");
                        break;
                    }
                    case EXIT: {
                        sb.append(currentQuadruple.getOp1());
                        break;
                    }
                    case VOID_RET: {
                        sb.append(currentQuadruple.getOp1());
                        break;
                    }
                    case CLOSE: {
                        sb.append("fin_programa");
                        break;
                    }
                    case FUNCTION_END: {
                        sb.append("fin_funcion "+currentQuadruple.getOp1());
                        break;
                    }
                    default: 
                        System.out.println("recibí una operacion invalida en la funcion buildIntermediateCode ");







                }
            }
            
            //switch para oprel
            if (currentQuadruple.getTypeString()!=null) {
                switch(currentQuadruple.getTypeString()) {
                
                case "IF>=": {
                    sb.append("if " + currentQuadruple.getOp1() + " >= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                    break;
                }
                case "IF<=": {
                    sb.append("if " + currentQuadruple.getOp1() + " <= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                    break;
                }
                case "IF>": {
                    sb.append("if " + currentQuadruple.getOp1() + " > " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                    break;
                }
                case "IF<": {
                    sb.append("if " + currentQuadruple.getOp1() + " < " + currentQuadruple.getOp2() + " goto " + currentQuadruple.geteLugar());
                    break;
                }
                case "IF/=": {
                    sb.append("if " + currentQuadruple.getOp1() + " /= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                    break;
                }
                case "IF=": {
                    sb.append("if " + currentQuadruple.getOp1() + " = " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel().getLabelName());
                    break;
                }
                default: 
                    System.out.println("recibí un OPREL invalido en la funcion BuildIntermediateCode");
                
                
             
                }
            }
            
            
            
            
            
            sb.append("\n");
        }
        return sb.toString();
     }
}
