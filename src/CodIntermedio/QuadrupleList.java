/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodIntermedio;

import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class QuadrupleList {
    private ArrayList<Quadruple> list;

    public QuadrupleList() {
        this.list = new ArrayList();
    }
    
    public void add(Quadruple n) {
        list.add(n);
    }
    
    public Quadruple elementAt(int i) {
        return list.get(i);
    }
    
    public int size() {
        return list.size();
    }
    
    public QuadrupleList merge(QuadrupleList other) {
        ArrayList<Quadruple> neoList = new ArrayList();
        neoList.addAll(this.list);
        neoList.addAll(other.list);
        QuadrupleList neo = new QuadrupleList();
        neo.list = neoList;
        return neo;
    }
}
