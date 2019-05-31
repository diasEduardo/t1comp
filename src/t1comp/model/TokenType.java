/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

/**
 *
 * @author eduardo
 */
public enum TokenType {
        CLASS, EXTENDS, INT, STRING, CONSTRUCTOR, PRINT, READ, RETURN, SUPER, IF,
        ELSE, FOR, NEW, BREAK, AT, EQ, GT, GE, LT, LE, NE, PLUS, MINUS, MUL, DIV, MOD,
        ID, INTCONST, STRINGCONST, OBRACE, CBRACE, OPAR, CPAR, OBRACK, CBRACK, SEMICOMMA,
        COMMA, DOT, BLANK, ERROR, NULLTYPE, SEMITOKEN, END;

        public static TokenType get(String typeName) {
            for (TokenType categoria : TokenType.values()) {
                if (typeName.equals(categoria.toString())) {
                    return categoria;
                }
            }

            return ERROR;
        }
    }
