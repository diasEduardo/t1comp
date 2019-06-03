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

    public AnalisadorSintatico() {
        parseTable = TableBuilder.buildTable();
    }

    public boolean parse(AnalisadorLexico lex) {
        String error = "";
//        ArrayList<String> tokens = new ArrayList();
//        tokens.add("CLASS");
//          tokens.add("BLANK");
//        tokens.add("IDENT");
//        tokens.add("OBRACE");
//          tokens.add("BLANK");
//        tokens.add("CBRACE");
        ArrayList<String> stack = new ArrayList<String>();
        stack.add("PROGRAM");
        while (lex.hasTokens()) {
            String token = lex.getNextToken().getTypeName();//tokens.get(0);
            if ("BLANK".equals(token)) {
                continue;
            }
            boolean tokenMatch = false;
            while (!tokenMatch) {
                if (token.toLowerCase().equals(stack.get(0).toLowerCase())) {
                    stack.remove(0);
                    tokenMatch = true;
                } else {
                    String nextProduction = parseTable.get(stack.get(0), token.toLowerCase());
                    if (nextProduction == null) {
                        error += "\nError on token: " + token;
                        continue;
                    }
                    String[] splited = nextProduction.split(" ");
                    stack.remove(0);
                    for (int i = splited.length - 1; i >= 0; i--) {
                        if (splited[i].length() > 0) {
                            stack.add(0, splited[i]);
                        }

                    }
                }
            }
        }

        if (error.isEmpty()) {
            System.out.println("Parser Done: Code OK");
        } else {
            System.err.println(error);
        }

//        while (lex.hasTokens()) {
//            Token token = lex.getNextToken();
////            parseTable.get(null, null);
//            System.out.println(token.toString());
//        }
        return true;
    }

}
