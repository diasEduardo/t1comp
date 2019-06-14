/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;
import t1comp.model.SemanticRule;
/**
 *
 * @author nathangodinho
 */
public class newNode implements SemanticRule<Integer>{

    private String descriptor;
    private String valueLeft;
    private static int nodeID = 1;
    
    public newNode(String receptor, String valueLeft, String valueRight) {
        this.descriptor = descriptor;
        this.valueLeft = valueLeft;
    }
    
    public newNode( String value) {
        this.valueLeft = value;
    }

    private int getNewId() {
        return nodeID++;
    }
    
    @Override
    public Integer action() {
        System.out.println("New node" + valueLeft);
        return getNewId();
    }

    
}
