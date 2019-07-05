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

        table.add("PROGRAM", "class", "CLASSLIST");
        

        table.add("CLASSLIST", "class", "CLASSDECL CLASSLIST1");

        table.add("CLASSLIST1", "class", "CLASSLIST");
        table.add("CLASSLIST1", "ident", "");
        table.add("CLASSLIST1", "cbrace", "");
        table.add("CLASSLIST1", "int", "");
        table.add("CLASSLIST1", "string", "");
        table.add("CLASSLIST1", "constructor", "");
        table.add("CLASSLIST1", "", "");

        table.add("CLASSDECL", "class", "class ident CLASSDECL1");

        table.add("CLASSDECL1", "extends", "extends ident CLASSBODY");
        table.add("CLASSDECL1", "obrace", "CLASSBODY");

        table.add("CLASSBODY", "obrace", "obrace CLASSBODY1");

        table.add("CLASSBODY1", "class", "PROGRAM CLASSBODY2");
        table.add("CLASSBODY1", "ident", "CLASSBODY2");
        table.add("CLASSBODY1", "cbrace", "CLASSBODY2");
        table.add("CLASSBODY1", "int", "CLASSBODY2");
        table.add("CLASSBODY1", "string", "CLASSBODY2");
        table.add("CLASSBODY1", "constructor", "CLASSBODY2");

        table.add("CLASSBODY2", "ident", "VARDECLTYPE CLASSBODY22");
        table.add("CLASSBODY2", "cbrace", "cbrace");
        table.add("CLASSBODY2", "int", "VARDECLTYPE CLASSBODY22");
        table.add("CLASSBODY2", "string", "VARDECLTYPE CLASSBODY22");
        table.add("CLASSBODY2", "constructor", "CLASSBODYCONSTRUCTDECL CLASSBODY4");

        table.add("CLASSBODY22", "ident", "ident CLASSBODY23");
        table.add("CLASSBODY22", "obrack", "VARDECLBRACKETS ident METHODBODY CLASSBODYMETHODDECL1 cbrace");

        //rever isso
        table.add("CLASSBODY23", "class", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "ident", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "cbrace", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "semicomma", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "int", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "string", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "obrack", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "comma", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "opar", "METHODBODY CLASSBODYMETHODDECL1 cbrace");
        table.add("CLASSBODY23", "", "VARDECL1 semicomma CLASSBODY2");
        table.add("CLASSBODY23", "constructor", "VARDECL1 semicomma CLASSBODY2");

        table.add("CLASSBODY3", "ident", "CLASSBODY4");
        table.add("CLASSBODY3", "cbrace", "CLASSBODY4");
        table.add("CLASSBODY3", "int", "CLASSBODY4");
        table.add("CLASSBODY3", "string", "CLASSBODY4");
        table.add("CLASSBODY3", "constructor", "CLASSBODYCONSTRUCTDECL CLASSBODY4");

        table.add("CLASSBODY4", "ident", "CLASSBODYMETHODDECL cbrace");
        table.add("CLASSBODY4", "cbrace", "cbrace");
        table.add("CLASSBODY4", "int", "CLASSBODYMETHODDECL cbrace");
        table.add("CLASSBODY4", "string", "CLASSBODYMETHODDECL cbrace");

        table.add("CLASSBODYVARDELC", "ident", "VARDECL semicomma CLASSBODYVARDELC1");
        table.add("CLASSBODYVARDELC", "int", "VARDECL semicomma CLASSBODYVARDELC1");
        table.add("CLASSBODYVARDELC", "string", "VARDECL semicomma CLASSBODYVARDELC1");

        table.add("CLASSBODYVARDELC1", "ident", "CLASSBODYVARDELC");
        table.add("CLASSBODYVARDELC1", "int", "CLASSBODYVARDELC");
        table.add("CLASSBODYVARDELC1", "string", "CLASSBODYVARDELC");

        table.add("CLASSBODYCONSTRUCTDECL", "constructor", "CONSTRUCTDECL CLASSBODYCONSTRUCTDECL1");

        table.add("CLASSBODYCONSTRUCTDECL1", "constructor", "CLASSBODYCONSTRUCTDECL");
        table.add("CLASSBODYCONSTRUCTDECL1", "cbrace", "");
        table.add("CLASSBODYCONSTRUCTDECL1", "ident", "");
        table.add("CLASSBODYCONSTRUCTDECL1", "int", "");
        table.add("CLASSBODYCONSTRUCTDECL1", "string", "");

        table.add("CLASSBODYMETHODDECL", "ident", "METHODDECL CLASSBODYMETHODDECL1");
        table.add("CLASSBODYMETHODDECL", "int", "METHODDECL CLASSBODYMETHODDECL1");
        table.add("CLASSBODYMETHODDECL", "string", "METHODDECL CLASSBODYMETHODDECL1");

        table.add("CLASSBODYMETHODDECL1", "string", "CLASSBODYMETHODDECL");
        table.add("CLASSBODYMETHODDECL1", "int", "CLASSBODYMETHODDECL");
        table.add("CLASSBODYMETHODDECL1", "ident", "CLASSBODYMETHODDECL");
        table.add("CLASSBODYMETHODDECL1", "cbrace", "");

        table.add("VARDECL", "ident", "VARDECLTYPE ident VARDECL1");
        table.add("VARDECL", "int", "VARDECLTYPE ident VARDECL1");
        table.add("VARDECL", "string", "VARDECLTYPE ident VARDECL1");

        table.add("VARDECL1", "semicomma", "VARDECL2");
        table.add("VARDECL1", "obrack", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECL1", "comma", "VARDECL2");

        table.add("VARDECL2", "semicomma", "");
        table.add("VARDECL2", "comma", "VARDECLWITHCOMA");

        table.add("VARDECLTYPE", "ident", "ident");
        table.add("VARDECLTYPE", "int", "int");
        table.add("VARDECLTYPE", "string", "string");

//        table.add("VARDECLBRACKETS", "obrack", "obrack cbrack VARDECLBRACKETS1");
        table.add("VARDECLBRACKETS", "obrack", "obrack intconst cbrack VARDECLBRACKETS1");

        table.add("VARDECLBRACKETS1", "ident", "");
        table.add("VARDECLBRACKETS1", "semicomma", "");
        table.add("VARDECLBRACKETS1", "obrack", "VARDECLBRACKETS");
        table.add("VARDECLBRACKETS1", "comma", "");
        table.add("VARDECLBRACKETS1", "cpar", "");

        table.add("VARDECLWITHCOMA", "comma", "comma ident VARDECLWITHCOMA1");

        table.add("VARDECLWITHCOMA1", "semicomma", "");
        table.add("VARDECLWITHCOMA1", "obrack", "VARDECLBRACKETS VARDECL2");
        table.add("VARDECLWITHCOMA1", "comma", "VARDECLWITHCOMA");

        table.add("CONSTRUCTDECL", "constructor", "constructor METHODBODY");

        table.add("METHODDECL", "ident", "VARDECLTYPE METHODDECL1");
        table.add("METHODDECL", "int", "VARDECLTYPE METHODDECL1");
        table.add("METHODDECL", "string", "VARDECLTYPE METHODDECL1");

        table.add("METHODDECL1", "ident", "ident METHODBODY");
        table.add("METHODDECL1", "obrack", "VARDECLBRACKETS ident METHODBODY");

        table.add("METHODBODY", "opar", "opar METHODBODY1");

        table.add("METHODBODY1", "ident", "PARAMLIST cpar STATEMENT");
        table.add("METHODBODY1", "int", "PARAMLIST cpar STATEMENT");
        table.add("METHODBODY1", "string", "PARAMLIST cpar STATEMENT");
        table.add("METHODBODY1", "cpar", "cpar STATEMENT");

        table.add("PARAMLIST", "ident", "VARDECLTYPE ident PARAMLIST12");
        table.add("PARAMLIST", "int", "VARDECLTYPE ident PARAMLIST12");
        table.add("PARAMLIST", "string", "VARDECLTYPE ident PARAMLIST12");

        table.add("PARAMLIST12", "obrack", "VARDECLBRACKETS PARAMLIST13");
        table.add("PARAMLIST12", "comma", "PARAMLIST2");
        table.add("PARAMLIST12", "cpar", "");

        table.add("PARAMLIST13", "cpar", "");
        table.add("PARAMLIST13", "comma", "PARAMLIST2");

        table.add("PARAMLIST2", "comma", "comma VARDECLTYPE ident PARAMLIST12");

        table.add("STATEMENT", "ident", "ident STATEMENT1");
        table.add("STATEMENT", "obrace", "obrace STATLIST cbrace");
        table.add("STATEMENT", "semicomma", "semicomma");
        table.add("STATEMENT", "int", "int ident VARDECL1 semicomma");
        table.add("STATEMENT", "string", "string ident VARDECL1 semicomma");
        table.add("STATEMENT", "break", "break semicomma");
        table.add("STATEMENT", "print", "PRINTSTAT semicomma");
        table.add("STATEMENT", "read", "READSTAT semicomma");
        table.add("STATEMENT", "return", "RETURNSTAT semicomma");
        table.add("STATEMENT", "super", "SUPERSTAT semicomma");
        table.add("STATEMENT", "if", "IFSTAT");
        table.add("STATEMENT", "for", "FORSTAT");

        table.add("STATEMENT1", "ident", "ident VARDECL1 semicomma");
        table.add("STATEMENT1", "obrack", "LVALUEEXPLIST at ATRIBSTAT1 semicomma");
        table.add("STATEMENT1", "at", "at ATRIBSTAT1 semicomma");
        table.add("STATEMENT1", "dot", "LVALUEEXPLIST at ATRIBSTAT1 semicomma");

        table.add("ATRIBSTAT", "ident", "ident LVALUE1 at ATRIBSTAT1");

        table.add("ATRIBSTAT1", "ident", "EXPRESSION");
        table.add("ATRIBSTAT1", "opar", "EXPRESSION");
        table.add("ATRIBSTAT1", "new", "ALOCEXPRESSION");
        table.add("ATRIBSTAT1", "plus", "EXPRESSION");
        table.add("ATRIBSTAT1", "minus", "EXPRESSION");
        table.add("ATRIBSTAT1", "intconst", "EXPRESSION");
        table.add("ATRIBSTAT1", "stringconst", "EXPRESSION");
        table.add("ATRIBSTAT1", "null", "EXPRESSION");

        
        table.add("ATRIBSTAT1", "new", "ALOCEXPRESSION");
        table.add("PRINTSTAT", "print", "print EXPRESSION");

        table.add("READSTAT", "read", "read LVALUE");

        table.add("RETURNSTAT", "return", "return RETURNSTAT1");

        table.add("RETURNSTAT1", "ident", "EXPRESSION");
        table.add("RETURNSTAT1", "semicomma", "");
        table.add("RETURNSTAT1", "opar", "EXPRESSION");
        table.add("RETURNSTAT1", "plus", "EXPRESSION");
        table.add("RETURNSTAT1", "minus", "EXPRESSION");
        table.add("RETURNSTAT1", "intconst", "EXPRESSION");
        table.add("RETURNSTAT1", "stringconst", "EXPRESSION");
        table.add("RETURNSTAT1", "null", "EXPRESSION");

        table.add("SUPERSTAT", "super", "super opar SUPERSTAT1");

        table.add("SUPERSTAT1", "ident", "ARGLIST cpar");
        table.add("SUPERSTAT1", "opar", "ARGLIST cpar");
        table.add("SUPERSTAT1", "cpar", "cpar");
        table.add("SUPERSTAT1", "plus", "ARGLIST cpar");
        table.add("SUPERSTAT1", "minus", "ARGLIST cpar");
        table.add("SUPERSTAT1", "intconst", "ARGLIST cpar");
        table.add("SUPERSTAT1", "stringconst", "ARGLIST cpar");
        table.add("SUPERSTAT1", "null", "ARGLIST cpar");

        table.add("IFSTAT", "if", "if opar EXPRESSION cpar STATEMENT IFSTAT1");

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