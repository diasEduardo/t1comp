/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

/**
 *
 * @author nathangodinho
 * OBS: Considerando "" como $
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
        
        table.add("CLASSDECL", "class", "class ident CLASSDECL1");
        
        table.add("CLASSDECL1", "extends", "extends ident CLASSBODY");
        table.add("CLASSDECL1", "{", "CLASSBODY");
        
        table.add("CLASSBODY", "{", "{ CLASSBODY1");
        
        table.add("CLASSBODY1", "class", "PROGRAM CLASSBODY2");
        table.add("CLASSBODY1", "ident", "CLASSBODY2");
        table.add("CLASSBODY1", "}", "CLASSBODY2");
        table.add("CLASSBODY1", "int", "CLASSBODY2");
        table.add("CLASSBODY1", "string", "CLASSBODY2");
        table.add("CLASSBODY1", "constructor", "CLASSBODY2");
        
        table.add("CLASSBODY2", "ident", "VARDECLTYPE CLASSBODY22");
        table.add("CLASSBODY2", "}", "}");
        table.add("CLASSBODY2", "int", "VARDECLTYPE CLASSBODY22");
        table.add("CLASSBODY2", "string", "VARDECLTYPE CLASSBODY22");
        
        table.add("CLASSBODY22", "ident", "ident CLASSBODY23");
        table.add("CLASSBODY22", "[", "VARDECLBRACKETS ident METHODBODY CLASSBODYMETHODDECL1 }");
        
        table.add("CLASSBODY23", "class", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "ident", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "}", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", ";", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "int", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "string", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "[", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", ",", "VARDECL1 ; CLASSBODY2");
        table.add("CLASSBODY23", "(", "METHODBODY CLASSBODYMETHODDECL1 }");
        table.add("CLASSBODY23", "", "VARDECL1 ; CLASSBODY2");
        
        table.add("CLASSBODY3", "ident", "CLASSBODY4");
        table.add("CLASSBODY3", "}", "CLASSBODY4");
        table.add("CLASSBODY3", "int", "CLASSBODY4");
        table.add("CLASSBODY3", "string", "CLASSBODY4");
        
        table.add("CLASSBODY4", "ident", "CLASSBODYMETHODDECL }");
        table.add("CLASSBODY4", "}", "CLASSBODYMETHODDECL }");
        table.add("CLASSBODY4", "int", "CLASSBODYMETHODDECL }");
        table.add("CLASSBODY4", "string", "CLASSBODYMETHODDECL }");
        
        table.add("CLASSBODYVARDELC", "ident", "VARDECL ; CLASSBODYVARDELC1");
        table.add("CLASSBODYVARDELC", "int", "VARDECL ; CLASSBODYVARDELC1");
        table.add("CLASSBODYVARDELC", "string", "VARDECL ; CLASSBODYVARDELC1");
        
        table.add("CLASSBODYVARDELC1", "ident", "CLASSBODYVARDELC");
        table.add("CLASSBODYVARDELC1", "int", "CLASSBODYVARDELC");
        table.add("CLASSBODYVARDELC1", "string", "CLASSBODYVARDELC");
        
        table.add("CLASSBODYCONSTRUCTDECL", "constructor", "CONSTRUCTDECL CLASSBODYCONSTRUCTDECL1");
        
        table.add("CLASSBODYCONSTRUCTDECL1", "constructor", "CLASSBODYMETHODDECL");
        table.add("CLASSBODYCONSTRUCTDECL1", "}", "");
        table.add("CLASSBODYCONSTRUCTDECL1", "int", "CLASSBODYMETHODDECL");
        table.add("CLASSBODYCONSTRUCTDECL1", "string", "CLASSBODYMETHODDECL");
        
        
        table.add("VARDECL", "ident", "VARDECLTYPE ident VARDECL1");
        table.add("VARDECL", "int", "VARDECLTYPE ident VARDECL1");
        table.add("VARDECL", "string", "VARDECLTYPE ident VARDECL1");
        
        table.add("VARDECL1", ";", "VARDECL2");
        table.add("VARDECL1", "[", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECL1", ",", "VARDECL2");
        
        table.add("VARDECL2", ";", "");
        table.add("VARDECL2", ",", "VARDECLWITHCOMA");
        
        table.add("VARDECLTYPE", "ident", "ident");
        table.add("VARDECLTYPE", "int", "int");
        table.add("VARDECLTYPE", "string", "string");
        
        table.add("VARDECLBRACKETS", "[", "[ ] VARDECLBRACKETS1");
        
        table.add("VARDECLBRACKETS1", "ident", "");
        table.add("VARDECLBRACKETS1", ";", "");
        table.add("VARDECLBRACKETS1", "[", "VARDECLBRACKETS");
        table.add("VARDECLBRACKETS1", ",", "");
        table.add("VARDECLBRACKETS1", ")", "");
        
        table.add("VARDECLWITHCOMA", ",", ", ident VARDECLWITHCOMA1");
        
        table.add("VARDECLWITHCOMA1", ";", "");
        table.add("VARDECLWITHCOMA1", "[", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECLWITHCOMA1", ",", "VARDECLWITHCOMA");
        
        table.add("CONSTRUCTDECL", "constructor", "constructor METHODBODY");
                
        table.add("METHODDECL", "ident", "VARDECLTYPE METHODDECL1");
        table.add("METHODDECL", "int", "VARDECLTYPE METHODDECL1");
        table.add("METHODDECL", "string", "VARDECLTYPE METHODDECL1");
                        
        table.add("METHODDECL1", "ident", "ident METHODBODY");
        table.add("METHODDECL1", "[", "VARDECLBRACKETS ident METHODBODY");
        
        table.add("METHODBODY", "(", "( METHODBODY1");
        
        table.add("METHODBODY1", "ident", "PARAMLIST ) STATEMENT");
        table.add("METHODBODY1", "int", "PARAMLIST ) STATEMENT");
        table.add("METHODBODY1", "string", "PARAMLIST ) STATEMENT");
        table.add("METHODBODY1", ")", ") STATEMENT");
        
        table.add("PARAMLIST", "ident", "VARDECLTYPE ident PARAMLIST12");
        table.add("PARAMLIST", "int", "VARDECLTYPE ident PARAMLIST12");
        table.add("PARAMLIST", "string", "VARDECLTYPE ident PARAMLIST12");
        
        table.add("PARAMLIST12", "[", "VARDECLBRACKETS PARAMLIST13");
        table.add("PARAMLIST12", ",", "PARAMLIST2");
        table.add("PARAMLIST12", ")", "");
                
        table.add("PARAMLIST13", ")", "");
        table.add("PARAMLIST13", ",", "PARAMLIST2");
        
        table.add("PARAMLIST2", ",", ", VARDECLTYPE ident PARAMLIST12");
        
       
        
        
//        TODO
        
        
        return table;
    }
}
