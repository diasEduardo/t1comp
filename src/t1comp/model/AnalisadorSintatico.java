/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import t1comp.model.TableBuilder;
import t1comp.model.semanticRules.atributeAssertion;
import t1comp.model.semanticRules.newLeaf;
import t1comp.model.semanticRules.newNode;

/**
 *
 * @author nathangodinho
 */
public class AnalisadorSintatico {

    private LL1Table parseTable;
    private String errorMessage;
    private SymbolsTable symbolsTable;
    private SemanticTable semanticTable;

    SemanticNode rootNode;

    public AnalisadorSintatico() {
        parseTable = TableBuilder.buildTable();
        errorMessage = "";
        symbolsTable = SymbolsTable.getInstance();
        semanticTable = SemanticTable.getInstance();
    }

    public String statusMessage() {
        return errorMessage.isEmpty() ? "Parser Analisis: Ok \n" : errorMessage;
    }

    private void cleanErrorMessage() {
        errorMessage = "";
    }

    public boolean parse(AnalisadorLexico lex) {
        cleanErrorMessage();
        ArrayList<String> stack = new ArrayList<String>();
        ArrayList<SemanticNode> nodeTreeStack = new ArrayList<SemanticNode>();
        stack.add("PROGRAM");
        rootNode = new SemanticNode(semanticTable.genId(), "PROGRAM", null);
        semanticTable.addNode(rootNode);
        nodeTreeStack.add(rootNode);
        SemanticNode current;
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
                    nodeTreeStack.remove(0);
//                    action()
                    tokenMatch = true;
                } else {
                    String nextProduction = parseTable.get(stack.get(0), token.toLowerCase());
//                    System.out.println(nextProduction);
                    if (nextProduction == null) {
                        errorMessage += "\nError on token: " + token;
                        errorMessage += "- Lines: " + String.valueOf(tokenLines[0])
                                + ": " + String.valueOf(tokenLines[1]);
                        break;
                    }

                    String[] splited = nextProduction.split(" ");
                    stack.remove(0);
                    current = nodeTreeStack.remove(0);
//                    if (nextProduction == "EXPRESSION") {
//                        System.out.println("!exp!");
//                    }

                    for (int i = splited.length - 1; i >= 0; i--) {
                        if (splited[i].length() > 0) {
                            String name = splited[i];
                            SemanticNode newNode = new SemanticNode(semanticTable.genId(), name, current);
                            semanticTable.addNode(newNode);
                            stack.add(0, name);
                            current.addChild(newNode);
                            nodeTreeStack.add(0, newNode);
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

        buildSemanticTable(rootNode);

        return true;
    }

    public void buildSemanticTable(SemanticNode root) {
        root.toString();

        if (root.getName() == "TERM3") {
            root.getChildren().stream().forEach((child) -> {
                if (child.getName() == "") {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
                } else if (child.getName() == "TERM2") {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                            new atributeAssertion(child.getId(), "her", root.getId(), "her"), //("TERM2.her","TERM3.her") 
                                            new atributeAssertion(root.getId(), "sin", child.getId(), "node")))); //("TERM3.sin", "TERM2.node")
                }
            });

        } else if (root.getName() == "MULDIVMOD") {
            root.getChildren().stream().forEach((child) -> {
                if (child.getName() == "mod") {
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node", "%")); //("MULDIVMOD.node", "%")
                } else if (child.getName() == "div") {
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node", "/")); //("MULDIVMOD.node", "/")
                } else if (child.getName() == "mul"){
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node", "*")); //("MULDIVMOD.node", "*")
                }
            });
        } else if (root.getName() == "UNARYEXPR") {
            if(root.getChildren().size() > 1){
                semanticTable.addRule(root.getId(), new newNode(root.getId(),"node", root.getChild(0).getId(),"node", root.getChild(1).getId() , "node")); //("UNARYEXPR.node", "SUMMINUS.node", "FACTOR.node")
            }else if(root.getChildren().size() == 1){   
                semanticTable.addRule(root.getId(), new atributeAssertion(root.getChild(0).getId(),"sin",root.getChild(0).getId(), "node"));
            }
        } else if (root.getName() == "FACTOR") {
            root.getChildren().stream().forEach((child) -> {
                if (child.getName() == "null") {
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node", "null")); //("FACTOR.node", "null")
                } else if (child.getName() == "stringconst") {
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node","stringconst")); //("FACTOR.node","stringconst")
                } else if (child.getName() == "intconst"){
                    semanticTable.addRule(root.getId(), new newLeaf(root.getId(),"node", "intconst")); //("FACTOR.node", "intconst")
                }
            });
        }

        root.getChildren().stream().forEach((child) -> {
            buildSemanticTable(child);
        });
    }
}

