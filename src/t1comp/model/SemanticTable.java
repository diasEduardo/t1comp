/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author nathangodinho
 */
public final class SemanticTable {
    public HashMap<Integer, ArrayList<SemanticRule>> table;
    private static final SemanticTable instance = new SemanticTable();
    private HashMap<Integer, SemanticNode> nodes;
    private static Integer idTracker = 0;
    public static int currentNode = 0;
    public SemanticTable() {
        table = new HashMap<>();
        nodes = new HashMap<>();
    }
    
    public static SemanticTable getInstance() {
        return instance;
    }
    
    public void addRule(Integer id, SemanticRule rule) {
//        System.out.println("Semantic rule applied for " + nodes.get(id).getName());
        if(table.containsKey(id)){
            ArrayList<SemanticRule> rules = table.get(id);
            rules.add(rule);
            table.put(id, rules);
        }
        else{
            table.put(id, new ArrayList<>(Arrays.asList(rule)));
        }
    }
    
    public void addRule(Integer id, ArrayList<SemanticRule> rules) {
//        System.out.println("Semantic rule applied for " + nodes.get(id).getName());
        table.put(id, rules);
    }
    
    public ArrayList<SemanticRule> getRule(Integer id) {
        return table.get(id);
    }
    
    public void clean() {
        table.clear();
    }
    
    public void callAllActions() {
//        table;
    }
    
    public SemanticNode getNode(int ID) {
        return nodes.get(ID);
    }
    
    public void addNode(SemanticNode node) {
        nodes.put(node.getId(), node);
    }
    
    public Integer genId() {
        return idTracker++;
    }
}
