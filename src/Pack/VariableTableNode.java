package Pack;

public class VariableTableNode extends SymbolTableNode {
    private String type; 
    private int form;
    private int direction;
    private String current_reg;
    private String final_direction;
    private boolean assigned;

    public static final int PARAM = 0;
    public static final int IN = 1;
    public static final int OUT = 2;
    public static final int INOUT = 3;

    public VariableTableNode(String Id, String Scope, String type, int direction, int form, boolean assigned){
        super(Id, Scope);
        this.setType(type);
        this.setDirection(direction);
        this.setForm(form);
        this.current_reg="";
        this.final_direction="";
        this.setAssigned(assigned);
    }
    

    public String getFinal_direction() {
        return final_direction;
    }


    public void setFinal_direction(String final_direction) {
        this.final_direction = final_direction;
    }


    public String getCurrent_reg() {
        return current_reg;
    }


    public void setCurrent_reg(String current_reg) {
        this.current_reg = current_reg;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public int getDirection() {
        return direction;
    }


    public void setDirection(int direction) {
        this.direction = direction;
    }


    public int getForm() {
        return form;
    }


    public void setForm(int form) {
        this.form = form;
    }


    public boolean isAssigned() {
        return assigned;
    }


    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }


    public boolean isParam(){
        if (form == 0) {
            return false;
        }else{
            return true;
        }
    }

    
}
