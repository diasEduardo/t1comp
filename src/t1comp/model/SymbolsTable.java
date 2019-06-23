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
    
    public int[] getLinesByIndex(int index) {
        TableEntry entry = tabela.get(index);
        
        if (entry != null) {
            int[] line = {entry.getLine(), entry.getColumn()};
            
            return line;
        }
       
        return new int[2];
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
                    +""
            );
        }
        
        return printTable;
    } 
    
    public void display () {
//        System.out.println(toString());
    }
    
    public void addType(int index, String type) {
        tabela.get(index).setType(type);
    }
    
    public String getSymbol(int index) {
        return tabela.get(index).getLexeme();
    }
    
//    TODO it is not a steing, need to check type before setting the return value
//    public <E> getIdValue(String ident, String type) { <-- eg
    public String getIdValue(String ident) {
        return ident;
    }
}
