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
import t1comp.model.semanticRules.assertionString.StringAssertionBundle;
import t1comp.model.semanticRules.assertionString.generatorStringeAssertionBundle;
import t1comp.model.semanticRules.assertionString.simpleStringAssertionBundle;
import t1comp.model.semanticRules.atributeAssertion;
import t1comp.model.semanticRules.atributeAssertionString;
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
    private int tempIdCounter = 0;
    private int labelIdCounter = 0;
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
            int[] tokenLines = symbolsTable.getLinesByIndex(tokenObj.getTableIndex() - 1);;

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
//                STATEMENT.next = newLabel();
//                STATEMENT.return = STATEMENT.next
//                PROGRAM.code = STATEMENT.code + label(STATEMENT.next)+gen(exit)
                break;

            case "VARDECL1":
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS") && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                            )));
//                    VARDECL1.code = VARDECL2.code + VARDECLBRACKETS.code
//                    VARDECL1.width = VARDECLBRACKETS.width
                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECL2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                            )));
//                    VARDECL1.code = VARDECL2.code 
//                    VARDECL1.width = 1
                }
                break;
            case "VARDECL2":
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her")
                            )));
//                    VARDECL1.code = VARDECLWITHCOMA.code 
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
//                    VARDECL2.code = ''
                }
                break;
            case "VARDECLBRACKETS":
                if (root.getChild(0).getName().equalsIgnoreCase("obrack") && root.getChild(1).getName().equalsIgnoreCase("intconst") && root.getChild(2).getName().equalsIgnoreCase("cbrack") && root.getChild(3).getName().equalsIgnoreCase("VARDECLBRACKETS1")) {
                    String tabsimbol = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(3).getId(), "her", root.getId(), "her"),
                                    //VARDECLBRACKETS.sin = array(tabSimbolo(int-constant),VARDECLBRACKETS1.sin)    
                                    new newNode(root.getId(), "sin", "array", tabsimbol, root.getChild(3).getId(), "sin")
                            )));
                    allocTable.addAllocAction(root.getId(), "int", Integer.parseInt(tabsimbol));
//                    VARDECLBRACKETS.width = newTemp()
//                    VARDECLBRACKETS.code = VARDECLBRACKETS1.code +"\n"+ 
//                    gen(VARDECLBRACKETS.width ‘=’VARDECLBRACKETS1.width ‘*’ tabSimbolo(int-const))

                }
                break;

            case "VARDECLBRACKETS1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
//                    VARDECLBRACKETS1.width = '1'
//                    VARDECLBRACKETS1.code = ''

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "sin")
                            )));
//                    VARDECLBRACKETS1.width = VARDECLBRACKETS.width
//                    VARDECLBRACKETS1.code = VARDECLBRACKETS.code

                }
                break;

            case "VARDECLWITHCOMA":
                if (root.getChild(0).getName().equalsIgnoreCase("comma") && root.getChild(1).getName().equalsIgnoreCase("ident") && root.getChild(2).getName().equalsIgnoreCase("VARDECLWITHCOMA1")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(2).getId(), "her", root.getId(), "her"),
                                    new addType(semanticTable.getNode(root.getChild(1).getId()).getTableId(), root.getChild(2).getId(), "sin")
                            )));
//                    VARDECLWITHCOMA.width = ‘4’
//                    VARDECLWITHCOMA.aloc = newTemp()
//                    VARDECLWITHCOMA.code = VARDECLWITHCOMA1.code +"\n"+
//                     gen(VARDECLWITHCOMA.aloc ‘=’ VARDECLWITHCOMA.width ‘*’ VARDECLWITHCOMA1.width) +"\n"+
//                    gen(aloc tabSimbolo(ident) VARDECLWITHCOMA.aloc )
//                     da linha de cima pode ser pego ident e vardeclwithcoma.aloc pra informar quantos bytes precisa alocar

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
//                    VARDECLWITHCOMA1.code = VARDECL2.code +"\n"+ VARDECLBRACKETS.code 
//                    VARDECLWITHCOMA1.width = VARDECLBRACKETS.width

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her")
                            )));
