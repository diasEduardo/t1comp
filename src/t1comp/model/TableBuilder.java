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

        table.add("FORSTAT", "for", "for opar FORSTAT1");

        table.add("FORSTAT1", "ident", "ATRIBSTAT semicomma FORSTAT2");
        table.add("FORSTAT1", "semicomma", "semicomma FORSTAT2");

        table.add("FORSTAT2", "ident", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "semicomma", "semicomma FORSTAT3");
        table.add("FORSTAT2", "opar", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "plus", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "minus", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "intconst", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "stringconst", "EXPRESSION semicomma FORSTAT3");
        table.add("FORSTAT2", "null", "EXPRESSION semicomma FORSTAT3");

        table.add("FORSTAT3", "ident", "ATRIBSTAT cpar STATEMENT");
        table.add("FORSTAT3", "cpar", "cpar STATEMENT");

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

        //table.add("LVALUE", "ident", "ident LVALUE1");
        table.add("LVALUE", "ident", "ident LVALUET2");

        /*table.add("LVALUE1", "semicomma", "");
        table.add("LVALUE1", "obrack", "LVALUEEXPLIST");
        table.add("LVALUE1", "cbrack", "");
        table.add("LVALUE1", "comma", "");
        table.add("LVALUE1", "cpar", "");
        table.add("LVALUE1", "at", "");
        table.add("LVALUE1", "dot", "LVALUEEXPLIST");
        table.add("LVALUE1", "ne", "");
        table.add("LVALUE1", "eq", "");
        table.add("LVALUE1", "ge", "");
        table.add("LVALUE1", "le", "");
        table.add("LVALUE1", "gt", "");
        table.add("LVALUE1", "lt", "");
        table.add("LVALUE1", "plus", "");
        table.add("LVALUE1", "minus", "");
        table.add("LVALUE1", "mod", "");
        table.add("LVALUE1", "div", "");
        table.add("LVALUE1", "mul", "");
        */
        table.add("LVALUET2", "semicomma", "");
        table.add("LVALUET2", "obrack", "obrack intconst cbrack LVALUET2");
        table.add("LVALUET2", "cbrack", "");
        table.add("LVALUET2", "comma", "");
        table.add("LVALUET2", "cpar", "");
        table.add("LVALUET2", "ne", "");
        table.add("LVALUET2", "eq", "");
        table.add("LVALUET2", "ge", "");
        table.add("LVALUET2", "le", "");
        table.add("LVALUET2", "gt", "");
        table.add("LVALUET2", "lt", "");
        table.add("LVALUET2", "plus", "");
        table.add("LVALUET2", "minus", "");
        table.add("LVALUET2", "mod", "");
        table.add("LVALUET2", "div", "");
        table.add("LVALUET2", "mul", "");
        
        table.add("LVALUEEXPLIST", "obrack", "obrack EXPRESSION cbrack LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST", "dot", "dot ident LVALUEEXPLIST2");

        table.add("LVALUEEXPLIST1", "semicomma", "");
        table.add("LVALUEEXPLIST1", "obrack", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST1", "cbrack", "");
        table.add("LVALUEEXPLIST1", "comma", "");
        table.add("LVALUEEXPLIST1", "cpar", "");
        table.add("LVALUEEXPLIST1", "at", "");
        table.add("LVALUEEXPLIST1", "dot", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST1", "ne", "");
        table.add("LVALUEEXPLIST1", "eq", "");
        table.add("LVALUEEXPLIST1", "ge", "");
        table.add("LVALUEEXPLIST1", "le", "");
        table.add("LVALUEEXPLIST1", "gt", "");
        table.add("LVALUEEXPLIST1", "lt", "");
        table.add("LVALUEEXPLIST1", "plus", "");
        table.add("LVALUEEXPLIST1", "minus", "");
        table.add("LVALUEEXPLIST1", "mod", "");
        table.add("LVALUEEXPLIST1", "div", "");
        table.add("LVALUEEXPLIST1", "mul", "");

        //review below
        table.add("LVALUEEXPLIST2", "semicomma", "");
        table.add("LVALUEEXPLIST2", "obrack", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST2", "cbrack", "");
        table.add("LVALUEEXPLIST2", "comma", "");
        table.add("LVALUEEXPLIST2", "opar", "opar LVALUEEXPLIST3");
        table.add("LVALUEEXPLIST2", "cpar", "");
        table.add("LVALUEEXPLIST2", "at", "");
        table.add("LVALUEEXPLIST2", "dot", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST2", "ne", "");
        table.add("LVALUEEXPLIST2", "eq", "");
        table.add("LVALUEEXPLIST2", "ge", "");
        table.add("LVALUEEXPLIST2", "le", "");
        table.add("LVALUEEXPLIST2", "gt", "");
        table.add("LVALUEEXPLIST2", "lt", "");
        table.add("LVALUEEXPLIST2", "plus", "");
        table.add("LVALUEEXPLIST2", "minus", "");
        table.add("LVALUEEXPLIST2", "mod", "");
        table.add("LVALUEEXPLIST2", "div", "");
        table.add("LVALUEEXPLIST2", "mul", "");

        table.add("LVALUEEXPLIST3", "ident", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "opar", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "cpar", "cpar");
        table.add("LVALUEEXPLIST3", "plus", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "minus", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "intconst", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "stringconst", "ARGLIST cpar LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "null", "ARGLIST cpar LVALUEEXPLIST1");
        
        //        ALOCEXPRESSION TODO
        
        table.add("ALOCEXPRESSION", "new", "new ALOCEXPRESSION1");
        
        table.add("ALOCEXPRESSION1", "string", "string ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSION1", "int", "int ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSION1", "ident", "ident ALOCEXPRESSION2");
        
        table.add("ALOCEXPRESSION2", "obrack", "ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSION2", "opar", "opar ALOCEXPRESSION3");
        
        table.add("ALOCEXPRESSION3", "null", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "stringconst", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "intconst", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "plus", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "minus", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "cpar", "cpar");
        table.add("ALOCEXPRESSION3", "opar", "ARGLIST cpar");
        table.add("ALOCEXPRESSION3", "ident", "ARGLIST cpar");
        
        table.add("ALOCEXPRESSIONPLUS", "obrack", "obrack EXPRESSION cbrack ALOCEXPRESSIONPLUS1");

        table.add("ALOCEXPRESSIONPLUS1", "semicomma", "");
        table.add("ALOCEXPRESSIONPLUS1", "obrack", "ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSIONPLUS1", "cpar", "");

        table.add("EXPRESSION", "ident", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "opar", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "plus", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "minus", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "intconst", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "stringconst", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "null", "NUMEXPRESSION EXPRESSION1");

        table.add("EXPRESSION1", "lt", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "gt", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "le", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "ge", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "eq", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "ne", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "cpar", "");
        table.add("EXPRESSION1", "comma", "");
        table.add("EXPRESSION1", "semicomma", "");        
        table.add("EXPRESSION1", "cbrack", "");
        table.add("EXPRESSION1", "cpar", "");

        table.add("EXPRESSIONCOMPARE", "lt", "lt");
        table.add("EXPRESSIONCOMPARE", "gt", "gt");
        table.add("EXPRESSIONCOMPARE", "le", "le");
        table.add("EXPRESSIONCOMPARE", "ge", "ge");
        table.add("EXPRESSIONCOMPARE", "eq", "eq");
        table.add("EXPRESSIONCOMPARE", "ne", "ne");

        table.add("NUMEXPRESSION", "null", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "stringconst", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "intconst", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "minus", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "plus", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "ident", "TERM NUMEXPRESSION1");

        table.add("NUMEXPRESSION1", "semicomma", "");
        table.add("NUMEXPRESSION1", "cbrack", "");
        table.add("NUMEXPRESSION1", "comma", "");
        table.add("NUMEXPRESSION1", "cpar", "");
        table.add("NUMEXPRESSION1", "lt", "");
        table.add("NUMEXPRESSION1", "gt", "");
        table.add("NUMEXPRESSION1", "le", "");
        table.add("NUMEXPRESSION1", "ge", "");
        table.add("NUMEXPRESSION1", "eq", "");
        table.add("NUMEXPRESSION1", "ne", "");
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
        
        table.add("TERM3", "semicomma", "");
        table.add("TERM3", "cbrack", "");
        table.add("TERM3", "comma", "");
        table.add("TERM3", "cpar", "");
        table.add("TERM3", "lt", "");
        table.add("TERM3", "gt", "");
        table.add("TERM3", "le", "");
        table.add("TERM3", "ge", "");
        table.add("TERM3", "eq", "");
        table.add("TERM3", "ne", "");
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

        
        table.add("ARGLIST", "ident", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "opar", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "plus", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "minus", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "intconst", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "stringconst", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "null", "EXPRESSION ARGLIST2");
        
        table.add("ARGLIST2", "cpar", "");
        table.add("ARGLIST2", "comma", "ARGLISTEXP");
        
        table.add("ARGLISTEXP", "comma", "comma EXPRESSION ARGLIST2");
        //Done
        return table;
    }
}