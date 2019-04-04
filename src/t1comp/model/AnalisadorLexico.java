/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import t1comp.model.TokenType;
/**
 *
 * @author nathan
 */
public class AnalisadorLexico {

    private ArrayList<Token> tokenList;
    private final SymbolsTable sysmbolsTable;

    public AnalisadorLexico() {
        this.sysmbolsTable = SymbolsTable.getInstance();
    }

    

    private TokenType checkOperatorsToken(String token) {
//        TODO NE CASE NOT CORRECT
        switch (token) {
            case ">":
                return TokenType.GT;
            case "<":
                return TokenType.LT;
            case ">=":
                return TokenType.GE;
            case "<=":
                return TokenType.LE;
            case "=":
                return TokenType.AT;
            case "==":
                return TokenType.EQ;
            case "!":
                return TokenType.SEMITOKEN;
            case "!=":
                return TokenType.NE;
            case "+":
                return TokenType.PLUS;
            case "-":
                return TokenType.MINUS;
            case "/":
                return TokenType.DIV;
            case "*":
                return TokenType.MUL;
            case "%":
                return TokenType.MOD;
            default:
                return TokenType.NULLTYPE;

        }
    }

    private TokenType checkScopeToken(String token) {
        switch (token) {
            case "{":
                return TokenType.OBRACE;
            case "}":
                return TokenType.CBRACE;
            case "(":
                return TokenType.OPAR;
            case ")":
                return TokenType.CPAR;
            case "[":
                return TokenType.OBRACK;
            case "]":
                return TokenType.CBRACK;
            case ";":
                return TokenType.SEMICOMMA;
            case ",":
                return TokenType.COMMA;
            case ".":
                return TokenType.DOT;
            default:
                return TokenType.NULLTYPE;

        }
    }

    private TokenType checkTokenType(String token) {

        switch (token) {
            case "class":
                return TokenType.CLASS;
            case "extends":
                return TokenType.EXTENDS;
            case "int":
                return TokenType.INT;
            case "string":
                return TokenType.STRING;
            case "constructor":
                return TokenType.CONSTRUCTOR;
            case "print":
                return TokenType.PRINT;
            case "read":
                return TokenType.READ;
            case "return":
                return TokenType.RETURN;
            case "super":
                return TokenType.SUPER;
            case "if":
                return TokenType.IF;
            case "else":
                return TokenType.ELSE;
            case "for":
                return TokenType.FOR;
            case "new":
                return TokenType.NEW;
            case "break":
                return TokenType.BREAK;
            default:
                return TokenType.ID;

        }

    }

    public String[] getLines(String text) {
        return text.split(System.getProperty("line.separator"));
    }

    public void analyze(String sourceCode) {
        sysmbolsTable.clean();
        int lineIndex = 0, columnIndex = 0;
        ArrayList<TokenType> lastMatch = new ArrayList<TokenType>();
        String lexeme = "";
        String[] lines = getLines(sourceCode);
        tokenList = new ArrayList<>();

        for (int l = 0; l < lines.length; l++) {

            lastMatch = new ArrayList<TokenType>();
            lexeme = "";
            lineIndex = l;
            columnIndex = 0;

            char[] characters = lines[l].toCharArray();

            for (int c = 0; c < characters.length; c++) {
                lexeme += characters[c];
                ArrayList<TokenType> match = doLexAnalysis(lexeme);
                if (match.size() == 0 && lastMatch.size() == 1 && lastMatch.get(0) != TokenType.SEMITOKEN) {
                    lexeme = lexeme.substring(0, lexeme.length() - 1);
                    TokenType token = lastMatch.get(0);
                    if (token.equals(TokenType.ID)) {
                        token = checkTokenType(lexeme);
                    }

                    //insert on symbol table
                    int tokenIndex = sysmbolsTable.addEntry(new TableEntry(token, lexeme, lineIndex, columnIndex));
                    tokenList.add(new Token(token,tokenIndex));
                    lastMatch = new ArrayList<TokenType>();
                    lexeme = "";
                    lineIndex = l;
                    columnIndex = c;
                    c -= 1;
                } else if (match.size() == 0 && lastMatch.size() != 1) {
                    //error     
                    System.out.println("ERROR IN: "+ (l+1) + " , " + (c+1));
                } else {
                    lastMatch = match;
                }
            }
            if (lastMatch.size() == 1 && lastMatch.get(0) != TokenType.SEMITOKEN) {
                TokenType token = lastMatch.get(0);
                if (token.equals(TokenType.ID)) {
                    token = checkTokenType(lexeme);
                }
                
                int tokenIndex = sysmbolsTable.addEntry(new TableEntry(token, lexeme, lineIndex, columnIndex));
                tokenList.add(new Token(token,tokenIndex));
            } else if ((lastMatch.size() != 1 && characters.length > 0) || (lastMatch.size() == 1 && lastMatch.get(0) == TokenType.SEMITOKEN)) {
                System.out.println("ERROR IN: "+ (l+1));
            }

        }

        sysmbolsTable.display();
    }

    public ArrayList<TokenType> doLexAnalysis(String lexeme) {
        ArrayList<TokenType> response = new ArrayList<TokenType>();
        TokenType pointAnalisys = checkOperatorsToken(lexeme);
        TokenType scopeAnalisys = checkScopeToken(lexeme);

        if (tokenMatch("^[a-zA-Z][a-zA-Z0-9]*$", lexeme)) {
            response.add(TokenType.ID);
        }

        if (tokenMatch("^\"[^\"]*\"$", lexeme)) {
            response.add(TokenType.STRINGCONST);
        } else if (tokenMatch("^\"[^\"]*$", lexeme)) {
            response.add(TokenType.SEMITOKEN);
        }

        if (tokenMatch("^[0-9]+$", lexeme)) {
            response.add(TokenType.INTCONST);
        }
        if (!pointAnalisys.equals(TokenType.NULLTYPE)) {
            response.add(pointAnalisys);
        }
        if (!scopeAnalisys.equals(TokenType.NULLTYPE)) {
            response.add(scopeAnalisys);
        }
        if (tokenMatch("^[ ]+$", lexeme)) {
            response.add(TokenType.BLANK);
        }

        return response;
    }

    public boolean tokenMatch(String pattern, String lexeme) {
        Pattern regex = Pattern.compile(pattern);
        Matcher regexMatcher = regex.matcher(lexeme);
        if (regexMatcher.find()) {
            return true;
        }
        return false;
    }

    public Token getNextToken() {
        return tokenList.remove(0);
    }

    public boolean hasTokens() {
        return !tokenList.isEmpty();
    }
}
