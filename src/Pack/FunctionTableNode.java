package Pack;

import java.util.ArrayList;

public class FunctionTableNode extends SymbolTableNode {
    private String return_type;
    private ArrayList<String> params;
    private boolean hasReturn;
    private String child_scope;

    public FunctionTableNode(String Id, String Scope, String return_type, String child_scope ){
        super(Id,Scope);
        this.setReturn_type(return_type);
        this.setParams(new ArrayList<String>());
        this.setHasReturn(false);
        this.setChild_scope(child_scope);
    }

    public void Add(String variable){
        this.params.add(variable);
    }

    public String getChild_scope() {
        return child_scope;
    }

    public void setChild_scope(String child_scope) {
        this.child_scope = child_scope;
    }

    public boolean isHasReturn() {
        return hasReturn;
    }

    public void setHasReturn(boolean hasReturn) {
        this.hasReturn = hasReturn;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

}