/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;
import java.util.ArrayList;
import t1comp.model.SemanticNode;
import t1comp.model.SemanticRule;
import t1comp.model.SemanticTable;
/**
 *
 * @author nathangodinho
 */
public class atributeAssertion implements SemanticRule<Integer>{
    private ArrayList<Integer> ids;
    private ArrayList<String> attributes;
    private final SemanticTable table;
    
    
    public atributeAssertion(Integer i0,String a0,Integer i1, String a1) {
        ids.add(i0);
        ids.add(i1);
        attributes.add(a0);
        attributes.add(a1);
        table = SemanticTable.getInstance();
    
    }
    
    @Override
    public Integer action() {
        SemanticNode node0 = table.getNode(ids.get(0));
        SemanticNode node1 = table.getNode(ids.get(1));
        
        node1.addAtribute(attributes.get(0), node1.getAttributeValue(attributes.get(1)));
        table.addNode(node0);
//        SemanticNode n
        return 0;
    }
    
}
