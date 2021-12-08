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
public class Label {
    private static int labelCount = 1;
    private int labelName;

    public Label() {
        //this(labelCount++);
        this.labelName = labelCount;
    }

    public Label(int labelName) {
        this.labelName = labelName;
    }

    public void setLabelName(int labelName) {
        this.labelName = labelName;
    }

    public int getLabelName() {
        return labelName;
    }
    
    public void addUno(){
        this.labelCount += 1;
    }
    

}
