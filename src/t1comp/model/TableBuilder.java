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
        /*
        table.add("LVALUE1", "semicomma", "");
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
        semt.addRule("TERM3", "", new atributeAssertion("TERM3.sin", "TERM3.her"));
        
        semt.addRule("TERM3", "TERM2", 
                new ArrayList<>(Arrays.asList(
                        new atributeAssertion("TERM2.her", "TERM3.her"), 
                        new atributeAssertion("TERM3.sin", "TERM2.node"))));
        
        table.add("MULDIVMOD", "mod", "mod");
        table.add("MULDIVMOD", "div", "div");
        table.add("MULDIVMOD", "mul", "mul");
        semt.addRule("MULDIVMOD", "mod", new newLeaf("MULDIVMOD.node", "%"));
        semt.addRule("MULDIVMOD", "div", new newLeaf("MULDIVMOD.node", "/"));
        semt.addRule("MULDIVMOD", "mul", new newLeaf("MULDIVMOD.node", "*"));
        
        table.add("UNARYEXPR", "ident", "FACTOR");
        table.add("UNARYEXPR", "opar", "FACTOR");
        table.add("UNARYEXPR", "plus", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "minus", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "intconst", "FACTOR");
        table.add("UNARYEXPR", "stringconst", "FACTOR");
        table.add("UNARYEXPR", "null", "FACTOR");
        semt.addRule("UNARYEXPR", "FACTOR", new newNode("UNARYEXPR.node", "SUMMINUS.node", "FACTOR.node"));
        semt.addRule("UNARYEXPR", "SUMMINUS FACTOR", new atributeAssertion("FACTOR.sin", "FACTOR.node"));
        
        table.add("FACTOR", "intconst", "intconst");
        table.add("FACTOR", "stringconst", "stringconst");
        table.add("FACTOR", "null", "null");
        table.add("FACTOR", "opar", "opar NUMEXPRESSION cpar");
        table.add("FACTOR", "ident", "LVALUE");
        semt.addRule("FACTOR", "null", new newLeaf("FACTOR.node", "null"));
        semt.addRule("FACTOR", "stringconst", new newLeaf("FACTOR.node","stringconst"));
        semt.addRule("FACTOR", "intconst", new newLeaf("FACTOR.node", "intconst"));
        
        
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

        
        semt.getRule("FACTOR", "intconst").get(0).action();
        semt.getNode(0).toString();
        //Done
        return table;
    }
}