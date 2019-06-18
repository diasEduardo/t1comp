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
    private HashMap<String, SemanticNode> attributes;
    private boolean isLeaf;
    private ArrayList<SemanticNode> children;
    private SemanticNode parent;
    private ArrayList<SemanticNode> nodes;
    
    
    public SemanticNode(Integer i, String name, SemanticNode parent) {
        this.id = i;
        this.name = name;
        attributes = new HashMap<>();
        children = new ArrayList<>();
        nodes = new ArrayList<>();
        this.parent = parent;
//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    public SemanticNode(Integer i, SemanticNode i2,SemanticNode i3) {
        this.id = i;
        attributes = new HashMap<>();
        nodes = new ArrayList<>();
        
        nodes.add(i2);
        nodes.add(i3);

//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    public SemanticNode(Integer i, SemanticNode i1,SemanticNode i2,SemanticNode i3) {
        this.id = i;
        attributes = new HashMap<>();
        nodes = new ArrayList<>();
        
        nodes.add(i1);
        nodes.add(i2);
        nodes.add(i3);

//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    
    public void addChild(SemanticNode child) {
        children.add(0, child);
    }
    
    public SemanticNode getChild() {
        return children.get(0);
    }
    
    public SemanticNode getChild(int index) {
        return children.get(index);
    }
    
    
    public ArrayList<SemanticNode> getChildren() {
     return children;   
    }
    
    public SemanticNode(Integer id) {
        this.id = id;
    }
    
    
    public Integer getId() {
        return id;
    }
    
    public void addAtribute(String name, SemanticNode value) {
        attributes.put(name, value);
    }
    
    public SemanticNode getAttributeValue(String name) {
        return attributes.get(name);
    } 
    
    @Override
    public String toString() {
        System.out.println(name);
        return name;
    }

    public String getName() {
        return name;
    }
    
}
