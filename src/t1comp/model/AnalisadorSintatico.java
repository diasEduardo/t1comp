/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;
import t1comp.model.TableBuilder;
/**
 *
 * @author nathangodinho
 */
public class AnalisadorSintatico {
 
    private LL1Table parseTable;
    
    public AnalisadorSintatico () {
        parseTable = TableBuilder.buildTable();
    }
    
    public boolean parse (AnalisadorLexico lex) {
        while (lex.hasTokens()) {
            Token token = lex.getNextToken();
            System.out.println(token.toString());
        }
        
        return true;
    }
    
}
