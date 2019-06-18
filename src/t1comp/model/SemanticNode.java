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
    private static int idTracker = 0;
    private final Integer id;
    private String name;
    private HashMap<String, SemanticNode> attributes;
    private boolean isLeaf;
    private ArrayList<SemanticNode> children;
    private SemanticNode parent;
    private ArrayList<SemanticNode> node;
    
    
    public SemanticNode(Integer i, String name, SemanticNode parent) {
        this.id = i;
        this.name = name;
        attributes = new HashMap<>();
        children = new ArrayList<>();
        node = new ArrayList<>();
        this.parent = parent;
        if (parent == null) {
            idTracker =  0;
        }
//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    public SemanticNode(Integer i, SemanticNode i2,SemanticNode i3) {
        this.id = i;
        attributes = new HashMap<>();
        node = new ArrayList<>();
        
        node.add(i2);
        node.add(i3);

//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    public int gentId() {
        return idTracker++;
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
