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
                case IF_GEQ: {
                    sb.append("if " + currentQuadruple.getOp1() + " >= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
                    break;
                }
                case IF_LEQ: {
                    sb.append("if " + currentQuadruple.getOp1() + " <= " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
                    break;
                }
                case IF_GT: {
                    sb.append("if " + currentQuadruple.getOp1() + " > " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
                    break;
                }
                case IF_LT: {
                    sb.append("if " + currentQuadruple.getOp1() + " < " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
                    break;
                }
                case IF_NEQ: {
                    sb.append("if " + currentQuadruple.getOp1() + " <> " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
                    break;
                }
                case IF_EQ: {
                    sb.append("if " + currentQuadruple.getOp1() + " = " + currentQuadruple.getOp2() + " goto " + currentQuadruple.getLabel());
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
                    sb.append("goto " + currentQuadruple.getLabel());
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
                    sb.append(currentQuadruple.getLabel() + ":");
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
            }
            sb.append("\n");
        }
        return sb.toString();
     }
}
