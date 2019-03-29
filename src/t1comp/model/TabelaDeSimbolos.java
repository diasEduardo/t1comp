/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.HashMap;
import java.util.HashSet;
import org.javatuples.Pair;

/**
 *
 * @author nathan
 */
public final class TabelaDeSimbolos {
    public HashMap< Pair<Integer, Integer>, Pair<AnalisadorLexico.TokenType, String>> tabela;
    private static final TabelaDeSimbolos instance = new TabelaDeSimbolos();

    private TabelaDeSimbolos() {
        clean();
    }

    public static TabelaDeSimbolos getInstance() {
        return instance;
    }

    public HashMap< Pair<Integer, Integer>, Pair<AnalisadorLexico.TokenType, String>> getTable () {
        return tabela;
    }

    public void addToken(int linha,int coluna,AnalisadorLexico.TokenType type, String word) {
        Pair<Integer, Integer> key = new Pair<Integer, Integer>( linha, coluna);
        Pair<AnalisadorLexico.TokenType, String> value = new Pair<AnalisadorLexico.TokenType, String>(type,word);
        tabela.put(key,value);
    }

    public void clean() {
        tabela = new HashMap< Pair<Integer, Integer>, Pair<AnalisadorLexico.TokenType, String>>();
    }
}
