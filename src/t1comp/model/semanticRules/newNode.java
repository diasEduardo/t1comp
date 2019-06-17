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
public class newNode implements SemanticRule<Integer>{

    private Integer id1;
    private String attribute1;
    private Integer id2;
    private String attribute2;
    private Integer id3;
    private String attribute3;
    private Integer id4;
    private String attribute4;
    private final SemanticTable table;
    
    public newNode(Integer i1,String a1,Integer i2, String a2,Integer i3,String a3) {
        id1 = i1;
        attribute1 = a1;
        id2 = i2;
        attribute2 = a2;
        table = SemanticTable.getInstance();
        id3 = i3;
        attribute3 = a3;

    }
    @Override
    public Integer action() {
        SemanticNode node1 = table.getNode(id1);
        SemanticNode node2 = table.getNode(id2);
        SemanticNode node3 = table.getNode(id3);
  
        
        SemanticNode newNode = new SemanticNode(table.genId(),node2.getAttributeValue(attribute2),node3.getAttributeValue(attribute3));
        table.addNode(newNode);
        node1.addAtribute(attribute1, newNode);
        
        return 0;
    }

    
}
