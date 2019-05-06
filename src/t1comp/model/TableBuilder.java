/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

/**
 *
 * @author nathangodinho
 */
public class TableBuilder {
    public static LL1Table buildTable () {
        LL1Table table = new LL1Table();
        
        table.add("PROGRAM", "class", "CLASSLIST");
        
        table.add("CLASSLIST", "class", "CLASSDECL CLASSLIST1");
        
        table.add("CLASSLIST1", "class", "CLASSLIST");
        table.add("CLASSLIST1", "ident", "");
        table.add("CLASSLIST1", "}", "");
        table.add("CLASSLIST1", "int", "");
        table.add("CLASSLIST1", "string", "");
        table.add("CLASSLIST1", "constructor", "");
        table.add("CLASSLIST1", "", "");
        
        return table;
    }
}
