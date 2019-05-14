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
        
        table.add("STATEMENT", "ident", "ident STATEMENT1");
        table.add("STATEMENT", "{", "{ STATLIST }");
        table.add("STATEMENT", ";", ";");
        table.add("STATEMENT", "int", "int ident VARDECL1 ;");
        table.add("STATEMENT", "string", "string ident VARDECL1 ;");
        table.add("STATEMENT", "break", "break ;");
        table.add("STATEMENT", "print", "PRINTSTAT ;");
        table.add("STATEMENT", "read", "READSTAT ;");
        table.add("STATEMENT", "return", "RETURNSTAT ;");
        table.add("STATEMENT", "super", "SUPERSTAT ;");
        table.add("STATEMENT", "if", "IFSTAT");
        table.add("STATEMENT", "for", "FORSTAT");
        
        table.add("STATEMENT1", "ident", "ident VARDECL1 ;");
        table.add("STATEMENT1", "[", "LVALUEEXPLIST = ATRIBSTAT1 ;");
        table.add("STATEMENT1", "=", "= ATRIBSTAT1 ;");
        table.add("STATEMENT1", ".", "LVALUEEXPLIST = ATRIBSTAT1 ;");
        
        table.add("ATRIBSTAT", "ident", "ident LVALUE1 = ATRIBSTAT1");
        
        table.add("ATRIBSTAT1", "ident", "EXPRESSION");
        table.add("ATRIBSTAT1", "(", "EXPRESSION");
        table.add("ATRIBSTAT1", "new", "ALOCEXPRESSION");
        table.add("ATRIBSTAT1", "+", "EXPRESSION");
        table.add("ATRIBSTAT1", "-", "EXPRESSION");
        table.add("ATRIBSTAT1", "int-constant", "EXPRESSION");
        table.add("ATRIBSTAT1", "string-constant", "EXPRESSION");
        table.add("ATRIBSTAT1", "null", "EXPRESSION");
        
        table.add("PRINTSTAT", "print", "print EXPRESSION");
        
        table.add("READSTAT", "read", "read LVALUE");
        
        table.add("RETURNSTAT", "return", "return RETURNSTAT1");
        
        table.add("RETURNSTAT1", "ident", "EXPRESSION");
        table.add("RETURNSTAT1", ";", "");
        table.add("RETURNSTAT1", "(", "EXPRESSION");
        table.add("RETURNSTAT1", "+", "EXPRESSION");
        table.add("RETURNSTAT1", "-", "EXPRESSION");
        table.add("RETURNSTAT1", "int-constant", "EXPRESSION");
        table.add("RETURNSTAT1", "string-constant", "EXPRESSION");
        table.add("RETURNSTAT1", "null", "EXPRESSION");
        
        table.add("SUPERSTAT", "super", "super ( SUPERSTAT1");
        
        table.add("SUPERSTAT1", "ident", "ARGLIST )");
        table.add("SUPERSTAT1", "(", "ARGLIST )");
        table.add("SUPERSTAT1", ")", ")");
        table.add("SUPERSTAT1", "+", "ARGLIST )");
        table.add("SUPERSTAT1", "-", "ARGLIST )");
        table.add("SUPERSTAT1", "int-constant", "ARGLIST )");
        table.add("SUPERSTAT1", "string-constant", "ARGLIST )");
        table.add("SUPERSTAT1", "null", "ARGLIST )");
        
        table.add("IFSTAT", "if", "if ( EXPRESSION ) STATEMENT IFSTAT1");
        
        table.add("IFSTAT1", "else", "IFSTAT1 -> else STATEMENT end");
        table.add("IFSTAT1", "end", "end");
        
        table.add("FORSTAT", "for", "for ( FORSTAT1");
        
        table.add("FORSTAT1", "ident", "ATRIBSTAT ; FORSTAT2");
        table.add("FORSTAT1", ";", "; FORSTAT2");
        
        table.add("FORSTAT2", "ident", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", ";", "; FORSTAT3");
        table.add("FORSTAT2", "(", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", "+", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", "-", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", "int-constant", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", "string-constant", "EXPRESSION ; FORSTAT3");
        table.add("FORSTAT2", "null", "EXPRESSION ; FORSTAT3");
        
        table.add("FORSTAT3", "ident", "ATRIBSTAT ) STATEMENT");
        table.add("FORSTAT3", ")", ") STATEMENT");
        
        table.add("STATLIST", "ident", "STATEMENT STATLIST1");
        table.add("STATLIST", "{", "STATEMENT STATLIST1");
        table.add("STATLIST", ";", "STATEMENT STATLIST1");
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
        table.add("STATLIST1", "{", "STATLIST");
        table.add("STATLIST1", "}", "");
        table.add("STATLIST1", ";", "STATLIST");
        table.add("STATLIST1", "int", "STATLIST");
        table.add("STATLIST1", "string", "STATLIST");
        table.add("STATLIST1", "break", "STATLIST");
        table.add("STATLIST1", "print", "STATLIST");
        table.add("STATLIST1", "read", "STATLIST");
        table.add("STATLIST1", "return", "STATLIST");
        table.add("STATLIST1", "super", "STATLIST");
        table.add("STATLIST1", "if", "STATLIST");
        table.add("STATLIST1", "for", "STATLIST");
        
        table.add("LVALUE", "ident", "ident LVALUE1");
        
        table.add("LVALUE1", ";", "");
        table.add("LVALUE1", "[", "LVALUEEXPLIST");
        table.add("LVALUE1", "]", "");
        table.add("LVALUE1", ",", "");
        table.add("LVALUE1", ")", "");
        table.add("LVALUE1", "=", "");
        table.add("LVALUE1", ".", "LVALUEEXPLIST");
        table.add("LVALUE1", "!=", "");
        table.add("LVALUE1", "==", "");
        table.add("LVALUE1", ">=", "");
        table.add("LVALUE1", "<=", "");
        table.add("LVALUE1", ">", "");
        table.add("LVALUE1", "<", "");
        table.add("LVALUE1", "+", "");
        table.add("LVALUE1", "-", "");
        table.add("LVALUE1", "%", "");
        table.add("LVALUE1", "/", "");
        table.add("LVALUE1", "*", "");
        
        table.add("LVALUEEXPLIST", "[", "[ EXPRESSION ] LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST", ".", ". ident LVALUEEXPLIST2");
        
        
        table.add("LVALUEEXPLIST1", ";", "");
        table.add("LVALUEEXPLIST1", "[", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST1", "]", "");
        table.add("LVALUEEXPLIST1", ",", "");
        table.add("LVALUEEXPLIST1", ")", "");
        table.add("LVALUEEXPLIST1", "=", "");
        table.add("LVALUEEXPLIST1", ".", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST1", "!=", "");
        table.add("LVALUEEXPLIST1", "==", "");
        table.add("LVALUEEXPLIST1", ">=", "");
        table.add("LVALUEEXPLIST1", "<=", "");
        table.add("LVALUEEXPLIST1", ">", "");
        table.add("LVALUEEXPLIST1", "<", "");
        table.add("LVALUEEXPLIST1", "+", "");
        table.add("LVALUEEXPLIST1", "-", "");
        table.add("LVALUEEXPLIST1", "%", "");
        table.add("LVALUEEXPLIST1", "/", "");
        table.add("LVALUEEXPLIST1", "*", "");
        
        table.add("LVALUEEXPLIST2", ";", "");
        table.add("LVALUEEXPLIST2", "[", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST2", "]", "");
        table.add("LVALUEEXPLIST2", ",", "");
        table.add("LVALUEEXPLIST2", "(", "( LVALUEEXPLIST3");
        table.add("LVALUEEXPLIST2", ")", "");
        table.add("LVALUEEXPLIST2", "=", "");
        table.add("LVALUEEXPLIST2", ".", "LVALUEEXPLIST");
        table.add("LVALUEEXPLIST2", "!=", "");
        table.add("LVALUEEXPLIST2", "==", "");
        table.add("LVALUEEXPLIST2", ">=", "");
        table.add("LVALUEEXPLIST2", "<=", "");
        table.add("LVALUEEXPLIST2", ">", "");
        table.add("LVALUEEXPLIST2", "<", "");
        table.add("LVALUEEXPLIST2", "+", "");
        table.add("LVALUEEXPLIST2", "-", "");
        table.add("LVALUEEXPLIST2", "%", "");
        table.add("LVALUEEXPLIST2", "/", "");
        table.add("LVALUEEXPLIST2", "*", "");
        
        table.add("LVALUEEXPLIST3", "ident", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "(", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", ")", ") LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "+", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "-", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "int-constant", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "string-constant", "ARGLIST ) LVALUEEXPLIST1");
        table.add("LVALUEEXPLIST3", "null", "ARGLIST ) LVALUEEXPLIST1");
        
        table.add("ALOCEXPRESSION", "new", "ALOCEXPRESSION1");
        
        table.add("ALOCEXPRESSION1", "ident", "ident ALOCEXPRESSION2");
        table.add("ALOCEXPRESSION1", "int", "int ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSION1", "string", "string ALOCEXPRESSIONPLUS");
        
        table.add("ALOCEXPRESSION2", "[", "ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSION2", "(", "( ALOCEXPRESSION3");
        
        table.add("ALOCEXPRESSION3", "ident", "ARGLIST )");
        table.add("ALOCEXPRESSION3", "(", "ARGLIST )");
        table.add("ALOCEXPRESSION3", ")", ")");
        table.add("ALOCEXPRESSION3", "+", "ARGLIST )");
        table.add("ALOCEXPRESSION3", "-", "ARGLIST )");
        table.add("ALOCEXPRESSION3", "int-constant", "ARGLIST )");
        table.add("ALOCEXPRESSION3", "string-constant", "ARGLIST )");
        table.add("ALOCEXPRESSION3", "null", "ARGLIST )");
        
        table.add("ALOCEXPRESSIONPLUS", "[", "[ EXPRESSION ] ALOCEXPRESSIONPLUS1 )");
        
        table.add("ALOCEXPRESSIONPLUS1", ";", "");
        table.add("ALOCEXPRESSIONPLUS1", "[", "ALOCEXPRESSIONPLUS");
        table.add("ALOCEXPRESSIONPLUS1", ")", "");
        
        table.add("EXPRESSION", "ident", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "(", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "+", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "-", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "int-constant", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "string-constant", "NUMEXPRESSION EXPRESSION1");
        table.add("EXPRESSION", "null", "NUMEXPRESSION EXPRESSION1");
        
        table.add("EXPRESSION1", ";", "");
        table.add("EXPRESSION1", "]", "");
        table.add("EXPRESSION1", ",", "");
        table.add("EXPRESSION1", ")", "");
        table.add("EXPRESSION1", "!=", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "==", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", ">=", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "<=", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", ">", "EXPRESSIONCOMPARE NUMEXPRESSION");
        table.add("EXPRESSION1", "<", "EXPRESSIONCOMPARE NUMEXPRESSION");
        
        table.add("EXPRESSIONCOMPARE", "!=", "!=");
        table.add("EXPRESSIONCOMPARE", "==", "==");
        table.add("EXPRESSIONCOMPARE", ">=", ">=");
        table.add("EXPRESSIONCOMPARE", "<=", "<=");
        table.add("EXPRESSIONCOMPARE", ">", ">");
        table.add("EXPRESSIONCOMPARE", "<", "<");
        
        table.add("NUMEXPRESSION", "ident", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "(", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "+", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "-", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "int-constant", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "string-constant", "TERM NUMEXPRESSION1");
        table.add("NUMEXPRESSION", "null", "TERM NUMEXPRESSION1");
        
        table.add("NUMEXPRESSION1", ";", "");
        table.add("NUMEXPRESSION1", "]", "");
        table.add("NUMEXPRESSION1", ",", "");
        table.add("NUMEXPRESSION1", ")", "");
        table.add("NUMEXPRESSION1", "!=", "");
        table.add("NUMEXPRESSION1", "==", "");
        table.add("NUMEXPRESSION1", ">=", "");
        table.add("NUMEXPRESSION1", "<=", "");
        table.add("NUMEXPRESSION1", ">", "");
        table.add("NUMEXPRESSION1", "<", "");
        table.add("NUMEXPRESSION1", "+", "SUMMINUS NUMEXPRESSION");
        table.add("NUMEXPRESSION1", "-", "SUMMINUS NUMEXPRESSION");
        
        table.add("SUMMINUS", "+", "+");
        table.add("SUMMINUS", "-", "-");
        
        table.add("TERM", "ident", "UNARYEXPR TERM3");
        table.add("TERM", "(", "UNARYEXPR TERM3");
        table.add("TERM", "+", "UNARYEXPR TERM3");
        table.add("TERM", "-", "UNARYEXPR TERM3");
        table.add("TERM", "int-constant", "UNARYEXPR TERM3");
        table.add("TERM", "string-constant", "UNARYEXPR TERM3");
        table.add("TERM", "null", "UNARYEXPR TERM3");
        
        table.add("TERM2", "%", "MULDIVMOD UNARYEXPR TERM3");
        table.add("TERM2", "/", "MULDIVMOD UNARYEXPR TERM3");
        table.add("TERM2", "*", "MULDIVMOD UNARYEXPR TERM3");
        
        
        table.add("TERM3", ";", "");
        table.add("TERM3", "]", "");
        table.add("TERM3", ",", "");
        table.add("TERM3", ")", "");
        table.add("TERM3", "!=", "");
        table.add("TERM3", "==", "");
        table.add("TERM3", ">=", "");
        table.add("TERM3", "<=", "");
        table.add("TERM3", ">", "");
        table.add("TERM3", "<", "");
        table.add("TERM3", "+", "");
        table.add("TERM3", "-", "");
        table.add("TERM3", "%", "TERM2");
        table.add("TERM3", "/", "TERM2");
        table.add("TERM3", "*", "TERM2");
        
        table.add("MULDIVMOD", "%", "%");
        table.add("MULDIVMOD", "/", "/");
        table.add("MULDIVMOD", "*", "*");
        
        table.add("UNARYEXPR", "ident", "FACTOR");
        table.add("UNARYEXPR", "(", "FACTOR");
        table.add("UNARYEXPR", "+", "FACTOR");
        table.add("UNARYEXPR", "-", "FACTOR");
        table.add("UNARYEXPR", "int-constant", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "string-constant", "SUMMINUS FACTOR");
        table.add("UNARYEXPR", "null", "FACTOR");
        
        table.add("FACTOR", "ident", "LVALUE");
        table.add("FACTOR", "(", "( EXPRESSION )");
        table.add("FACTOR", "int-constant", "int-constant");
        table.add("FACTOR", "string-constant", "string-constant");
        table.add("FACTOR", "null", "null");
        
        table.add("ARGLIST", "ident", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "(", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "+", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "-", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "int-constant", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "string-constant", "EXPRESSION ARGLIST2");
        table.add("ARGLIST", "null", "EXPRESSION ARGLIST2");
        
        table.add("ARGLIST2", ",", "ARGLISTEXP");
        table.add("ARGLIST2", ")", "");
        
        table.add("ARGLISTEXP", ")", ", EXPRESSION ARGLIST2");
        
        return table;
    }
}
