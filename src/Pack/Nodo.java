
package Pack;

import CodIntermedio.IntermediateExpression;
import java.util.ArrayList;



public class Nodo {
    private String nombre;
    private ArrayList<Nodo> hijos;
    private String valor;
    private int numNodo;
    private String e_lugar ; 
    
    private int fila;
    private int columna;
    
    //
    private IntermediateExpression Expression;
    
    public Nodo(String nombre)
    {
        setNombre(nombre);
        hijos = new ArrayList<>();
        //setValor("");
        setNumNodo(0);
        String e_lugar  = null;
        Expression = new IntermediateExpression();
        this.fila = -1;
        this.columna= -1;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public IntermediateExpression getExpression() {
        return Expression;
    }

    public void setExpression(IntermediateExpression Expression) {
        this.Expression = Expression;
    }
    
    

    public void setE_lugar(String e_lugar) {
        this.e_lugar = e_lugar;
    }

    public String getE_lugar() {
        return e_lugar;
    }
    
    
    
    
    public void addHijo(Nodo hijo)
    {
        hijos.add(hijo);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the hijos
     */
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the numNodo
     */
    public int getNumNodo() {
        return numNodo;
    }

    /**
     * @param numNodo the numNodo to set
     */
    public void setNumNodo(int numNodo) {
        this.numNodo = numNodo;
    }
}
