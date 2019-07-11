/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author adribeiro
 */
public class Scope {
    private HashMap<Integer, String> variables;
    private HashMap<Integer, String> tipos;
    private int idV;
    private int id;
    private boolean insideFor;
    
    public Scope(int v,boolean inFor){
        insideFor = inFor;
        variables = new HashMap<>();
        tipos = new HashMap<>();
        idV = 0;
        id = v;
    }
    
    public boolean hasVariable(String v){
        return variables.containsValue(v);
    }
    
    public void addVariable(String v, String t){
        variables.put(idV, v);
        tipos.put(idV, t);
        idV++;
    }
    
    public boolean isInsideFor() {
        return insideFor;
    }

    public void setInsideFor(boolean insideFor) {
        this.insideFor = insideFor;
    }

    public int getId() {
        return id;
    }
    
    
    
}
