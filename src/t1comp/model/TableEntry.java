/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import t1comp.model.TokenType;

/**
 *
 * @author nathan
 */
// TODO add entry for the type for semantic analisys

public class TableEntry {

    private TokenType token;
    private String lexeme;
    private int line, column;

    public TableEntry(TokenType token, String lexeme, int line, int column) {
        this.token = token;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    public TokenType getToken() {
        return token;
    }

    public void setToken(TokenType token) {
        this.token = token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexema) {
        this.lexeme = lexema;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        String printTable = "";

        printTable = printTable.concat(
                "Token: " + token.toString()
                + " | Lexeme: " + lexeme
                + " | Line: " + line
                + " | Column: " + column
                + "\n"
        );

        return printTable;
    }
}
