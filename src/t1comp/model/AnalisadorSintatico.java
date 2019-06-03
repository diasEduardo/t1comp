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
        ArrayList<String> tokens = new ArrayList();
        tokens.add("CLASS");
//          tokens.add("BLANK");
        tokens.add("IDENT");
        tokens.add("OBRACE");
//          tokens.add("BLANK");
        tokens.add("CBRACE");

        String currentProduction = "PROGRAM";
        while (!tokens.isEmpty()) {
            String token = tokens.get(0);
            String nextProduction = "";
            nextProduction = parseTable.get(currentProduction, token.toLowerCase());
            if (nextProduction == null) {
                error = "Error on token: " + token;
            }
            String[] splited = nextProduction.split(" ");
            for (String derivation : splited) {
                if (tokens.get(0).toLowerCase().equals(derivation)) {
                    tokens.remove(0);
                    if (!tokens.isEmpty()) {
                        token = tokens.get(0);
                    }
                } else {
                    currentProduction = derivation;
                    break;
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
