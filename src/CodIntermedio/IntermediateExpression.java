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
public class IntermediateExpression extends IntermediateStatement{
    private Temporal eLugar;
    private ArrayList<Label> t;
    private ArrayList<Label> f;
    
    public IntermediateExpression() { 
        this.t = new ArrayList();
        this.f = new ArrayList();
    }

    

    public ArrayList<Label> getTrue() {
        return t;
    }

    public ArrayList<Label> getFalse() {
        return f;
    }

    public Temporal geteLugar() {
        return eLugar;
    }

    public void seteLugar(Temporal eLugar) {
        this.eLugar = eLugar;
    }
    
    

    public void setTrue(ArrayList<Label> t) {
        this.t = t;
    }

    public void setFalse(ArrayList<Label> f) {
        this.f = f;
    }
}

