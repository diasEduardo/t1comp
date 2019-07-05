/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.Arrays;
import t1comp.model.semanticRules.atributeAssertion;
import t1comp.model.semanticRules.newLeaf;
import t1comp.model.semanticRules.newNode;

/**
 *
 * @author nathangodinho OBS: Considerando "" como $
 */
public class TableBuilder {

    public static LL1Table buildTable() {
        LL1Table table = new LL1Table();
        SemanticTable semt = SemanticTable.getInstance();

        table.add("PROGRAM", "ident", "STATEMENT");
        table.add("PROGRAM", "SEMICOMMA", "STATEMENT");
        table.add("PROGRAM", "OBRACE", "STATEMENT");
        table.add("PROGRAM", "break", "STATEMENT");
        table.add("PROGRAM", "int", "STATEMENT");
        table.add("PROGRAM", "string", "STATEMENT");
        table.add("PROGRAM", "print", "STATEMENT");
        table.add("PROGRAM", "read", "STATEMENT");
        table.add("PROGRAM", "return", "STATEMENT");
        table.add("PROGRAM", "if", "STATEMENT");
        table.add("PROGRAM", "for", "STATEMENT");

        table.add("VARDECL1", "semicomma", "VARDECL2");
        table.add("VARDECL1", "obrack", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECL1", "comma", "VARDECL2");

        table.add("VARDECL2", "semicomma", "");
        table.add("VARDECL2", "comma", "VARDECLWITHCOMA");
        
        table.add("VARDECLBRACKETS", "obrack", "obrack intconst cbrack VARDECLBRACKETS1");

        table.add("VARDECLBRACKETS1", "semicomma", "");
        table.add("VARDECLBRACKETS1", "obrack", "VARDECLBRACKETS");
        table.add("VARDECLBRACKETS1", "comma", "");

        table.add("VARDECLWITHCOMA", "comma", "comma ident VARDECLWITHCOMA1");

        table.add("VARDECLWITHCOMA1", "semicomma", "");
        table.add("VARDECLWITHCOMA1", "obrack", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECLWITHCOMA1", "comma", "VARDECLWITHCOMA");

        table.add("STATEMENT", "ident", "ident STATEMENT1");
        table.add("STATEMENT", "semicomma", "semicomma");
        table.add("STATEMENT", "obrace", "obrace STATLIST cbrace");
        table.add("STATEMENT", "break", "break semicomma");
        table.add("STATEMENT", "int", "int ident VARDECL1 semicomma");
        table.add("STATEMENT", "string", "string ident VARDECL1 semicomma");
        table.add("STATEMENT", "print", "PRINTSTAT semicomma");
        table.add("STATEMENT", "read", "READSTAT semicomma");
        table.add("STATEMENT", "return", "RETURNSTAT semicomma");
        table.add("STATEMENT", "if", "IFSTAT");
        table.add("STATEMENT", "for", "FORSTAT");

        table.add("STATEMENT1", "ident", "ident VARDECL1 semicomma");
        table.add("STATEMENT1", "at", "at NUMEXPRESSION semicomma");

        table.add("ATRIBSTAT", "ident", "LVALUE at NUMEXPRESSION");

        table.add("PRINTSTAT", "print", "print NUMEXPRESSION");

        table.add("READSTAT", "read", "read LVALUE");

        table.add("RETURNSTAT", "return", "return RETURNSTAT1");

        table.add("RETURNSTAT1", "ident", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "semicomma", "");
        table.add("RETURNSTAT1", "opar", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "intconst", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "plus", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "minus", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "stringconst", "NUMEXPRESSION");
        table.add("RETURNSTAT1", "null", "NUMEXPRESSION");

        table.add("IFSTAT", "if", "if opar NUMEXPRESSION cpar STATEMENT IFSTAT1");

        table.add("IFSTAT1", "else", "else STATEMENT end");
        table.add("IFSTAT1", "end", "end");
        
        table.add("FORSTAT", "for", "for opar ATRIBSTAT semicomma NUMEXPRESSION semicomma ATRIBSTAT cpar STATEMENT");
        
        table.add("STATLIST", "ident", "STATEMENT STATLIST1");
        table.add("STATLIST", "obrace", "STATEMENT STATLIST1");
        table.add("STATLIST", "semicomma", "STATEMENT STATLIST1");
        table.add("STATLIST", "int", "STATEMENT STATLIST1");
        table.add("STATLIST", "string", "STATEMENT STATLIST1");
        table.add("STATLIST", "break", "STATEMENT STATLIST1");
        table.add("STATLIST", "print", "STATEMENT STATLIST1");
        table.add("STATLIST", "read", "STATEMENT STATLIST1");
        table.add("STATLIST", "return", "STATEMENT STATLIST1");
        table.add("STATLIST", "super", "STATEMENT STATLIST1");
        table.add("STATLIST", "if", "STATEMENT STATLIST1");
        table.add("STATLIST", "for", "STATEMENT STATLIST1");

        table.add("STATLIST1", "ident", "STATLIST");
        table.add("STATLIST1", "obrace", "STATLIST");
        table.add("STATLIST1", "cbrace", "");
        table.add("STATLIST1", "semicomma", "STATLIST");
        table.add("STATLIST1", "int", "STATLIST");
        table.add("STATLIST1", "string", "STATLIST");
        table.add("STATLIST1", "break", "STATLIST");
        table.add("STATLIST1", "print", "STATLIST");
        table.add("STATLIST1", "read", "STATLIST");
        table.add("STATLIST1", "return", "STATLIST");
        table.add("STATLIST1", "super", "STATLIST");
        table.add("STATLIST1", "if", "STATLIST");
        table.add("STATLIST1", "for", "STATLIST");

        table.add("LVALUE", "ident", "ident LVALUET2");

        table.add("LVALUET2", "semicomma", "");
        table.add("LVALUET2", "obrack", "obrack intconst cbrack LVALUET2");
        table.add("LVALUET2", "at", "");
        table.add("LVALUET2", "cpar", "");
          table.add("LVALUET2", "plus", "");
        table.add("LVALUET2", "minus", "");
        table.add("LVALUET2", "mod", "");
        table.add("LVALUET2", "div", "");
        table.add("LVALUET2", "mul", "");
        
        table.add("LVALUET2", "cbrack", "");
        table.add("LVALUET2", "comma", "");
        
        table.add("LVALUET2", "ne", "");
        table.add("LVALUET2", "eq", "");
        table.add("LVALUET2", "ge", "");
        table.add("LVALUET2", "le", "");
        table.add("LVALUET2", "gt", "");
        table.add("LVALUET2", "lt", "");
      
      
        
        table.add("NUMEXPRESSION", "opar", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "intconst", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "minus", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "plus", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "null", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "stringconst", "TERM NUMEXPRESSION1");         
        table.add("NUMEXPRESSION", "ident", "TERM NUMEXPRESSION1");

        table.add("NUMEXPRESSION1", "semicomma", "");
        table.add("NUMEXPRESSION1", "cpar", "");
        table.add("NUMEXPRESSION1", "plus", "SUMMINUS NUMEXPRESSION");
        table.add("NUMEXPRESSION1", "minus", "SUMMINUS NUMEXPRESSION");
        
        table.add("SUMMINUS", "minus", "minus");
        table.add("SUMMINUS", "plus", "plus");

        table.add("TERM", "null", "UNARYEXPR TERM3");
        table.add("TERM", "stringconst", "UNARYEXPR TERM3");
        table.add("TERM", "intconst", "UNARYEXPR TERM3");
        table.add("TERM", "minus", "UNARYEXPR TERM3");
        table.add("TERM", "plus", "UNARYEXPR TERM3");
        table.add("TERM", "opar", "UNARYEXPR TERM3");
        table.add("TERM", "ident", "UNARYEXPR TERM3");
        
        table.add("TERM2", "mod", "MULDIVMOD UNARYEXPR TERM3");
        table.add("TERM2", "div", "MULDIVMOD UNARYEXPR TERM3");
        table.add("TERM2", "mul", "MULDIVMOD UNARYEXPR TERM3");
        
        table.add("TERM3", "comma", "");
        table.add("TERM3", "semicomma", "");
        table.add("TERM3", "cpar", "");
        table.add("TERM3", "plus", "");
        table.add("TERM3", "minus", "");
        table.add("TERM3", "mod", "TERM2");
        table.add("TERM3", "div", "TERM2");
        table.add("TERM3", "mul", "TERM2");
                
        table.add("MULDIVMOD", "mod", "mod");
        table.add("MULDIVMOD", "div", "div");
        table.add("MULDIVMOD", "mul", "mul");
        
        table.add("UNARYEXPR", "ident", "FACTOR");
        table.add("UNARYEXPR", "opar", "FACTOR");
        table.add("UNARYEXPR", "plus", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "minus", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "intconst", "FACTOR");
        table.add("UNARYEXPR", "stringconst", "FACTOR");
        table.add("UNARYEXPR", "null", "FACTOR");
        
        table.add("FACTOR", "intconst", "intconst");
        table.add("FACTOR", "stringconst", "stringconst");
        table.add("FACTOR", "null", "null");
        table.add("FACTOR", "opar", "opar NUMEXPRESSION cpar");
        table.add("FACTOR", "ident", "LVALUE");

        //Done
        return table;
    }
}