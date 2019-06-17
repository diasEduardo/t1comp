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
    public HashMap<String, ArrayList<SemanticRule>> table;
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
    
    public void addRule(String production, String derivation, SemanticRule rule) {
        table.put(production.concat(derivation), new ArrayList<>(Arrays.asList(rule)));
    }
    
    public void addRule(String production, String derivation, ArrayList<SemanticRule> rules) {
        table.put(production.concat(derivation), rules);
    }
    
    public ArrayList<SemanticRule> getRule(String production, String derivation) {
        return table.get(production.concat(derivation));
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