//                    VARDECLWITHCOMA1.code = VARDECLWITHCOMA.code
//                    VARDECLWITHCOMA1.width = ‘1’                    
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(), new atributeAssertion(root.getId(), "sin", root.getId(), "her"));
//                    VARDECLWITHCOMA1.code = ''
//                    VARDECLWITHCOMA1.width = ‘1’                 
                }
                break;

            case "STATEMENT":
                if (root.getChild(0).getName().equalsIgnoreCase("PRINTSTAT") && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
//                  PRINTSTAT.next = STATEMENT.next
//                  STATEMENT.code = PRINTSTAT.code +"\n"+ label(PRINTSTAT.next) +"\n"
                } else if (root.getChild(0).getName().equalsIgnoreCase("READSTAT") && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
//                    READSTAT.next = STATEMENT.next
//                    STATEMENT.code = READSTAT.code || label(READSTAT.next) 
                } else if (root.getChild(0).getName().equalsIgnoreCase("RETURNSTAT") && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
//                    STATEMENT.code = RETURNSTAT.code
                } else if (root.getChild(0).getName().equalsIgnoreCase("IFSTAT")) {
//                    IFSTAT.next = STATEMENT.next
//                    STATEMENT.code = IFSTAT.code || label(IFSTAT.next)

                } else if (root.getChild(0).getName().equalsIgnoreCase("FORSTAT")) {
//                    FORSTAT.next = FORSTAT.next
//                    STATEMENT.code = FORSTAT.code || label(FORSTAT.next)

                } else if (root.getChild(0).getName().equalsIgnoreCase("obrace") && root.getChild(1).getName().equalsIgnoreCase("STATLIST") && root.getChild(2).getName().equalsIgnoreCase("cbrace")) {
//                    STATEMENT.code = STATLIST.code
                } else if (root.getChild(0).getName().equalsIgnoreCase("break") && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
//                    STATEMENT.code = gen(‘goto’ STATEMENT.break) 
                } else if (root.getChild(0).getName().equalsIgnoreCase("semicomma")) {
//                    STATEMENT.code = ‘’         
                } else if (root.getChild(0).getName().equalsIgnoreCase("int") && root.getChild(1).getName().equalsIgnoreCase("ident") && root.getChild(2).getName().equalsIgnoreCase("VARDECL1") && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {
//                    VARDECL1.her =  new leaf(id,"int")
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT.width = ‘4’
//                    STATEMENT.aloc = newTemp()
//                    STATEMENT.code = VARDECL1.code 
//                    || gen(STATEMENT.aloc ‘=’ STATEMENT.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT.aloc )

                } else if (root.getChild(0).getName().equalsIgnoreCase("string") && root.getChild(1).getName().equalsIgnoreCase("ident") && root.getChild(2).getName().equalsIgnoreCase("VARDECL1") && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {
//                    VARDECL1.her = new leaf(id,"string") 
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT.width = ‘4’
//                    STATEMENT.aloc = newTemp()
//                    STATEMENT.code = VARDECL1.code 
//                    || gen(STATEMENT.aloc ‘=’ STATEMENT.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT.aloc )

                } else if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("STATEMENT")) {
//                    STATEMENT1.her = new leaf(id,tabSimbolo(ident))
//                    STATEMENT.code = STATEMENT1.code

                }
                break;

            case "STATEMENT1":
                if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("VARDECL1") && root.getChild(2).getName().equalsIgnoreCase("semicomma")) {
//                    VARDECL1.her = STATEMENT1.her
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT1.width = ‘4’
//                    STATEMENT1.aloc = newTemp()
//                    STATEMENT1.code = VARDECL1.code 
//                    || gen(STATEMENT1.aloc ‘=’ STATEMENT1.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT1.aloc )

                } else if (root.getChild(0).getName().equalsIgnoreCase("at") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION") && root.getChild(2).getName().equalsIgnoreCase("semicomma")) {
//                    STATEMENT1.addr = getName(STATEMENT1.her)
//                    STATEMENT1.code = NUMEXPRESSION.code || gen(STATEMENT1.addr ‘=’NUMEXPRESSION.addr)

                }
                break;

            case "ATRIBSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("LVALUE") && root.getChild(1).getName().equalsIgnoreCase("at")
                        && root.getChild(2).getName().equalsIgnoreCase("NUMEXPRESSION")) {
//                    ATRIBSTAT.addr = LVALUE.addr
//                    ATRIBSTAT.code = NUMEXPRESSION.code || LVALUE.code  || gen(LVALUE.addr ‘=’NUMEXPRESSION.addr) DONE
                    SemanticNode ATRIBSTAT = root, 
                            LVALUE = root.getChild(0),
                            NUMEXPRESSION = root.getChild(2);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(LVALUE.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(LVALUE.getId(), "addr", "=", NUMEXPRESSION.getId(), "addr"));
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(ATRIBSTAT.getId(), "addr", LVALUE.getId(), "addr"),
                                    new atributeAssertionString(ATRIBSTAT.getId(), "code", bundle)
                            )));

                }
                break;

            case "PRINTSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("print") 
                        && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
