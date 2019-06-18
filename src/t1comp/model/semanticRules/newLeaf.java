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
    private SemanticTable table;
    private Integer id0;
    private String attribute0;
    private String name;
    
    public newLeaf(Integer i0, String a0,String n) {
        table = SemanticTable.getInstance();
        id0 = i0;
        this.attribute0 = a0;
        this.name = n;
    }
    
    @Override
    public Integer action() {
        SemanticNode node0 = table.getNode(id0);
        
        node0.addAtribute(attribute0, new SemanticNode(table.genId(),name,null));
        table.addNode(node0);
//        System.out.println("Creating new leaf...\n");
//        SemanticNode newLeaf = new SemanticNode(st.genId(), receptor);
//        newLeaf.addAtribute(attribute, value);
//        st.addNode(newLeaf);
//        st.currentNode = newLeaf.getId();
//        return newLeaf.getId();
        return 0;
    }
    
}
