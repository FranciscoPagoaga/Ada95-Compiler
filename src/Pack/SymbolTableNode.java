/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pack;

/**
 *
 * @author francisco
 */
public class SymbolTableNode {
    protected String Id;
    protected String Scope;
    protected boolean active;

    public SymbolTableNode(String Id, String Scope) {
        this.Id = Id;
        this.Scope= Scope;
        this.active = true;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String Scope) {
        this.Scope = Scope;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}