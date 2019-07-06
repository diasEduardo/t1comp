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
import t1comp.model.semanticRules.addType;
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
    private AllocationTable allocTable;
    
    private ArrayList<String> generatedInterCode;
    SemanticNode rootNode;

    
    public AnalisadorSintatico() {
        parseTable = TableBuilder.buildTable();
        errorMessage = "";
        symbolsTable = SymbolsTable.getInstance();
        semanticTable = SemanticTable.getInstance();
        allocTable = AllocationTable.getInstance();
        generatedInterCode = new ArrayList();
    }

    public String statusMessage() {
        String succesMsg = "Parser Analisis: Ok \n\n".concat(allocTable.getStatusMessage());
        return errorMessage.isEmpty() ? succesMsg : errorMessage;
    }

    private void cleanErrorMessage() {
        errorMessage = "";
    }

    public boolean parse(AnalisadorLexico lex) {
        cleanErrorMessage();
        allocTable.cleanStatusMessage();
        ArrayList<String> stack = new ArrayList<String>();
        ArrayList<SemanticNode> nodeTreeStack = new ArrayList<SemanticNode>();
        stack.add("PROGRAM");
        SemanticNode nullNode = new SemanticNode(semanticTable.genId(), "null", SemanticNode.NULL_PARENT);
        semanticTable.addNode(nullNode);
        rootNode = new SemanticNode(semanticTable.genId(), "PROGRAM", SemanticNode.NULL_PARENT);
        semanticTable.addNode(rootNode);
        nodeTreeStack.add(rootNode);
        SemanticNode current;
        while (lex.hasTokens()) {
            Token tokenObj = lex.getNextToken();
            String token = tokenObj.getTypeName();//tokens.get(0);
            int[] tokenLines = symbolsTable.getLinesByIndex(tokenObj.getTableIndex() -1);;

            if ("BLANK".equals(token)) {
                continue;
            }
            boolean tokenMatch = false;
            while (!tokenMatch) {
                if (token.toLowerCase().equals(stack.get(0).toLowerCase())) {
                    if (token.equalsIgnoreCase("ident") || token.equalsIgnoreCase("intconst")) {
                        semanticTable.getNode(nodeTreeStack.get(0).getId()).setTableId(tokenObj.getTableIndex() - 1);
//                        newNode.setTableId(tokenObj.getTableIndex());
                    }
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
                            SemanticNode newNode = new SemanticNode(semanticTable.genId(), name, current.getId());
                            semanticTable.addNode(newNode);
                            stack.add(0, name);
                            semanticTable.getNode(current.getId()).addChild(newNode.getId());
                            nodeTreeStack.add(0, newNode);
                            
                        } else if (splited[i].equals("")) {
                            SemanticNode newNode = new SemanticNode(semanticTable.genId(), "", current.getId());
                            semanticTable.addNode(newNode);
                            semanticTable.getNode(current.getId()).addChild(newNode.getId());
                        }

                    }
                }
            }
        }

        if (errorMessage.isEmpty()) {
            System.out.println("Parser Done: Code OK");
        } else {
            System.err.println(errorMessage);
            return false;
        }

        buildSemanticTable(rootNode);
        postOder(rootNode);
        
       
        return true;
    }

    public void postOder(SemanticNode root) {
        for (SemanticNode child : root.getChildren()) {
            postOder(child);
        }

        ArrayList<SemanticRule> rules = semanticTable.getRule(root.getId());
        if (null != rules) {
            rules.forEach((rule) -> {
                rule.action();
            });
        }
    }

    public void buildSemanticTable(SemanticNode root) {
        root.toString();
        String rootName = root.getName().toLowerCase();
        
        switch (rootName.toUpperCase()) {
            case "PROGRAM":
                break;
                
            case "VARDECL1":
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS") && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                            )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECL2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                            )));
                }
                break;
            case "VARDECL2": 
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her")
                            )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
                }
                break;
            case "VARDECLBRACKETS":
                if (root.getChild(0).getName().equalsIgnoreCase("obrack") && root.getChild(1).getName().equalsIgnoreCase("intconst") && root.getChild(2).getName().equalsIgnoreCase("cbrack") && root.getChild(3).getName().equalsIgnoreCase("VARDECLBRACKETS1")) {
                    String tabsimbol = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                            new atributeAssertion(root.getChild(3).getId(), "her", root.getId(), "her"),
                                            //VARDECLBRACKETS.sin = array(tabSimbolo(int-constant),VARDECLBRACKETS1.sin)    
                                            new newNode(root.getId(), "sin","array",tabsimbol,root.getChild(3).getId(),"sin")
                                    )));
                    allocTable.addAllocAction(root.getId(), "int", Integer.parseInt(tabsimbol));

                }
                break;
                
            case "VARDECLBRACKETS1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
            } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                        )));
            }
                break;
                
            case "VARDECLWITHCOMA":
            if (root.getChild(0).getName().equalsIgnoreCase("comma") && root.getChild(1).getName().equalsIgnoreCase("ident") && root.getChild(2).getName().equalsIgnoreCase("VARDECLWITHCOMA1")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                new atributeAssertion(root.getChild(2).getId(), "her", root.getId(), "her"),
                                new addType(semanticTable.getNode(root.getChild(1).getId()).getTableId(), root.getChild(2).getId(), "sin")
                        )));
            }
                break;
                
            case "VARDECLWITHCOMA1":
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS") && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin"),
                                new atributeAssertion(root.getChild(1).getId(), "her", root.getId(), "her")
                        )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her")
                            )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
                }
                break;
                
            case "STATEMENT":
                break;
                
            case "ATRIBSTAT":
                break;
                
            case "PRINTSTAT":
                break;
                
            case "READSTAT":
                break;
                
            case "RETURNSTAT":
                break;
            
            case "RETURNSTAT1":
                break;
                
            case "IFSTAT":
                break;
                
            case "IFSTAT1":
                break;
            case "FORSTAT":
                break;
            case "STATLIST":   
                break;
            case "STATLIST1":  
                break;
            case "LVALUE":
                if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("LVALUET2")) {
                String tabsimbolIdent = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                semanticTable.addRule(root.getId(),
                        new ArrayList<>(Arrays.asList(
                                        new newLeaf((root.getChild(1).getId()), "her", tabsimbolIdent),
                                        new atributeAssertion(root.getId(), "node", root.getChild(1).getId(), "sin"),
                                        new atributeAssertion(root.getId(), "addher", tabsimbolIdent),
                                        new atributeAssertion(root.getId(), "code", root.getChild(1).getId(), "code"),
                                        new atributeAssertion(root.getId(), "addr", root.getChild(1).getId(), "addr")
                                )));
            }
                break;
            case "LVALUET2":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                new atributeAssertion(root.getId(), "sin", root.getId(), "her"),
                                new atributeAssertion(root.getId(), "code", ""),
                                new atributeAssertion(root.getId(), "addr", root.getId(), "addher"))));
                    
                } else if (root.getChild(0).getName().equalsIgnoreCase("obrack")
                        && root.getChild(1).getName().equalsIgnoreCase("intconst")
                        && root.getChild(2).getName().equalsIgnoreCase("cbrack")
                        && root.getChild(3).getName().equalsIgnoreCase("LVALUET2")) {
                    String tabsimbolIdent = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    Integer id = root.getChild(1).getId();
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
    //                                        LVALUET2’.her = LVALUET2.her + ”[" + tabSimbolo(int-constant) + "]”
                                            new newNode(root.getChild(3).getId(), "her","array",tabsimbolIdent,root.getId(),"her"), 
                                            new atributeAssertion(root.getId(), "sin", root.getChild(3).getId(), "sin"),
                                            new atributeAssertion(root.getId(), "addr", newTemp()),
                                            new atributeAssertion(root.getId(), "local", newTemp()),
//                                            LVALUE2.code = gen(LVALUE2.local ‘=’ tabSimbolo(int-constant) ‘*’ ‘4’) || gen(LVALUE2.addr ‘=’ LVALUE2.addrher ‘+’ LVALUE2.local ) || LVALUE2’.code

                                            new atributeAssertion(root.getId(), "addher", root.getId(), "addr"))));

                } 
                break;
                  
            case "NUMEXPRESSION":
                if (root.getChild(0).getName().equalsIgnoreCase("TERM") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION1")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "node"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(0).getId(), "code"),
//                                    || NUMEXPRESSION.CODE
                                    new atributeAssertion(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "addr", root.getChild(1).getId(), "addr")
                                    )));
                }
                break;
               
            case "NUMEXPRESSION1":
                    if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                new atributeAssertion(root.getId(), "node", root.getId(), "her"),
                                new atributeAssertion(root.getId(), "addr", root.getId(), "addrher"),
                                new atributeAssertion(root.getId(), "code", "addrher"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getId(), "her", root.getChild(1).getId(), "node"),
                                new atributeAssertion(root.getId(), "addr", newTemp()),
                                new atributeAssertion(root.getId(), "code", root.getChild(1).getId(), "addrher")
