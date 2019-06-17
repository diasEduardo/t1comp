/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package t1comp.model.semanticRules;
import t1comp.model.SemanticRule;
import t1comp.model.SemanticTable;
import t1comp.model.SemanticNode;
/**
 *
 * @author nathangodinho
 */
public class newLeaf implements SemanticRule<Integer>{
    private SemanticTable st;
    private String receptor;
    private String attribute;
    private String value;
    
    public newLeaf(String receptor, String value) {
        st = SemanticTable.getInstance();
        String[] parts = receptor.split("\\.");;
        this.receptor = parts[0];
        this.attribute = parts[1];
        this.value = value;
    }
    
    @Override
    public Integer action() {
//        System.out.println("Creating new leaf...\n");
//        SemanticNode newLeaf = new SemanticNode(st.genId(), receptor);
//        newLeaf.addAtribute(attribute, value);
//        st.addNode(newLeaf);
//        st.currentNode = newLeaf.getId();
//        return newLeaf.getId();
        return 0;
    }
    
}
