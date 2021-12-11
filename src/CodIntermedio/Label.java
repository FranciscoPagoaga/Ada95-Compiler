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
    private static int labelCount = 0;
    private String labelName;

    public Label() {
        this.labelName="etiq"+labelCount++;
    }

    public Label(String labelName) {
        this.labelName = labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String toString() {
        return labelName;
    }
    

}