// ||                                   gen(NUMEXPRESSION1.addr ‘=’ NUMEXPRESSION1.addrher SUMMINUS.addr NUMEXPRESSION.addr)

                            )));
                }
                break;
              
            case "SUMMINUS":
                if (root.getChild(0).getName().equalsIgnoreCase("plus")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                new newLeaf(root.getId(), "node", "+"),
                                new atributeAssertion(root.getId(), "addr", "+"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("minus")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                new newLeaf(root.getId(), "node", "-"),
                                new atributeAssertion(root.getId(), "addr", "-"))));
                }
                break;
              
            case "TERM":
                if (root.getChild(0).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(1).getName().equalsIgnoreCase("TERM3")) {
                semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "sin"),
                                    new atributeAssertion(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(0).getId(), "code"),
//                                    || TERM3.code
                                    new atributeAssertion(root.getId(), "addr", root.getChild(1).getId(), "addr"))));
                }
                break;
            case "TERM2":
                if (root.getChild(0).getName().equalsIgnoreCase("MULDIVMOD") && root.getChild(1).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(2).getName().equalsIgnoreCase("TERM3")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getId(), "her", root.getChild(2).getId(), "sin"),
                                    new atributeAssertion(root.getChild(2).getId(), "her", root.getChild(1).getId(), "sin"),
                                    new atributeAssertion(root.getId(), "addr", newTemp()),
                                    new atributeAssertion(root.getId(), "code", root.getChild(1).getId(), "code"),
                                    new atributeAssertion(root.getChild(2).getId(), "addrher", root.getId(), "addr"))));
