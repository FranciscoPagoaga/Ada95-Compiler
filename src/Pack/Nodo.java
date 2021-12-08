
package Pack;

import CodIntermedio.IntermediateExpression;
import java.util.ArrayList;



public class Nodo {
    private String nombre;
    private ArrayList<Nodo> hijos;
    private String valor;
    private int numNodo;
    private String e_lugar;
    private String verdadero;
    private String falso; 
    private String siguiente;
    
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
    }

    public String getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(String siguiente) {
        this.siguiente = siguiente;
    }

    public String getFalso() {
        return falso;
    }

    public void setFalso(String falso) {
        this.falso = falso;
    }

    public String getVerdadero() {
        return verdadero;
    }

    public void setVerdadero(String verdadero) {
        this.verdadero = verdadero;
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
