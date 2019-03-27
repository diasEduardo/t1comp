/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author nathan
 */
public final class SymbolsTable {
    public ArrayList<TableEntry> tabela;
    private static final SymbolsTable instance = new SymbolsTable();
    
    private SymbolsTable() {
        tabela = new ArrayList<>();
        clean();
    }
    
    public static SymbolsTable getInstance() {
        return instance;
    }
    
    public ArrayList<TableEntry> getTable () {
        return tabela;
    }
    
    public void addToken(TableEntry newEntry) {
        tabela.add(newEntry);
    }
    
    public void clean() {
        tabela.clear();
    }
    
    public void display () {
        System.out.println("## Symbols Table: \n");
        
        for (TableEntry entry: tabela) {
            System.out.println (
                "Token: " + entry.getToken().toString() + 
                " | Lexeme: " + entry.getLexeme() + 
                " | Line: " + entry.getLine() +
                " | Column: " + entry.getColumn()
            );
        }
       
    }
}