//      TERM3.code ?              
//      gen(TERM2.addr ‘=’ TERM2.addrher MULDIVMOD.addr UNARYEXPR.addr)?
                }
                break;
                
            case "TERM3":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "addr", root.getId(), "addrher"),
                                    new atributeAssertion(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("TERM2")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertion(root.getChild(0).getId(), "addrher", root.getId(), "addrher"),
                                    new atributeAssertion(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(0).getId(), "code"))));
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node"))));
                }
                break;
            
            case "MULDIVMOD":      
                if (root.getChild(0).getName().equalsIgnoreCase("mod")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "%"),
                                    new atributeAssertion(root.getId(), "addr", "%"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("div")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "/"),
                                    new atributeAssertion(root.getId(), "addr", "/"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("mul")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "*"),
                                    new atributeAssertion(root.getId(), "addr", "*"))));
                    
                }
                break;
            case "UNARYEXPR":
                if (root.getChild(0).getName().equalsIgnoreCase("FACTOR")) {
                semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertion(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("FACTOR")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getChild(1).getId(), "node"),
                                    new atributeAssertion(root.getId(), "addr", newTemp()),
                                    new atributeAssertion(root.getId(), "code", root.getChild(1).getId(), "code"))));
// TODO
//                    gen(UNARYEXPR.addr ‘=’ SUMMINUS.addr FACTOR.addr)//ou seja temp = +/- factor 
                }
                break;
            case "FACTOR":
                if (root.getChild(0).getName().equalsIgnoreCase("null")) {
                String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertion(root.getId(), "addr", tableValue),
                                    new atributeAssertion(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("stringconst")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertion(root.getId(), "addr", tableValue),
                                    new atributeAssertion(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("intconst")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertion(root.getId(), "addr", tableValue),
                                    new atributeAssertion(root.getId(), "code", ""))));
                    
                } else if (root.getChild(0).getName().equalsIgnoreCase("LVALUE")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "node", root.getChild(0).getId(), "node"),
                                    new atributeAssertion(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("OPAR") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION") && root.getChild(2).getName().equalsIgnoreCase("CPAR")) {
                    semanticTable.addRule(root.getId(), 
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "node", root.getChild(1).getId(), "sin"),
                                    new atributeAssertion(root.getId(), "addr", root.getChild(1).getId(), "addr"),
                                    new atributeAssertion(root.getId(), "code", root.getChild(1).getId(), "code"))));
                }
                break;  
        }
        
        for (int i = 0; i < root.getChildren().size(); i++) {
            buildSemanticTable(root.getChild(i));
        }
    }
    
    private String newTemp() {
        return "t1";
    }
 }
