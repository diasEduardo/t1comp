/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nathan
 */
public class AnalisadorLexico {

    private ArrayList<String> tokenList;
    private final TabelaDeSimbolos tabelaDeSimbolos;

    public AnalisadorLexico() {
        this.tabelaDeSimbolos = TabelaDeSimbolos.getInstance();
    }

    public enum TokenType {
        CLASS, EXTENDS, INT, STRING, CONSTRUCTOR, PRINT, READ, RETURN, SUPER, IF,
        ELSE, FOR, NEW, BREAK, AT, EQ, GT, GE, LT, LE, NE, PLUS, MINUS, MUL, DIV, MOD,
        ID, INTCONST, STRINGCONST, OBRACE, CBRACE, OPAR, CPAR, OBRACK, CBRACK, SEMICOMMA,
        COMMA, DOT, BLANK, ERROR;

        public static TokenType get(String typeName) {
            for (TokenType categoria : TokenType.values()) {
                if (typeName.equals(categoria.toString())) {
                    return categoria;
                }
            }

            return ERROR;
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
        tabelaDeSimbolos.clean();
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
                if (match.size() == 0 && lastMatch.size() == 1) {
                    lexeme = lexeme.substring(0, lexeme.length() - 1);
                    TokenType token = lastMatch.get(0);
                    if (token.equals(TokenType.ID)) {
                        token = checkTokenType(lexeme);
                    }
                    
                    //insert on symbol table
                    System.out.println("(" + token + "," + lexeme + "," + lineIndex + "," + columnIndex + ")");
                    lastMatch = new ArrayList<TokenType>();
                    lexeme = "";
                    lineIndex = l;
                    columnIndex = c;
                    c -= 1;
                } else if (match.size() == 0 && lastMatch.size() != 1) {
                    //error
                    System.out.println("ERROR");
                } else {
                    lastMatch = match;
                }
            }
            if (lastMatch.size() == 1) {
                TokenType token = lastMatch.get(0);
                if (token.equals(TokenType.ID)) {
                    token = checkTokenType(lexeme);
                }
                //insert on symbol table
                System.out.println("(" + token + "," + lexeme + "," + lineIndex + "," + columnIndex + ")");

            } else if (lastMatch.size() > 1) {
                //error
                System.out.println("ERROR");
            }

        }

        //System.out.println("1");
        ///System.out.println(tokenList.toString());
        //System.out.println(tabelaDeSimbolos.getTable());
    }

    public ArrayList<TokenType> doLexAnalysis(String lexeme) {
        ArrayList<TokenType> response = new ArrayList<TokenType>();

        if (tokenMatch("^[a-zA-Z][a-zA-Z0-9]*$", lexeme)) {
            response.add(TokenType.ID);
        }
        if (tokenMatch("^[0-9]+$", lexeme)) {
            response.add(TokenType.INTCONST);
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

    public String getNextToken() {
        return tokenList.remove(0);
    }

    public boolean hasTokens() {
        return !tokenList.isEmpty();
    }
}
