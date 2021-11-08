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
        }else if(nodo.getNombre().equals("FUNCTION_BLOCK")||nodo.getNombre().equals("PROCEDURE_BLOCK")){
            addFunctionBlock(nodo);
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
        String returnType="void";// si es un procedure el tipo de valor de retorno es void
        Nodo parametros=null;
        //Nodo id_list = null;
        
        for (Nodo hijo : nodo.getHijos()) {
            if(hijo.getNombre().equals("RETURN_TYPE")){//solo en FUNCTION_BLOCK
                returnType = hijo.getValor();
            }
            if (hijo.getNombre().equals("MAIN_PARAMETERS")) {
                parametros = hijo; // parametros.parameters 
            }  
            
        }
        
        // parametros 
        
       
        
        // por aqui deber ir la funcion para añadir los parametros
        
        
        //por aqui se va agregando todo al objeto funtionTableNode
        
        FunctionTableNode tmpfnode = new FunctionTableNode(nodo.getHijos().get(0).getValor(), "s0", returnType, "s0");
         
        
        for (int i = 0; i < parametros.getHijos().size(); i++) {
            Nodo actualNode = parametros.getHijos().get(i); 
            System.out.println(actualNode.getNombre());
            agregar_parametros_a_tmpfnode(tmpfnode, actualNode);
        }
        
        
        
    }
    
    
    /*
    public ArrayList<VariableTableNode> addParameters(Nodo nodo){
        FuntionTableNode tmpfnode = FunctionTableNode()
        
        for (Nodo Hijo : nodo.getHijos()) {
            
        }
    }*/
    
    public void agregar_parametros_a_tmpfnode(FunctionTableNode tmpnode, Nodo nodo/*each parameter_specification*/){
        //Se necesita el ultimo nodo para saber el tipo
        String tipo = nodo.getHijos().get(nodo.getHijos().size()-1).getValor();
        //Se debe iterar todos menos el ultimo tipo, debido a que no es un id 
        String parameter_mode = nodo.getHijos().get(nodo.getHijos().size()-2).getValor();
        
        int parameter_mode_int ; 
        
        switch (parameter_mode) {
            case "In":
                parameter_mode_int = 1;
                break;
            case "Out":
                parameter_mode_int = 2;
                break;
            case "In Out":
                parameter_mode_int = 3; 
                break;
            default:
                parameter_mode_int = 0;// en caso de null ahora 1
                break;
        }
        
        Nodo ID_LIST = nodo.getHijos().get(0);
        
        ArrayList<VariableTableNode> params = new ArrayList<>();
        
        //iterar sobre ID_LIST
        for (int i = 0; i < ID_LIST.getHijos().size(); i++) {
            Nodo actualNode = ID_LIST.getHijos().get(i);
            VariableTableNode tmpvnode = new VariableTableNode(actualNode.getValor(), "s0", tipo,parameter_mode_int );
            //agrega variable a lista de simbolos, retorna true si se pudo, false si ya existia
            System.out.println(tmpvnode.getId());
            System.out.println(tmpvnode.getType());
            System.out.println(tmpvnode.getForm());
            /*if (!this.symbolTable.addSymbol(tmpvnode)) {
                System.out.println("El identificador ya esta declarado");
            }else{
                //si agrega
                params.add(tmpvnode);
            }*/
            
            params.add(tmpvnode);
        }
        
        //set params
        tmpnode.setParams(params);
        
        
    }
    
    
    


}
