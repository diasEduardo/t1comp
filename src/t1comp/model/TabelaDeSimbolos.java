/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author nathan
 */
public final class TabelaDeSimbolos {
    public HashMap<AnalisadorLexico.TokenType, HashSet<String>> tabela;
    private static final TabelaDeSimbolos instance = new TabelaDeSimbolos();
    
    private TabelaDeSimbolos() {
        clean();
    }
    
    public static TabelaDeSimbolos getInstance() {
        return instance;
    }
    
    public HashMap<AnalisadorLexico.TokenType, HashSet<String>> getTable () {
        return tabela;
    }
    
    public void addToken(AnalisadorLexico.TokenType type, String word) {
        tabela.get(type).add(word);
    }
    
    public void clean() {
        tabela = new HashMap<AnalisadorLexico.TokenType, HashSet<String>>() {
            {
                for (AnalisadorLexico.TokenType categoria : AnalisadorLexico.TokenType.values()) {
                    put(categoria, new HashSet<String>());
                }
            }
        };
    }
}
