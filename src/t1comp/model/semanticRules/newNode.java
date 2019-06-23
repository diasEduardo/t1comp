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
public class newNode implements SemanticRule<Integer>{

    private ArrayList<Integer> ids;
    private ArrayList<String> attributes;
    private final SemanticTable table;
    
    public newNode(Integer i0,String a0,Integer i1, String a1,Integer i2,String a2) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        ids.add(i1);
        ids.add(i2);
        attributes.add(a0);
        attributes.add(a1);
        attributes.add(a2);
        table = SemanticTable.getInstance();

    }
    
    public newNode(Integer i0,String a0,Integer i1, String a1,Integer i2,String a2, Integer i3,String a3) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        ids.add(i1);
        ids.add(i2);
        ids.add(i3);
        attributes.add(a0);
        attributes.add(a1);
        attributes.add(a2);
        attributes.add(a3);
        table = SemanticTable.getInstance();

    }
    
    public newNode(Integer i0,String a0, String a1,String a2, Integer i3,String a3) {
        ids = new ArrayList<Integer>();
        attributes = new ArrayList<String>();
        ids.add(i0);
        ids.add(i3);
        attributes.add(a0);
        attributes.add(a1);
        attributes.add(a2);
        attributes.add(a3);
        table = SemanticTable.getInstance();

    }
    
    @Override
    public Integer action() {
        ArrayList<SemanticNode> nodes = new ArrayList<SemanticNode>();
        
        for(int i =0 ; i< ids.size();i++){  
            nodes.add(table.getNode(ids.get(i)));
        }
        if(ids.size() == 2){
            SemanticNode father = new SemanticNode(table.genId(),attributes.get(1),0);
            table.addNode(father);
            SemanticNode child = new SemanticNode(table.genId(),attributes.get(2),nodes.get(0).getId());
            table.addNode(child);
            SemanticNode newNode = new SemanticNode(table.genId(),father.getId(),child.getId(),nodes.get(1).getAttributeValue(attributes.get(3)));
            table.addNode(newNode);
            nodes.get(0).addAtribute(attributes.get(0), newNode.getId());
            table.addNode(nodes.get(0));
            System.out.println("Novo nodo: "+ nodes.get(0).getName()+"."+ attributes.get(0) + " <- "+ father.getName()+ " && " + child.getName()+ " && " + nodes.get(1).getName()+ "."+ attributes.get(3));
            
                    
        }else if(ids.size() == 3){
            SemanticNode newNode = new SemanticNode(table.genId(),
                    nodes.get(1).getAttributeValue(attributes.get(1)),nodes.get(2).getAttributeValue(attributes.get(2))); 
            table.addNode(newNode);
            nodes.get(0).addAtribute(attributes.get(0), newNode.getId());
            table.addNode(nodes.get(0));
            System.out.println("Novo nodo: "+ nodes.get(0).getName()+"."+ attributes.get(0) + " <- "+ nodes.get(1).getName()+ "."+ attributes.get(1) + " && " + nodes.get(2).getName()+"."+ attributes.get(2));
        }else if(ids.size() == 4){
            SemanticNode newNode = new SemanticNode(table.genId(),
                    nodes.get(1).getAttributeValue(attributes.get(1)),
                    nodes.get(2).getAttributeValue(attributes.get(2)),
                    nodes.get(3).getAttributeValue(attributes.get(3)));
            table.addNode(newNode);
            nodes.get(0).addAtribute(attributes.get(0), newNode.getId());
            table.addNode(nodes.get(0));
            System.out.println("Novo nodo: "+ nodes.get(0).getName()+"."+ attributes.get(0) + " <- " + nodes.get(1).getName() +"."+ attributes.get(1) + " && " + nodes.get(2).getName()+"."+ attributes.get(2)+" && " + nodes.get(3).getName()+"."+ attributes.get(3) );
        }
        
        return 0;
    }

    
}
