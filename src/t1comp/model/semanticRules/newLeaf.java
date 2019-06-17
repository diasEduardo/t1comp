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
    private Integer id1;
    private String attribute1;
    private String name;
    
    public newLeaf(Integer i1, String a1,String n) {
        table = SemanticTable.getInstance();
        id1 = i1;
        this.attribute1 = a1;
        this.name = n;
    }
    
    @Override
    public Integer action() {
        SemanticNode node1 = table.getNode(id1);
        SemanticNode node2 = new SemanticNode(table.genId(),name,null);
        
        node1.addAtribute(attribute1, node2);
        table.addNode(node2);
//        System.out.println("Creating new leaf...\n");
//        SemanticNode newLeaf = new SemanticNode(st.genId(), receptor);
//        newLeaf.addAtribute(attribute, value);
//        st.addNode(newLeaf);
//        st.currentNode = newLeaf.getId();
//        return newLeaf.getId();
        return 0;
    }
    
}
