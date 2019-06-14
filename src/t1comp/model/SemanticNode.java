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
public class SemanticNode {
    private final Integer id;
    private String name;
    private HashMap<String, String> attributes;

    public SemanticNode(Integer id, String name) {
        this.id = id;
        this.name = name.concat("- "+ id);
        attributes = new HashMap<>();
    }
    
    public SemanticNode(Integer id, String name, Integer leftNode, Integer rightNode) {
        this.id = id;
        this.name = name.concat("- "+ id);
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
