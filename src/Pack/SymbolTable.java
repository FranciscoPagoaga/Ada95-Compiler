package Pack;

import java.util.ArrayList;

public class SymbolTable {
    private ArrayList<SymbolTableNode> symbolList;
    public static int integer_size = 4;
    public static int float_size = 8;
    public static int boolean_size = 1;
    public static int string_size = 4;

    public SymbolTable(){
        symbolList = new ArrayList<>();
    }

    public static int sizeOf(String type){
        if(type.equals("integer")){
            return integer_size;
        } else if(type.equals("float")){
            return float_size;
        } else if(type.equals("boolean")){
            return boolean_size;
        } else if(type.equals("string")){
            return string_size;
        }else{
            return -1;
        }
    }
    
    // pendiente: se encarga de activar las variables del scope actual
    // y desactivar variables en scopes padres con mismo nombre
    // o variables en scope diferente 
    public void activateWithScope(String scope){
        
    }

    public SymbolTableNode findSymbol(String id, String scope){
        for (int i = 0; i < symbolList.size(); i++) {
            //pendiente: a este if agregarle && symbolList.get(i).isActive()
            if( symbolList.get(i).getId().equals(id)){
                //comparar scopes
                if(symbolList.get(i).getScope().equals(scope)){
                    //ya existe
                    return symbolList.get(i);
                }
                else {
                    String []existingScope = symbolList.get(i).getScope().split(".");
                    String []testScope = scope.split(".");
                    
                    // si tienen el mismo tamaño son scopes hermanos
                    if(!(existingScope.length == testScope.length)){
                        boolean ancestry = true;
                        for (int j = 0; j < existingScope.length && j < testScope.length; j++) {
                            if(!existingScope[j].equals(testScope[j])){
                                ancestry = false;
                                break;
                            }
                        }
                        if(ancestry){
                            // test scope lenght aqui deberia ser mas grande de todos modos activateWithScope deberia
                            // desactivar la variable en el scope padre cuando se llega al hermano
                            if(testScope.length > existingScope.length){
                                symbolList.get(i).setActive(false);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean addSymbol(SymbolTableNode symbol){
        if(findSymbol(symbol.getId(), symbol.getScope()) != null){
          return false;
        }
        symbolList.add(symbol);
        return true;
    }

    public FunctionTableNode getFunction(String scope, String id){
        for (int i = 0; i < this.symbolList.size(); i++) {
            if (this.symbolList.get(i) instanceof FunctionTableNode && this.symbolList.get(i).Scope.equals(scope) && this.symbolList.get(i).Id.equals(id)) {
                return (FunctionTableNode)this.symbolList.get(i); 
            }
        }
        return null;
    }

    public ArrayList<FunctionTableNode> getAllFunctions (){
        ArrayList<FunctionTableNode> functions = new ArrayList();
        for(int i=0; i<this.symbolList.size(); i++){
            if(this.symbolList.get(i) instanceof FunctionTableNode){
                functions.add((FunctionTableNode) this.symbolList.get(i));
            }
        }
        return functions;
    }

    public ArrayList<VariableTableNode> getAllVars( String scope){
        ArrayList<VariableTableNode> result = new ArrayList();
        for (int i = 0; i < this.symbolList.size(); i++) {
            if (this.symbolList.get(i) instanceof VariableTableNode && this.symbolList.get(i).Scope.equals(scope)) {
                result.add((VariableTableNode)this.symbolList.get(i));
            }
    
        }
        
        return result;
    }
}
