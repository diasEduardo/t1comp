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
    private HashMap<String, String> attributes;
    private boolean isLeaf;
    private ArrayList<SemanticNode> children;
    private SemanticNode parent;
    
    public SemanticNode(String name, SemanticNode parent) {
        this.id = gentId();
        this.name = name;
        attributes = new HashMap<>();
        children = new ArrayList<>();
        this.parent = parent;
        if (parent == null) {
            idTracker =  0;
        }
//        System.out.println("Creating: " + this.name + "| From parent: " + this.parent);
    }
    
    public int gentId() {
        return idTracker++;
    }
    
    public void addChild(SemanticNode child) {
        children.add(child);
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
    
    public void addAtribute(String name, String value) {
        attributes.put(name, value);
    }
    
    public String getAttributeValue(String name) {
        return attributes.get(name);
    } 
    
    @Override
    public String toString() {
        System.out.println(name);
        return name;
    }
}
