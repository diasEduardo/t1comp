/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import t1comp.model.TableBuilder;

/**
 *
 * @author nathangodinho
 */
public class AnalisadorSintatico {

    private LL1Table parseTable;
    private String errorMessage;
    private SymbolsTable symbolsTable;
    
    public AnalisadorSintatico() {
        parseTable = TableBuilder.buildTable();
        errorMessage = "";
        symbolsTable = SymbolsTable.getInstance();
    }
    
    public String statusMessage () {
        return errorMessage.isEmpty() ? "Parser Analisis: Ok \n" : errorMessage;
    }
    
    private void cleanErrorMessage() {
        errorMessage = "";
    }
    
    public boolean parse(AnalisadorLexico lex) {
        cleanErrorMessage();
        ArrayList<String> stack = new ArrayList<String>();
        stack.add("PROGRAM");
        while (lex.hasTokens()) {
            Token tokenObj = lex.getNextToken();
            String token = tokenObj.getTypeName();//tokens.get(0);
            int[] tokenLines = symbolsTable.getLinesByIndex(tokenObj.getTableIndex() - 1);;
            
            if ("BLANK".equals(token)) {
                continue;
            }
            boolean tokenMatch = false;
            while (!tokenMatch) {
                if (token.toLowerCase().equals(stack.get(0).toLowerCase())) {
                    stack.remove(0);
//                    action()
                    tokenMatch = true;
                } else {
                    String nextProduction = parseTable.get(stack.get(0), token.toLowerCase());
                    if (nextProduction == null) {
                        errorMessage += "\nError on token: " + token;
                        errorMessage += "- Lines: " + String.valueOf(tokenLines[0]) 
                                + ": " + String.valueOf(tokenLines[1]);
                        break;
                    }
                    String[] splited = nextProduction.split(" ");
                    stack.remove(0);
                    //                    action()
                    for (int i = splited.length - 1; i >= 0; i--) {
                        if (splited[i].length() > 0) {
                            stack.add(0, splited[i]);
                        }

                    }
                }
            }
        }

        if (errorMessage.isEmpty()) {
            System.out.println("Parser Done: Code OK");
        } else {            
            System.err.println(errorMessage);
        }
        
        return true;
    }

}
