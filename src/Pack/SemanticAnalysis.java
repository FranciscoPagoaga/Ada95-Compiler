package Pack;

import java.util.ArrayList;

public class SemanticAnalysis {
    private SymbolTable symbolTable;
    private boolean has_error;
    private String scope;
    private String current_id;

    public SemanticAnalysis(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
        has_error= false;
        scope = "";
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void Traverse(Nodo nodo){
        if(nodo.getNombre().equals("VARIABLE_DECLARATION")){
            addVariableDeclaration(nodo);
        //else if(nodo.getNombre().equals("FUNCTION")){

        }else{
            for (Nodo hijos : nodo.getHijos()) {
                Traverse(hijos);
            }
        }
    }

    // Itera sobre el nodo VARIABLE_DECLARATION para agregar id con sus tipos
    public void addVariableDeclaration(Nodo nodo){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //Se debe iterar todos menos el ultimo tipo, debido a que no es un id 

        for (int i = 0; i <= nodo.getHijos().size()-2; i++) {
            Nodo actualNode = nodo.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), "s0", tipo, 0);
            //agrega variable a lista de simbolos, retorna true si se pudo, false si ya existia
            System.out.println(tmpvnode.getId());
            if (!this.symbolTable.addSymbol(tmpvnode)) {
                System.out.println("El identificador ya esta declarado");
            }
        }
    }

    public void addFunctionBlock(Nodo nodo){
        String returnType="";
        Nodo parametros=null;
        for (Nodo hijo : nodo.getHijos()) {
            if(hijo.getNombre().equals("RETURN_TYPE")){
                returnType = hijo.getValor();
            }
            if (hijo.getNombre().equals("MAIN_PARAMETERS")) {
                parametros = hijo;
            }  
        }
        
        FunctionTableNode tmpfnode = new FunctionTableNode(nodo.getValor(), "s0", returnType, "s0");
    }

    public ArrayList<VariableTableNode> addParameters(Nodo nodo){
        for (Nodo Hijo : nodo.getHijos()) {
            
        }
    }


}
