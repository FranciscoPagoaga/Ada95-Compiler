/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodIntermedio;

/**
 *
 * @author pc
 */
public class Quadruple {
    public enum Operations {ADD, MIN, UMIN, MUL, DIV, IF_GEQ, IF_LEQ, IF_GT, IF_LT, IF_NEQ, IF_EQ, ASSIGN, PARAM, CALL, GOTO, PRINT, READ, LABEL, EXIT, VOID_RET, POWER, FUNCTION_END, CLOSE }
    private String eLugar;
    private String op1;
    private String op2;
    private Operations type;
    private Label l;
    private String scope;

    public Quadruple(Operations type, String op1, String op2, String eLugar) {
        this.op1 = op1;
        this.op2 = op2;
        this.type = type;
        this.eLugar = eLugar;
    }
    public Quadruple (Operations type){
       this.type= type;
    }
    
    public Quadruple(Operations type, String op1, String op2 ,String eLugar, String scope) {
        this.type = type;
        this.op1 = op1;
        this.op2 = op2;
        this.eLugar = eLugar;
        this.scope = scope;
    }

    public Quadruple( Operations type, String op1, String op2,String eLugar, Label l) {
        this.type = type;
        this.op1 = op1;
        this.op2 = op2;
        this.eLugar = eLugar;
        this.l = l;
    }
    
    public Quadruple(Label l) {
        this.l = l;
        this.type = Operations.LABEL;
    }

    public String geteLugar() {
        return eLugar;
    }

    public void seteLugar(String eLugar) {
        this.eLugar = eLugar;
    }
    
   

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public Operations getType() {
        return type;
    }
    
    public Label getLabel() {
        return l;
    }
}
