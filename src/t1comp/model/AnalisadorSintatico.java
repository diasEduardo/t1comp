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
                    if (nextProduction == null) {
                        errorMessage += "\nError on token: " + token;
                        errorMessage += "- Lines: " + String.valueOf(tokenLines[0])
                                + ": " + String.valueOf(tokenLines[1]);
                        break;
                    }

                    String[] splited = nextProduction.split(" ");
                    stack.remove(0);
                    current = nodeTreeStack.remove(0);

                    for (int i = splited.length - 1; i >= 0; i--) {
                        if (splited[i].length() > 0) {
                            String name = splited[i];
                            SemanticNode newNode = new SemanticNode(semanticTable.genId(), name, current);
                            semanticTable.addNode(newNode);
                            stack.add(0, name);
                            current.addChild(newNode);
                            nodeTreeStack.add(0, newNode);
                        } else if (splited[i].equals("")) {
                            SemanticNode newNode = new SemanticNode(semanticTable.genId(), "", current);
                            semanticTable.addNode(newNode);
                            current.addChild(newNode);
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

    public void postOder(SemanticNode root) {
        for (SemanticNode child : root.getChildren()) {
            postOder(child);
        }
        
        root.toString();
    }
    
   
    public void buildSemanticTable(SemanticNode root) {
        root.toString(); 
        if (root.getName().equalsIgnoreCase("LVALUE")) {
            if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("LVALUET2")) {
                //LVALUET2.her = tabSimbolo(ident)
                //LVALUET2.hertype = type(ident)
                //LVALUE.node = new leaf(id, LVALUET2.sin)
            }
            
        } else if (root.getName().equalsIgnoreCase("LVALUET2")) {
            if (root.getChild(0).getName().equalsIgnoreCase("")) {
                semanticTable.addRule(root.getId(), new atributeAssertion(root.getChild(1).getId(), "sin", root.getId(), "her"));
            } else if (root.getChildren().size() >=4 &&
                    root.getChild(0).getName().equalsIgnoreCase("obrace") && 
                    root.getChild(0).getName().equalsIgnoreCase("instconst") && 
                    root.getChild(0).getName().equalsIgnoreCase("cbrace") && 
                    root.getChild(0).getName().equalsIgnoreCase("LVALUET2")) {
                
//                ?
            }
            
        } else if (root.getName().equalsIgnoreCase("EXPRESSION")) {
            if (root.getChild(0).getName().equalsIgnoreCase("NUMEXPRESSION") && root.getChild(1).getName().equalsIgnoreCase("EXPRESSION1")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        new atributeAssertion(root.getId(), "her", root.getChild(0).getId(), "her"), //("TERM2.her","TERM3.her") 
                                        new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "node")))); //("TERM3.sin", "TERM2.node")
            }

        } else if (root.getName().equalsIgnoreCase("EXPRESSION1")) {
            if (root.getChild(0).getName().equalsIgnoreCase("EXPRESSIONCOMPARE") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
//                semanticTable.addRule(root.getId(), new newNode(root.getId(),"node", 
//                        root.getChild(0).getId(),"node", root.getChild(1).getId() , "node"));
//                TODO MAKE WITH 3 PARAMS CONSTRUCTOR
            }

        }  else if (root.getName().equalsIgnoreCase("EXPRESSIONCOMPARE")) {

            if (root.getChild(0).getName().equalsIgnoreCase("!=")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "!=")); //("MULDIVMOD.node", "%")
            } else if (root.getChild(0).getName().equalsIgnoreCase("==")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "==")); //("MULDIVMOD.node", "/")
            } else if (root.getChild(0).getName().equalsIgnoreCase(">=")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", ">=")); //("MULDIVMOD.node", "*")
            } else if (root.getChild(0).getName().equalsIgnoreCase("<=")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "<=")); //("MULDIVMOD.node", "*")
            } else if (root.getChild(0).getName().equalsIgnoreCase(">")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", ">")); //("MULDIVMOD.node", "*")
            } else if (root.getChild(0).getName().equalsIgnoreCase("<")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "<")); //("MULDIVMOD.node", "*")
            }

        } else if (root.getName().equalsIgnoreCase("NUMEXPRESSION")) {
        	if (root.getChild(0).getName().equalsIgnoreCase("TERM") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION1")) {
        		semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"), //("TERM2.her","TERM3.her") 
                                        new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "node")))); //("TERM3.sin", "TERM2.node")
        	}
        } else if (root.getName().equalsIgnoreCase("NUMEXPRESSION1")) {
        	if (root.getChild(0).getName().equalsIgnoreCase("")) {
        		semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her")); //("MULDIVMOD.node", "*")
        	} else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
//                        semanticTable.addRule(root.getId(), new newNode(root.getId(),"node",  
//                                root.getChild(0).getId(),"node", 
//                                root.getId() , "her", 
//                                root.getChild(1).getId(), "node"));
//                    TODO!
        	}

        } else if (root.getName().equalsIgnoreCase("SUMMINUS")) {
        	if (root.getChild(0).getName().equalsIgnoreCase("+")) {
        		semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "+")); //("MULDIVMOD.node", "*")
        	} else if (root.getChild(0).getName().equalsIgnoreCase("-")) {
        		semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "-")); //("MULDIVMOD.node", "*")
        	}

        } else if (root.getName().equalsIgnoreCase("TERM2")) {
            if (root.getChild(0).getName().equalsIgnoreCase("MULDIVMOD") && root.getChild(1).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(2).getName().equalsIgnoreCase("TERM3")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        //    TODO newNode(3)                                        new newNode(root.getId(),"node", root.getChild(0).getId(),"her", root.getChild(1).getId() , "sin"), //("TERM2.her","TERM3.her") 
                                        new atributeAssertion(root.getChild(2).getId(), "her", root.getChild(1).getId(), "sin"))));
            }
        } else if (root.getName().equalsIgnoreCase("TERM")) {
            if (root.getChild(0).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(1).getName().equalsIgnoreCase("TERM3")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                        new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "sin"))));
            }
        } else if (root.getName().equalsIgnoreCase("TERM3")) {
            if (root.getChild(0).getName().equalsIgnoreCase("")) {
                semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
            } else if (root.getChild(0).getName().equalsIgnoreCase("TERM2")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"), //("TERM2.her","TERM3.her") 
                                        new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node")))); //("TERM3.sin", "TERM2.node")
            }
        } else if (root.getName().equalsIgnoreCase("MULDIVMOD")) {

            if (root.getChild(0).getName().equalsIgnoreCase( "mod")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "%")); //("MULDIVMOD.node", "%")
            } else if (root.getChild(0).getName().equalsIgnoreCase("div") ){
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "/")); //("MULDIVMOD.node", "/")
            } else if (root.getChild(0).getName().equalsIgnoreCase("mul")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "*")); //("MULDIVMOD.node", "*")
            }

        } else if (root.getName().equalsIgnoreCase("UNARYEXPR")) {
            if (root.getChild(0).getName().equalsIgnoreCase("FACTOR")) {
                semanticTable.addRule(root.getId(), new atributeAssertion(root.getChild(0).getId(), "sin", root.getChild(0).getId(), "node"));
            } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("FACTOR")) {
                semanticTable.addRule(root.getId(), new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getChild(1).getId(), "node")); //("UNARYEXPR.node", "SUMMINUS.node", "FACTOR.node")
            }
        } else if (root.getName().equalsIgnoreCase("FACTOR")) {
            if (root.getChild(0).getName().equalsIgnoreCase("null")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "null")); //("FACTOR.node", "null")
            } else if (root.getChild(0).getName().equalsIgnoreCase("stringconst")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "stringconst")); //("FACTOR.node","stringconst")
            } else if (root.getChild(0).getName().equalsIgnoreCase( "intconst")) {
                semanticTable.addRule(root.getId(), new newLeaf(root.getId(), "node", "intconst")); //("FACTOR.node", "intconst")
            }
        }

        root.getChildren().stream().forEach((child) -> {
            buildSemanticTable(child);
        });
    }
}