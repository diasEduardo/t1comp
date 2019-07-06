/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nathangodinho
 */
public final class SemanticNode {
    private final Integer id;
    private String name;
    private HashMap<String, Integer> attributes;
    private boolean isLeaf;
    private ArrayList<Integer> children;
    private int parent;
    private int tableId;
    private SemanticTable smt = SemanticTable.getInstance();
    public static final int NULL_PARENT = -1;
    private String nodeValue;
    public String code, addr;

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
    
    public SemanticNode(Integer i, String name, Integer parent) {
        this.id = i;
        this.name = name;
        attributes = new HashMap<>();
        children = new ArrayList<>();
        
        this.parent = parent;
//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    public SemanticNode(Integer i, Integer i1,Integer i2) {
        this.id = i;
        this.name = "";
        attributes = new HashMap<>();
        children = new ArrayList<>();
        
        this.parent = i1;
        this.addChild(i2);

//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    public SemanticNode(Integer i, Integer i1, Integer i2, Integer i3) {
        this.id = i;
        this.name = "";
        attributes = new HashMap<>();
        children = new ArrayList<>();
       
        
        this.parent = i1;
        this.addChild(i2);
        this.addChild(i3);

//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    
    public void addChild(Integer child) {
        children.add(0, child);
    }
    
    public SemanticNode getChild() {
        return smt.getNode(children.get(0));
    }
    
    public SemanticNode getChild(int index) {
        return smt.getNode(children.get(index));
    }
    
    public Integer getChildTableId() {
        return children.get(0);
    }
    
    public Integer getChildTableId(int index) {
        return children.get(index);
    }
    
    
    public ArrayList<Integer> getChildrenList() {
     return children;   
    }
    
    public ArrayList<SemanticNode> getChildren() {
        ArrayList<SemanticNode> children = new ArrayList<>();
        for (Integer childId : getChildrenList()) {
            children.add(smt.getNode(childId));
        }
        
        return children;   
    }
    
    public SemanticNode(Integer id) {
        this.id = id;
    }
    
    
    public Integer getId() {
        return id;
    }
    
    public void addAtribute(String name, Integer value) {
        attributes.put(name, value);
    }
    
    public Integer getAttributeValue(String name) {
        if(attributes.containsKey(name)){
            return attributes.get(name);
        }
        return null;
    } 
    
    @Override
    public String toString() {
        System.out.println(name);
        return name;
    }

    public String getName() {
        return name;
    }
    
    public int getParent() {
        return parent;
    }
    
    public boolean isRoot() {
        return parent == NULL_PARENT;
    }
}
