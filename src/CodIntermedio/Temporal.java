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
public class Temporal {
    private static int temporalCount = 1;
    private String temporalLiteral;

    public Temporal() {
        int index = this.temporalCount++;
        this.temporalLiteral = "t" + index;
    }

    public Temporal(String temporalLiteral) {
        this.temporalLiteral = temporalLiteral;
    }

    public String getTemporal() {
        return temporalLiteral;
    }
    
    
    
    
}
