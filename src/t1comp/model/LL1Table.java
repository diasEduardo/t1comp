/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;
import java.util.HashMap;
import java.util.HashSet;
/**
 *
 * @author nathangodinho
 */
public class LL1Table {
    public HashMap<String, HashMap<String, String>> table;
    
    public LL1Table() {
        this.table = new HashMap<String, HashMap<String, String>>();
    }
    
    public HashMap<String, HashMap<String, String>> getTabke() {
        return table;
    }
    
    public void add(String NonTerminal, String terminal, String production) {
        if (table.containsKey(NonTerminal)) {
            table.get(NonTerminal).put(terminal, production);
        } else {
            HashMap newRow = new HashMap<>();
            newRow.put(terminal, production);
            table.put(NonTerminal, newRow);
        }
    }
    
    public String get(String NonTerminal, String terminal) {
        return table.get(NonTerminal).get(terminal);
    }
    
    @Override
    public String toString() {
        String print = "";
        for (String nonTerminal : table.keySet()) {
            print += "# " + nonTerminal +  ": ";
            HashMap<String, String> tableRow = table.get(nonTerminal);
            for (String terminal : tableRow.keySet()) {
                print += terminal + " => " + tableRow.get(terminal) + " | ";
            }
            print += "\n";
        }
        return print;
    }
}