//                    PRINTSTAT.code = NUMEXPRESSION.code || gen(‘out’ NUMEXPRESSION.addr)  DONE
                    SemanticNode PRINTSTAT = root, NUMEXPRESSION = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("out", NUMEXPRESSION.getId(), "addr"));

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(PRINTSTAT.getId(), "code", bundle)
                            )));

                }
                break;

            case "READSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("read") && root.getChild(1).getName().equalsIgnoreCase("LVALUE")) {
//                    READSTAT.code = LVALUE.code || gen(‘in’ LVALUE.addr) 
                    SemanticNode READSTAT = root, LVALUE = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(LVALUE.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("out", LVALUE.getId(), "addr"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(READSTAT.getId(), "code", bundle)
                            )));
                }
                break;

            case "RETURNSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("return") 
                        && root.getChild(1).getName().equalsIgnoreCase("RETURNSTAT1")) {
                    SemanticNode RETURNSTAT = root, RETURNSTAT1 = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(RETURNSTAT1.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto", RETURNSTAT1.getId(), "addr"));
                    
//                     gen(‘goto’ STATEMENT.return) DONE?
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(RETURNSTAT1.getId(), "code", bundle)
                            )));
//                RETURNSTAT.code = RETURNSTAT1.code || gen(‘goto’ STATEMENT.return)

                }
                break;

            case "RETURNSTAT1":
                if (root.getChild(0).getName().equalsIgnoreCase("NUMEXPRESSION")) {
                    SemanticNode RETURNSTAT1 = root, 
                            NUMEXPRESSION = root.getChild(0);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(RETURNSTAT1.getId(), "code", NUMEXPRESSION.getId(), "code")
                            )));
//                    RETURNSTAT1.code = NUMEXPRESSION.code DONE
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    SemanticNode RETURNSTAT1 = root;
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(RETURNSTAT1.getId(), "code", "")
                            )));
                            
