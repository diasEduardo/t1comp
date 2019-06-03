/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import t1comp.model.TokenType;

/**
 *
 * @author eduardo
 */
public class Token {
    private TokenType type;
    private int tableIndex;

    public Token(TokenType type, int tableIndex) {
        this.type = type;
        this.tableIndex = tableIndex;
    }

    public TokenType getType() {
        return type;
    }

    public int getTableIndex() {
        return tableIndex;
    }
    
    public String toString(){
     return "<"+type+","+tableIndex+">";
    }
    
    public String getTypeName(){
     return type+"";
    }
    
}
