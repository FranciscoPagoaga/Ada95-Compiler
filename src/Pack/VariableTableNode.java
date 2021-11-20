package Pack;

public class VariableTableNode extends SymbolTableNode {
    private String type; 
    private int form;

    public static final int PARAM = 0;
    public static final int IN = 1;
    public static final int OUT = 2;
    public static final int INOUT = 3;

    public VariableTableNode(String Id, String Scope, String type, int form){
        super(Id, Scope);
        this.setType(type);
        this.setForm(form);
    }
    

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public int getForm() {
        return form;
    }


    public void setForm(int form) {
        this.form = form;
    }

    public boolean isParam(){
        if (form == 0) {
            return false;
        }else{
            return true;
        }
    }

    
}