//                    RETURNSTAT1.code = ‘’ DONE

                }
                break;

            case "IFSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("if") 
                        && root.getChild(1).getName().equalsIgnoreCase("opar")
                        && root.getChild(2).getName().equalsIgnoreCase("NUMEXPRESSION") 
                        && root.getChild(3).getName().equalsIgnoreCase("cpar") 
                        && root.getChild(4).getName().equalsIgnoreCase("STATEMENT") 
                        && root.getChild(5).getName().equalsIgnoreCase("IFSTAT1")) {
//                    NUMEXPRESSION.true = newLabel();
//                    NUMEXPRESSION.false = newLabel();
//                    STATEMENT.next = IFSTAT.next
//                    IFSTAT1.next = IFSTAT.next
//                    IFSTAT.code = NUMEXPRESSION.code || gen(if NUMEXPRESSION.addr ‘!=’ ‘0’ ‘goto’ NUMEXPRESSION.true)|| label(NUMEXPRESSION.false) ||IFSTAT.code
//                    || label(NUMEXPRESSION.true) ||STATEMENT.code || gen(‘goto’ STATEMENT.next) DONE
                    
                    SemanticNode IFSTAT = root, 
                            NUMEXPRESSION = root.getChild(2),
                            STATEMENT = root.getChild(4),
                            IFSTAT1 = root.getChild(5);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("if", NUMEXPRESSION.getId(), "addr", " != 0 ", "goto", NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "false"));
                    bundle.add(new simpleStringAssertionBundle(IFSTAT.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto", STATEMENT.getId(), "next"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(NUMEXPRESSION.getId(), "true", newLabel()),
                                    new atributeAssertionString(NUMEXPRESSION.getId(), "false", newLabel()),
                                    new atributeAssertionString(STATEMENT.getId(), "next", IFSTAT.getId(), "next"),
                                    new atributeAssertionString(IFSTAT1.getId(), "next", IFSTAT.getId(), "next"),
                                    new atributeAssertionString(IFSTAT.getId(), "code", bundle)
                            )));

                }
                break;

            case "IFSTAT1":
                if (root.getChild(0).getName().equalsIgnoreCase("else") && root.getChild(1).getName().equalsIgnoreCase("STATEMENT") && root.getChild(2).getName().equalsIgnoreCase("end")) {
//                    STATEMENT.next = IFSTAT1.next
//                    IFSTAT1.code = STATEMENT.code || gen(‘goto’ STATEMENT.next) DONE
                    SemanticNode IFSTAT1 = root, STATEMENT = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto", STATEMENT.getId(), "next"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(STATEMENT.getId(), "next", IFSTAT1.getId(), "next"),
                                    new atributeAssertionString(IFSTAT1.getId(), "code", bundle)
                            )));

                } else if (root.getChild(0).getName().equalsIgnoreCase("end")) {
                    SemanticNode IFSTAT1 = root;
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new generatorStringeAssertionBundle("goto", IFSTAT1.getId(), "next"));
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(IFSTAT1.getId(), "code", bundle)
                            )));

