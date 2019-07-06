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
    private String attValue;
    
    public atributeAssertion(Integer i0,String a0,Integer i1, String a1) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        ids.add(i1);
        attributes.add(a0);
        attributes.add(a1);
        table = SemanticTable.getInstance();
    }
    
    public atributeAssertion(Integer i0,String a0, String value) {
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
            
            if (attributes.get(0) == "addr") {
                node0.addr = attValue;
            } else if (attributes.get(0) == "addher") {
                node0.addrher = attValue;
            } else if (attributes.get(0) == "code") {
                node0.code = attValue;
            }
            
            System.out.println("Atribuindo: " 
                + node0.getName() + "." + attributes.get(0).toString() + " <- " + attValue);
        } else {
            SemanticNode node1 = table.getNode(ids.get(1));
            
            if (attributes.get(0) == "addr") {
                if (attributes.get(1) == "addr") {
                    node0.addr = node1.addr;
                } else if (attributes.get(1) == "addrher") {
                    node0.addr = node1.addrher;
                }
            } else if (attributes.get(0) == "addher") {
                if (attributes.get(1) == "addr") {
                    node0.addrher = node1.addr;
                } else if (attributes.get(1) == "addrher") {
                    node0.addrher = node1.addrher;
                }  
            } else if(attributes.get(0) == "code"){
                if (attributes.get(1) == "code") {
                    node0.code = node1.code;
                }
            } else {
                node1.addAtribute(attributes.get(0), node1.getAttributeValue(attributes.get(1)));
            }
            
            System.out.println("Atribuindo: " 
                + node0.getName() + "." + attributes.get(0).toString() + " <- " 
                + node1.getName()+ "." + attributes.get(1).toString());
        }
        
        table.addNode(node0);
        
      
        return 0;
    }
    
}
