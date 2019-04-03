/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;

/**
 *
 * @author nathan
 */
public final class SymbolsTable {
    //precisa ser privado e não pode remover itens do arraylist por questões de referencia
    private ArrayList<TableEntry> tabela;
    //precisa ser privado e não pode remover itens do arraylist por questões de referencia
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
    
    public int getIndex(){
        return tabela.size();
    }
    
    public int addEntry(TableEntry newEntry) {
        tabela.add(newEntry);
        return tabela.size();
    }
    
    public void clean() {
        tabela.clear();
    }
    
    @Override
    public String toString() {
        String printTable = "## Symbols Table: \n";
        for (TableEntry entry: tabela) {
            printTable = printTable.concat(
                "Token: " + entry.getToken().toString() + 
                " | Lexeme: " + entry.getLexeme() + 
                " | Line: " + entry.getLine() +
                " | Column: " + entry.getColumn()
                    +"\n"
            );
        }
        
        return printTable;
    } 
    
    public void display () {
//        System.out.println(toString());
    }
}