//                    IFSTAT1.code = gen(‘goto’ IFSTAT1.next) DONE

                }
                break;
            case "FORSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("for") && 
                        root.getChild(1).getName().equalsIgnoreCase("opar") && 
                        root.getChild(2).getName().equalsIgnoreCase("ATRIBSTAT") && 
                        root.getChild(3).getName().equalsIgnoreCase("semicomma") && 
                        root.getChild(4).getName().equalsIgnoreCase("NUMEXPRESSION") && 
                        root.getChild(5).getName().equalsIgnoreCase("semicomma") && 
                        root.getChild(6).getName().equalsIgnoreCase("ATRIBSTAT") && 
                        root.getChild(7).getName().equalsIgnoreCase("cpar") && 
                        root.getChild(8).getName().equalsIgnoreCase("STATEMENT")) {
                    
                    SemanticNode FORSTAT = root, 
                            ATRIBSTAT = root.getChild(2),
                            NUMEXPRESSION = root.getChild(3),
                            ATRIBSTAT_ = root.getChild(6),
                            STATEMENT = root.getChild(8);
                    
//                    NUMEXPRESSION.true = newLabel();
//                    NUMEXPRESSION.false = FORSTAT.next;
//                    FORSTAT.end = newLabel();
//                    FORSTAT.begin = newLabel();
//                    FORSTAT.temp = newTemp()
//                    STATEMENT.next = FORSTAT.end
//                    STATEMENT.break = FORSTAT.next
//                    FORSTAT.code = ATRIBSTAT.code 
//                    || label(FORSTAT.begin) 
//                    || NUMEXPRESSION.code
//                    || gen(if NUMEXPRESSION.addr ‘!=’ ‘0’ ‘goto’ NUMEXPRESSION.true)
//                    || gen(‘goto’ NUMEXPRESSION.false)
//                    || label(NUMEXPRESSION.true) 
//                    || STATEMENT.code
//                    || label(FORSTAT.end) 
//                    || ATRIBSTAT’.code
//                    || gen(‘goto’ FORSTAT.begin) DONE
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(ATRIBSTAT.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(), "label"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("if", NUMEXPRESSION.getId(), "addr", " != 0 ", "goto", NUMEXPRESSION.getId(), "true"));
                    bundle.add(new generatorStringeAssertionBundle("goto", NUMEXPRESSION.getId(), "false"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(), "end"));
                    bundle.add(new simpleStringAssertionBundle(ATRIBSTAT.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto", FORSTAT.getId(), "begin"));

                    
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(NUMEXPRESSION.getId(), "true", newLabel()),
                                    new atributeAssertionString(NUMEXPRESSION.getId(), "false", FORSTAT.getId(), "next"),
                                    new atributeAssertionString(FORSTAT.getId(), "begin", newLabel()),
                                    new atributeAssertionString(FORSTAT.getId(), "temp", newTemp()),
                                    new atributeAssertionString(STATEMENT.getId(), "next", FORSTAT.getId(), "end"),
                                    new atributeAssertionString(STATEMENT.getId(), "break", FORSTAT.getId(), "next"),
                                    new atributeAssertionString(FORSTAT.getId(), "code", bundle)
                            )));

                }
                break;
            case "STATLIST":
                if (root.getChild(0).getName().equalsIgnoreCase("STATEMENT") && root.getChild(1).getName().equalsIgnoreCase("STATLIST1")) {
//                     DONE
                    SemanticNode STATLIST = root, 
                            STATEMENT = root.getChild(0),
                            STATLIST1 = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(STATLIST1.getId(), "code"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "code", bundle)
                            )));
                }
                break;
            case "STATLIST1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "code", "")
                            )));
//                    STATLIST1.code = '' DONE

                } else if (root.getChild(0).getName().equalsIgnoreCase("STATLIST")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code")
                            )));
