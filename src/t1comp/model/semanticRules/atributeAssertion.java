/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;
import t1comp.model.SemanticNode;
import t1comp.model.SemanticRule;
import t1comp.model.SemanticTable;
/**
 *
 * @author nathangodinho
 */
public class atributeAssertion implements SemanticRule<Integer>{
    private Integer id1;
    private String attribute1;
    private Integer id2;
    private String attribute2;
    private final SemanticTable table;
    
    
    public atributeAssertion(Integer i1,String a1,Integer i2, String a2) {
        id1 = i1;
        attribute1 = a1;
        id2 = i2;
        attribute2 = a2;
        table = SemanticTable.getInstance();
    
    }
    
    @Override
    public Integer action() {
        SemanticNode node1 = table.getNode(id1);
        SemanticNode node2 = table.getNode(id2);
        
        node1.addAtribute(attribute1, node2.getAttributeValue(attribute2));
//        SemanticNode n
        return 0;
    }
    
}
