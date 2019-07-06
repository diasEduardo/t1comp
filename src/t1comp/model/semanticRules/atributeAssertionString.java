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
public class atributeAssertionString implements SemanticRule<Integer>{
    private ArrayList<Integer> ids;
    private ArrayList<String> attributes;
    private final SemanticTable table;
    private String attValue;
    
    public atributeAssertionString(Integer i0,String a0,Integer i1, String a1) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        ids.add(i1);
        attributes.add(a0);
        attributes.add(a1);
        table = SemanticTable.getInstance();
    }
    
    public atributeAssertionString(Integer i0,String a0, String value) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        attributes.add(a0);
        this.attValue = value;
        table = SemanticTable.getInstance();
    }
    
    @Override
    public Integer action() {
        SemanticNode node0 = table.getNode(ids.get(0));
        
        if (attValue != null) {
            node0.stringAttributes.put(attributes.get(0), attValue);
            
            System.out.println("Atribuindo: " 
                + node0.getName() + "." + attributes.get(0).toString() + " <- " + attValue);
        } else {
            SemanticNode node1 = table.getNode(ids.get(1));
            
            node0.stringAttributes.put(attributes.get(0), node1.stringAttributes.get(attributes.get(1)));
            System.out.println("Value: " + node0.stringAttributes.get(attributes.get(0)));
            System.out.println("Atribuindo: " 
                + node0.getName() + "." + attributes.get(0).toString() + " <- " 
                + node1.getName()+ "." + attributes.get(1).toString());
        }
        
        table.addNode(node0);
      
        return 0;
    }
    
}
