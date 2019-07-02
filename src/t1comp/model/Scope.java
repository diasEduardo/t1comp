/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;

/**
 *
 * @author adribeiro
 */
public class Scope {
    private ArrayList<String> variables;
    boolean insideFor;
    
    public Scope(boolean inFor){
        insideFor = inFor;
        variables = new ArrayList<String>();
    }
    
    public boolean hasVariable(String v){
        return variables.contains(v);
    }
    
    public void addVariable(String v){
        if(!variables.contains(v)){
            variables.add(v);
        }
    }

    public boolean isInsideFor() {
        return insideFor;
    }

    public void setInsideFor(boolean insideFor) {
        this.insideFor = insideFor;
    }
    
    
    
}