//                    STATLIST1.code = STATLIST.code DONE

                }

                break;
            case "LVALUE":
                if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("LVALUET2")) {
                    String tabsimbolIdent = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf((root.getChild(1).getId()), "her", tabsimbolIdent),
                                    new atributeAssertion(root.getId(), "node", root.getChild(1).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "addher", tabsimbolIdent),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(1).getId(), "code"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addr")
                            )));
                }
                break;
            case "LVALUET2":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "code", ""),
                                    new atributeAssertionString(root.getId(), "addr", root.getId(), "addher"))));

                } else if (root.getChild(0).getName().equalsIgnoreCase("obrack")
                        && root.getChild(1).getName().equalsIgnoreCase("intconst")
                        && root.getChild(2).getName().equalsIgnoreCase("cbrack")
                        && root.getChild(3).getName().equalsIgnoreCase("LVALUET2")) {
                    String tabsimbolIdent = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    Integer id = root.getChild(1).getId();
                    SemanticNode LVALUTE2 = root;
                    SemanticNode LVALUTE2_ = root.getChild(3);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new generatorStringeAssertionBundle(LVALUTE2.getId(),
                            "local", "=", tabsimbolIdent.concat(" * 4")));
                    bundle.add(new generatorStringeAssertionBundle(LVALUTE2.getId(),
                            "addr", "=", LVALUTE2.getId(), "addrher", "+",
                            LVALUTE2.getId(), "local"));
                    bundle.add(new simpleStringAssertionBundle(LVALUTE2_.getId(), "code"));
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    //                                        LVALUET2’.her = LVALUET2.her + ”[" + tabSimbolo(int-constant) + "]”
                                    new newNode(root.getChild(3).getId(), "her", "array", tabsimbolIdent, root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(3).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "local", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle),
                                    new atributeAssertion(root.getId(), "addher", root.getId(), "addr"))));

                }
                break;

            case "NUMEXPRESSION":
                if (root.getChild(0).getName().equalsIgnoreCase("TERM") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION1")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"),
                                    //                                    || NUMEXPRESSION.CODE
                                    new atributeAssertionString(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addr")
                            )));
                }
                break;

            case "NUMEXPRESSION1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "node", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "addr", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "code", "addrher"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getId(), "her", root.getChild(1).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(1).getId(), "addrher")
                            // ||                                   gen(NUMEXPRESSION1.addr ‘=’ NUMEXPRESSION1.addrher SUMMINUS.addr NUMEXPRESSION.addr)

                            )));
                }
                break;

            case "SUMMINUS":
                if (root.getChild(0).getName().equalsIgnoreCase("plus")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "+"),
                                    new atributeAssertionString(root.getId(), "addr", "+"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("minus")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "-"),
                                    new atributeAssertionString(root.getId(), "addr", "-"))));
                }
                break;

            case "TERM":
                if (root.getChild(0).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(1).getName().equalsIgnoreCase("TERM3")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(1).getId(), "sin"),
                                    new atributeAssertionString(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"),
                                    //                                    || TERM3.code
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addr"))));
                }
                break;
            case "TERM2":
                if (root.getChild(0).getName().equalsIgnoreCase("MULDIVMOD") && root.getChild(1).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(2).getName().equalsIgnoreCase("TERM3")) {
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();

                    bundle.add(new simpleStringAssertionBundle(root.getChild(1).getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(root.getId(),
                            "addr", "=", root.getId(), "addrher",
                            root.getChild(0).getId(), "addr", root.getChild(1).getId(), "addr"));
                    bundle.add(new simpleStringAssertionBundle(root.getChild(2).getId(), "code"));

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getId(), "her", root.getChild(2).getId(), "sin"),
                                    new atributeAssertion(root.getChild(2).getId(), "her", root.getChild(1).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle),
                                    new atributeAssertionString(root.getChild(2).getId(), "addrher", root.getId(), "addr"))));
                }
                break;

            case "TERM3":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "addr", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("TERM2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getChild(0).getId(), "addrher", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
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
                                    new atributeAssertionString(root.getId(), "addr", "%"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("div")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "/"),
                                    new atributeAssertionString(root.getId(), "addr", "/"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("mul")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", "*"),
                                    new atributeAssertionString(root.getId(), "addr", "*"))));

                }
                break;
            case "UNARYEXPR":
                if (root.getChild(0).getName().equalsIgnoreCase("FACTOR")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("FACTOR")) {
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();

                    bundle.add(new simpleStringAssertionBundle(root.getChild(1).getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(root.getId(),
                            "addr", "=", root.getChild(0).getId(), "addr",
                            root.getChild(1).getId(), "addr"));

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newNode(root.getId(), "node", root.getChild(0).getId(), "node", root.getChild(1).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle))));
                }
                break;
            case "FACTOR":
                if (root.getChild(0).getName().equalsIgnoreCase("null")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("stringconst")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("intconst")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new newLeaf(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));

                } else if (root.getChild(0).getName().equalsIgnoreCase("LVALUE")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "node", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("OPAR") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION") && root.getChild(2).getName().equalsIgnoreCase("CPAR")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertion(root.getId(), "node", root.getChild(1).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(1).getId(), "code"))));
                }
                break;
        }

        for (int i = 0; i < root.getChildren().size(); i++) {
            buildSemanticTable(root.getChild(i));
        }
    }

    private String newTemp() {
        tempIdCounter++;
        return "T" + tempIdCounter;
    }
    
    private String newLabel() {
        labelIdCounter++;
        return "L" + tempIdCounter + ":";
    }
}
