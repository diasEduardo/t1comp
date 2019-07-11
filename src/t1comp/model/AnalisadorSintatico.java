/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import t1comp.model.TableBuilder;
import t1comp.model.semanticRules.addType;
import t1comp.model.semanticRules.atributeAssertion;
import t1comp.model.semanticRules.newLeaf;
import t1comp.model.semanticRules.newNode;
import t1comp.model.Scope;
import t1comp.model.semanticRules.assertionString.StringAssertionBundle;
import t1comp.model.semanticRules.assertionString.generatorStringeAssertionBundle;
import t1comp.model.semanticRules.assertionString.simpleStringAssertionBundle;
import t1comp.model.semanticRules.atributeAssertionString;

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
    private HashMap<Integer, Scope> scopeTable;
    private int idScope = 0;
    private int tempIdCounter = 0;
    private int labelIdCounter = 0;
    private ArrayList<String> generatedInterCode;
    private SemanticNode rootNode;
    private String interCode;
    private ArrayList<Integer> statmentContext;
  
    public AnalisadorSintatico() {
        parseTable = TableBuilder.buildTable();
        errorMessage = "";
        symbolsTable = SymbolsTable.getInstance();
        semanticTable = SemanticTable.getInstance();
        allocTable = AllocationTable.getInstance();
        scopeTable = new HashMap<>();
        generatedInterCode = new ArrayList();
    }

    public String getInterCode() {
        return "# intermediate Code: \n\n"+ interCode;
    }
    public String statusMessage() {
        String succesMsg = "Parser Analisis: Ok \n\n".concat(allocTable.getStatusMessage());
        return errorMessage.isEmpty() ? succesMsg : errorMessage;
    }

    private void cleanErrorMessage() {
        errorMessage = "";
    }

    private void cleanCounters() {
        tempIdCounter = 0;
        labelIdCounter = 0;
    }
    public boolean parse(AnalisadorLexico lex) {
        statmentContext = new ArrayList<>();
        interCode = "";
        cleanCounters();
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
        SemanticNode current = null;
        ArrayList<Scope> scopeStack = new ArrayList<Scope>();
        Scope current_scope = new Scope(idScope,false);
        scopeTable.put(idScope, current_scope);
        idScope++;
        boolean nextFor = false;
        String lastToken= "";
        String lastTokenName="";
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
                    if (token.equalsIgnoreCase("ident") || token.equalsIgnoreCase("intconst") 
                            || token.equalsIgnoreCase("stringconst")) {
                        semanticTable.getNode(nodeTreeStack.get(0).getId()).setTableId(tokenObj.getTableIndex() - 1);
//                        newNode.setTableId(tokenObj.getTableIndex());
                    }
                    
                    if (token.equalsIgnoreCase("FOR")) {
                        nextFor = true;
                    }
                    
                    if (token.equalsIgnoreCase("OBRACE")) {
                        System.out.println("Escopo criado");
                        scopeStack.add(0,current_scope);
                        if(nextFor){
                            current_scope = new Scope(idScope,nextFor);
                            scopeTable.put(idScope, current_scope);
                            nextFor = false;
                        } else{
                            current_scope = new Scope(idScope,current_scope.isInsideFor());
                            scopeTable.put(idScope, current_scope);
                        }
                    }
                    
                    if (token.equalsIgnoreCase("CBRACE")) {
                        current_scope = scopeStack.remove(0);
                        System.out.println("Escopo fechado");
                    }
                    
                    if (token.equalsIgnoreCase("BREAK")) {
                        if((lastToken.equalsIgnoreCase("CPAR") && nextFor) || current_scope.isInsideFor()){
                            System.out.println("Comando break dentro do loop");
                        }
                        else{
                            errorMessage += "\nError on token (break outside loop): " + token;
                            errorMessage += "- Lines: " + String.valueOf(tokenLines[0])
                                + ": " + String.valueOf(tokenLines[1]);
                            break;
                        }
                    }
                    
                    if (token.equalsIgnoreCase("IDENT"))  {
                        String tokenName = symbolsTable.getSymbol(tokenObj.getTableIndex() - 1);
                        if (  !lex.verifyIdent()
                                && !lastToken.equalsIgnoreCase("AT")
                                && !lastToken.equalsIgnoreCase("PLUS")
                                && !lastToken.equalsIgnoreCase("MINUS")
                                && !lastToken.equalsIgnoreCase("MUL")
                                && !lastToken.equalsIgnoreCase("DIV")) {
                            if (current_scope.hasVariable(tokenName)) {
                                errorMessage += "\nError, ident has already been declared: " + token + ": " + tokenName;
                                errorMessage += "- Lines: " + String.valueOf(tokenLines[0])
                                        + ": " + String.valueOf(tokenLines[1]);
                                break;
                            }
                            System.out.println("Variável adicionada ao escopo: " + tokenName);
                            if (lastToken.equalsIgnoreCase("CLASS") || lastToken.equalsIgnoreCase("INT") || lastToken.equalsIgnoreCase("STRING")) {
                                current_scope.addVariable(tokenName, lastToken);
                                System.out.println("Tipo: " + lastToken);
                            } else if (lastToken.equalsIgnoreCase("IDENT")) {
                                current_scope.addVariable(tokenName, lastTokenName);
                                System.out.println("Tipo: " + lastTokenName);
                            } else {
                                String tokenTipo = lex.verifyType();
                                current_scope.addVariable(tokenName, tokenTipo);
                                System.out.println("Tipo: "+ tokenTipo);
                            }
                            scopeTable.put(current_scope.getId(), current_scope);
                        }
                        lastTokenName = tokenName;
                    }
                    lastToken = token;
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
        interCode = semanticTable.getNode(rootNode.getId())
                .getStringAttributes("code");
        System.out.println("## CODE ##\n".concat(interCode));
        return true;
    }

    public void checkHer(SemanticNode root) {
        String rootName = root.getName().toLowerCase();

        switch (rootName.toUpperCase()) {
            case "STATEMENT":{
                if (root.getChild(0).getName().equalsIgnoreCase("int")
                        && root.getChild(1).getName().equalsIgnoreCase("ident")
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECL1")
                        && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {
                    
                    SemanticNode VARDECL1 = semanticTable.getNode(root.getChild(2).getId());
                    VARDECL1.stringAttributes.put("her", "int");
                    semanticTable.addNode(VARDECL1);
                    System.out.println("Nova folha: " + VARDECL1.getName() + ".her" + " <- " + VARDECL1.getStringAttributes("her"));

                } else if (root.getChild(0).getName().equalsIgnoreCase("string")
                        && root.getChild(1).getName().equalsIgnoreCase("ident")
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECL1")
                        && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {

                    SemanticNode VARDECL1 = semanticTable.getNode(root.getChild(2).getId());
                    VARDECL1.stringAttributes.put("her", "string");
                    semanticTable.addNode(VARDECL1);
                    System.out.println("Nova folha: " + VARDECL1.getName() + ".her" + " <- " + VARDECL1.getStringAttributes("her"));

                } else if (root.getChild(0).getName().equalsIgnoreCase("ident")
                        && root.getChild(1).getName().equalsIgnoreCase("STATEMENT1")) {
                    SemanticNode STATEMENT1 = semanticTable.getNode(root.getChild(1).getId());
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());

                    STATEMENT1.stringAttributes.put("her", tableValue);
                    semanticTable.addNode(STATEMENT1);
                    System.out.println("Nova folha: " + STATEMENT1.getName() + ".her" + " <- " + STATEMENT1.getStringAttributes("her"));
                }
                break;
            }
            case "STATEMENT1": {
                if (root.getChild(0).getName().equalsIgnoreCase("ident")
                        && root.getChild(1).getName().equalsIgnoreCase("VARDECL1")
                        && root.getChild(2).getName().equalsIgnoreCase("semicomma")) {

                    SemanticNode STATEMENT1 = semanticTable.getNode(root.getId()),
                            VARDECL1 = semanticTable.getNode(root.getChild(1).getId());
                    
                    VARDECL1.stringAttributes.put("her", STATEMENT1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECL1);
                    System.out.println("Atribuindo: "
                            + VARDECL1.getName() + ".her <- "
                            + STATEMENT1.getName() + ".her = " + STATEMENT1.getStringAttributes("her")
                    );

                }
                break;
            }
            case "VARDECL1": {
                SemanticNode VARDECL1 = semanticTable.getNode(root.getId());

                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")
                        && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                    SemanticNode VARDECLBRACKETS = semanticTable.getNode(root.getChild().getId());
                    SemanticNode VARDECL2 = semanticTable.getNode(root.getChild(1).getId());


                    VARDECLBRACKETS.stringAttributes.put("her", VARDECL1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLBRACKETS);
                    System.out.println("Atribuindo: "
                            + VARDECL1.getName() + ".her <- "
                            + VARDECL1.getName() + ".her = " + VARDECL1.getStringAttributes("her")
                    );
                    
                    VARDECL2.stringAttributes.put("her", VARDECL1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECL2);
                    System.out.println("Atribuindo: "
                            + VARDECL2.getName() + ".her <- "
                            + VARDECL1.getName() + ".her = " + VARDECL1.getStringAttributes("her")
                    );

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECL2")) {
                    SemanticNode VARDECL2 = root.getChild(0);

                    VARDECL2.stringAttributes.put("her", VARDECL1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECL2);
                    System.out.println("Atribuindo: "
                            + VARDECL2.getName() + ".her <- "
                            + VARDECL1.getName() + ".her = " + VARDECL1.getStringAttributes("her")
                    );
                }
                break;
            }
            case "VARDECL2": {
                SemanticNode VARDECL2 = semanticTable.getNode(root.getId());
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    SemanticNode VARDECLWITHCOMA = semanticTable.getNode(root.getChild(0).getId());
                    
                    VARDECLWITHCOMA.stringAttributes.put("her", VARDECL2.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLWITHCOMA);
                    System.out.println("Atribuindo: "
                            + VARDECLWITHCOMA.getName() + ".her <- "
                            + VARDECL2.getName() + ".her = " + VARDECL2.getStringAttributes("her")
                    );
                }
                break;
            }
            case "VARDECLBRACKETS": {
                if (root.getChild(0).getName().equalsIgnoreCase("obrack")
                        && root.getChild(1).getName().equalsIgnoreCase("intconst")
                        && root.getChild(2).getName().equalsIgnoreCase("cbrack")
                        && root.getChild(3).getName().equalsIgnoreCase("VARDECLBRACKETS1")) {

                    SemanticNode VARDECLBRACKETS = semanticTable.getNode(root.getId());
                    SemanticNode VARDECLBRACKETS1 = semanticTable.getNode(root.getChild(3).getId());

                    VARDECLBRACKETS1.stringAttributes.put("her", VARDECLBRACKETS.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLBRACKETS1);
                    System.out.println("Atribuindo: "
                            + VARDECLBRACKETS1.getName() + ".her <- "
                            + VARDECLBRACKETS.getName() + ".her = " + VARDECLBRACKETS.getStringAttributes("her")
                    );
                }
                break;
            }
            case "VARDECLBRACKETS1": {
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")) {
                    SemanticNode VARDECLBRACKETS1 = semanticTable.getNode(root.getId());
                    SemanticNode VARDECLBRACKETS = semanticTable.getNode(root.getChild(0).getId());

                    VARDECLBRACKETS.stringAttributes.put("her", VARDECLBRACKETS1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLBRACKETS);
                    System.out.println("Atribuindo: "
                            + VARDECLBRACKETS.getName() + ".her <- "
                            + VARDECLBRACKETS1.getName() + ".her = " + VARDECLBRACKETS1.getStringAttributes("her")
                    );

                }
                break;
            }
            case "VARDECLWITHCOMA": {
                if (root.getChild(0).getName().equalsIgnoreCase("comma")
                        && root.getChild(1).getName().equalsIgnoreCase("ident")
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECLWITHCOMA1")) {
                    SemanticNode VARDECLWITHCOMA = semanticTable.getNode(root.getId());
                    SemanticNode VARDECLWITHCOMA1 = semanticTable.getNode(root.getChild(2).getId());

                    VARDECLWITHCOMA1.stringAttributes.put("her", VARDECLWITHCOMA.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLWITHCOMA1);
                    System.out.println("Atribuindo: "
                            + VARDECLWITHCOMA1.getName() + ".her <- "
                            + VARDECLWITHCOMA.getName() + ".her = " + VARDECLWITHCOMA.getStringAttributes("her")
                    );

                }
                break;
            }
            case "VARDECLWITHCOMA1": {
                SemanticNode VARDECLWITHCOMA1 = semanticTable.getNode(root.getId());
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")
                        && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {

                    SemanticNode VARDECLBRACKETS = semanticTable.getNode(root.getChild(0).getId()),
                            VARDECL2 = semanticTable.getNode(root.getChild(1).getId());

                    VARDECLBRACKETS.stringAttributes.put("her", VARDECLWITHCOMA1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLBRACKETS);
                    System.out.println("Atribuindo: "
                            + VARDECLBRACKETS.getName() + ".her <- "
                            + VARDECLWITHCOMA1.getName() + ".her = " + VARDECLWITHCOMA1.getStringAttributes("her")
                    );

                    VARDECL2.stringAttributes.put("her", VARDECLWITHCOMA1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECL2);
                    System.out.println("Atribuindo: "
                            + VARDECL2.getName() + ".her <- "
                            + VARDECLWITHCOMA1.getName() + ".her = " + VARDECLWITHCOMA1.getStringAttributes("her")
                    );

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    
                    SemanticNode VARDECLWITHCOMA = semanticTable.getNode(root.getChild(0).getId());

                    VARDECLWITHCOMA.stringAttributes.put("her", VARDECLWITHCOMA1.getStringAttributes("her"));
                    semanticTable.addNode(VARDECLWITHCOMA);
                    System.out.println("Atribuindo: "
                            + VARDECLWITHCOMA.getName() + ".her <- "
                            + VARDECLWITHCOMA1.getName() + ".her = " + VARDECLWITHCOMA1.getStringAttributes("her")
                    );
                }
                break;
            }
            case "LVALUE": {
                if (root.getChild(0).getName().equalsIgnoreCase("ident") && root.getChild(1).getName().equalsIgnoreCase("LVALUET2")) {
                    SemanticNode LVALUET2 = semanticTable.getNode(root.getChild(1).getId());
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    
                    LVALUET2.stringAttributes.put("her", tableValue);
                    semanticTable.addNode(LVALUET2);
                    System.out.println("Nova folha: " + LVALUET2.getName() + ".her" + " <- " + LVALUET2.getStringAttributes("her"));
                    LVALUET2.stringAttributes.put("addrher", tableValue);
                    System.out.println("Attribuindo her: " + LVALUET2.getName() + ".addher" + " <- " + LVALUET2.getStringAttributes("addrher"));
 
                }
                break;
            }
            case "LVALUET2": {
                if (root.getChild(0).getName().equalsIgnoreCase("obrack")
                        && root.getChild(1).getName().equalsIgnoreCase("intconst")
                        && root.getChild(2).getName().equalsIgnoreCase("cbrack")
                        && root.getChild(3).getName().equalsIgnoreCase("LVALUET2")) {

                    String tabsimbolIdent = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    Integer id = root.getChild(1).getId();
                    SemanticNode LVALUTE2 = semanticTable.getNode(root.getId());
                    SemanticNode LVALUTE2_ = semanticTable.getNode(root.getChild(3).getId());
                    
                    String node = "array " + tabsimbolIdent + " " + LVALUTE2.getStringAttributes("her");
                    LVALUTE2_.stringAttributes.put("her", node);
                    semanticTable.addNode(LVALUTE2_);
                    System.out.println("Novo nodo: " + LVALUTE2_.getName() + ".her <- " + node);
                }
                break;
            }
            
            case "TERM3":{
                SemanticNode TERM3 = semanticTable.getNode(root.getId());
                if (root.getChild(0).getName().equalsIgnoreCase("TERM2")) {
                    SemanticNode TERM2 = semanticTable.getNode(root.getChild(0).getId());

                    TERM2.stringAttributes.put("her", TERM3.getStringAttributes("her"));
                    semanticTable.addNode(TERM2);
                    System.out.println("Atribuindo: "
                            + TERM2.getName() + ".her <- "
                            + TERM3.getName() + ".her = " + TERM3.getStringAttributes("her")
                    );
                    
                    TERM2.stringAttributes.put("addrher", TERM3.getStringAttributes("addrher"));
                    semanticTable.addNode(TERM2);
                    System.out.println("Atribuindo: "
                            + TERM2.getName() + ".addrher <- "
                            + TERM3.getName() + ".addrher = " + TERM3.getStringAttributes("addrher")
                    );
                }
                break;
            }
            case "TERM2": {
                if (root.getChild(0).getName().equalsIgnoreCase("MULDIVMOD") && root.getChild(1).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(2).getName().equalsIgnoreCase("TERM3")) {
                    SemanticNode TERM2 = semanticTable.getNode(root.getId());
                    SemanticNode MULDIVMOD = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode UNARYEXPR = semanticTable.getNode(root.getChild(1).getId());
                    SemanticNode TERM3 = semanticTable.getNode(root.getChild(2).getId());

                    TERM3.stringAttributes.put("addrher", TERM2.getStringAttributes("addr"));
                    semanticTable.addNode(TERM3);
                    System.out.println("Atribuindo: "
                            + TERM3.getName() + ".addrher <- "
                            + TERM2.getName() + ".addr = " + TERM2.getStringAttributes("addr")
                    );
                }
                break;
            }
            
        }
    }

    public void postOder(SemanticNode root) {
     
        for (SemanticNode child : root.getChildren()) {
            checkHer(child);
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
        String rootName = root.getName().toLowerCase();

        switch (rootName.toUpperCase()) {
            case "PROGRAM":
                
                if (root.getChild(0).getName().equalsIgnoreCase("STATEMENT")) {
                    SemanticNode PROGRAM = root, 
                            STATEMENT = root.getChild(0);
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    String newLabel = newLabel();
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(),"next"));
                    bundle.add(new simpleStringAssertionBundle(" exit ")); 
                    
                    STATEMENT.stringAttributes.put("next", newLabel);
                    STATEMENT.stringAttributes.put("return", STATEMENT.getStringAttributes("next"));
                    statmentContext.add(STATEMENT.getId());
                    semanticTable.addNode(STATEMENT);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(PROGRAM.getId(), "code", bundle)
                            )));
                }
//                STATEMENT.next = newLabel();
//                STATEMENT.return = STATEMENT.next
//                PROGRAM.code = STATEMENT.code + label(STATEMENT.next)+gen(exit) DONE
                break;

            case "VARDECL1": {
                SemanticNode VARDECL1 = root;
                
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS") 
                        && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                    SemanticNode VARDECLBRACKETS = root.getChild(0);
                    SemanticNode VARDECL2 = root.getChild(1);
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECL2.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(VARDECLBRACKETS.getId(),"code"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
//                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "sin"),
                                    new atributeAssertionString(VARDECL1.getId(), "width" , VARDECLBRACKETS.getId(), "width"),
                                    new atributeAssertionString(VARDECL1.getId(), "code", bundle)
                            )));
//                    VARDECL1.code = VARDECL2.code + VARDECLBRACKETS.code
//                    VARDECL1.width = VARDECLBRACKETS.width
                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECL2")) {
                    SemanticNode VARDECL2 = root.getChild(0);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "sin"),
                                    new atributeAssertionString(VARDECL1.getId(), "code", VARDECL2.getId(), "code"),
                                    new atributeAssertionString(VARDECL1.getId(), "width", "1")
                            )));
//                    VARDECL1.code = VARDECL2.code 
//                    VARDECL1.width = 1 DONE
                }
                break;
            } case "VARDECL2": {
                SemanticNode VARDECL2 = root;
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    SemanticNode VARDECLWITHCOMA = root.getChild(0);
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(VARDECL2.getId(), "code", VARDECLWITHCOMA.getId(), "code")
                            )));
//                    VARDECL1.code = VARDECLWITHCOMA.code 
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(VARDECL2.getId(), "code", "")
                            )));
//                    VARDECL2.code = ''
                }
                break;
            } case "VARDECLBRACKETS":
                if (root.getChild(0).getName().equalsIgnoreCase("obrack") && 
                        root.getChild(1).getName().equalsIgnoreCase("intconst") && 
                        root.getChild(2).getName().equalsIgnoreCase("cbrack") && 
                        root.getChild(3).getName().equalsIgnoreCase("VARDECLBRACKETS1")) {
                    
                    String tabsimbol = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    SemanticNode VARDECLBRACKETS = semanticTable.getNode(root.getId());
                    SemanticNode VARDECLBRACKETS1 = semanticTable.getNode(root.getChild(3).getId());
                    
                      
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECLBRACKETS1.getId(),"code"));
//                    bundle.add(new simpleStringAssertionBundle("\n"));
                    bundle.add(new generatorStringeAssertionBundle(VARDECLBRACKETS.getId(),"width", "=", 
                            VARDECLBRACKETS1.getId(), "width".concat(" * " + tabsimbol)));
                    
                    String node = "array " + tabsimbol + " " + VARDECLBRACKETS1.getStringAttributes("sin");
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(3).getId(), "her", root.getId(), "her"),
                                    //VARDECLBRACKETS.sin = array(tabSimbolo(int-constant),VARDECLBRACKETS1.sin)
                                    new atributeAssertionString(root.getId(), "sin", node),
                                    new atributeAssertionString(VARDECLBRACKETS.getId(), "width", newTemp()),
                                    new atributeAssertionString(VARDECLBRACKETS.getId(), "code", bundle)
                            )));
                    allocTable.addAllocAction(root.getId(), "int", Integer.parseInt(tabsimbol));
//                    VARDECLBRACKETS.width = newTemp()
//                    VARDECLBRACKETS.code = VARDECLBRACKETS1.code +"\n"+ 
//                    gen(VARDECLBRACKETS.width ‘=’VARDECLBRACKETS1.width ‘*’ tabSimbolo(int-const))

                }
                break;

            case "VARDECLBRACKETS1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    SemanticNode VARDECLBRACKETS1 = root;
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(VARDECLBRACKETS1.getId(), "width", "1"),
                                    new atributeAssertionString(VARDECLBRACKETS1.getId(), "code", "")
                            )));
//                    VARDECLBRACKETS1.width = '1'
//                    VARDECLBRACKETS1.code = '' DONE

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS")) {
                    SemanticNode VARDECLBRACKETS1 = root;
                    SemanticNode VARDECLBRACKETS = root.getChild(0);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "sin"),
                                    new atributeAssertionString(VARDECLBRACKETS1.getId(), "width", VARDECLBRACKETS.getId(), "width"),
                                    new atributeAssertionString(VARDECLBRACKETS1.getId(), "code", VARDECLBRACKETS.getId(), "code")
                            )));
//                    VARDECLBRACKETS1.width = VARDECLBRACKETS.width
//                    VARDECLBRACKETS1.code = VARDECLBRACKETS.code DONE

                }
                break;

            case "VARDECLWITHCOMA":
                if (root.getChild(0).getName().equalsIgnoreCase("comma") 
                        && root.getChild(1).getName().equalsIgnoreCase("ident") 
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECLWITHCOMA1")) {
                    SemanticNode VARDECLWITHCOMA = root;
                    SemanticNode VARDECLWITHCOMA1 = root.getChild(2);
                    
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                     
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECLWITHCOMA1.getId(),"code"));
//                    bundle.add(new simpleStringAssertionBundle("\n"));
                    bundle.add(new generatorStringeAssertionBundle(VARDECLWITHCOMA.getId(),"aloc", "=", 
                            VARDECLWITHCOMA.getId(), "width", " * ", VARDECLWITHCOMA1.getId(), "width"));
//                    bundle.add(new simpleStringAssertionBundle("\n"));
                    bundle.add(new generatorStringeAssertionBundle("aloc ".concat(tableValue), 
                            VARDECLWITHCOMA.getId(), "aloc"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getChild(2).getId(), "her", root.getId(), "her"),
                                    new addType(semanticTable.getNode(root.getChild(1).getId()).getTableId(), root.getChild(2).getId(), "sin"),
                                    new atributeAssertionString(VARDECLWITHCOMA.getId(), "width", "4"),
                                    new atributeAssertionString(VARDECLWITHCOMA.getId(), "aloc", newTemp()),
                                    new atributeAssertionString(VARDECLWITHCOMA.getId(), "code", bundle)
                    )));
//                    VARDECLWITHCOMA.width = ‘4’
//                    VARDECLWITHCOMA.aloc = newTemp()
//                    VARDECLWITHCOMA.code = VARDECLWITHCOMA1.code +"\n"+
//                     gen(VARDECLWITHCOMA.aloc ‘=’ VARDECLWITHCOMA.width ‘*’ VARDECLWITHCOMA1.width) +"\n"+
//                    gen(aloc tabSimbolo(ident) VARDECLWITHCOMA.aloc )
//                     da linha de cima pode ser pego ident e vardeclwithcoma.aloc pra informar quantos bytes precisa alocar

                }
                break;

            case "VARDECLWITHCOMA1": {
                SemanticNode VARDECLWITHCOMA1 = root;
                if (root.getChild(0).getName().equalsIgnoreCase("VARDECLBRACKETS") 
                        && root.getChild(1).getName().equalsIgnoreCase("VARDECL2")) {
                    
                    SemanticNode VARDECLBRACKETS = root.getChild(0),
                            VARDECL2 = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECL2.getId(),"code"));
//                    bundle.add(new simpleStringAssertionBundle("\n"));
                    bundle.add(new simpleStringAssertionBundle(VARDECLBRACKETS.getId(),"code"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "sin"),
//                                    new atributeAssertion(root.getChild(1).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "code", bundle),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "width", VARDECLBRACKETS.getId(), "width")
                            )));
//                    VARDECLWITHCOMA1.code = VARDECL2.code +"\n"+ VARDECLBRACKETS.code 
//                    VARDECLWITHCOMA1.width = VARDECLBRACKETS.width

                } else if (root.getChild(0).getName().equalsIgnoreCase("VARDECLWITHCOMA")) {
                    SemanticNode VARDECLWITHCOMA = root.getChild(0);
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "code", VARDECLWITHCOMA.getId(), "code"),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "width", "1")
                            )));
//                    VARDECLWITHCOMA1.code = VARDECLWITHCOMA.code
//                    VARDECLWITHCOMA1.width = ‘1’                    
                } else if (root.getChild(0).getName().equalsIgnoreCase("")) { 
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "code", ""),
                                    new atributeAssertionString(VARDECLWITHCOMA1.getId(), "width", "1")
                                            
                    )));
//                    VARDECLWITHCOMA1.code = ''
//                    VARDECLWITHCOMA1.width = ‘1’     DONE            
                }
                break;
            }
            case "STATEMENT" : {
                SemanticNode STATEMENT = root;
                
                if (root.getChild(0).getName().equalsIgnoreCase("PRINTSTAT") 
                        && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
                    SemanticNode PRINTSTAT = root.getChild(0);
//                  PRINTSTAT.next = STATEMENT.next
//                  STATEMENT.code = PRINTSTAT.code +"\n"+ label(PRINTSTAT.next) +"\n"
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(PRINTSTAT.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(PRINTSTAT.getId(),"next"));
                    
                    PRINTSTAT.stringAttributes.put("next", STATEMENT.getStringAttributes("next"));
                    semanticTable.addNode(PRINTSTAT);
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(PRINTSTAT.getId(), "next", STATEMENT.getId(), "next"),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                    )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("READSTAT") 
                        && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
                    SemanticNode READSTAT = root.getChild(0);
//                    READSTAT.next = STATEMENT.next
//                    STATEMENT.code = READSTAT.code || label(READSTAT.next) 
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(READSTAT.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(READSTAT.getId(),"next"));
                    
                    READSTAT.stringAttributes.put("next", STATEMENT.getStringAttributes("next"));
                    
                    semanticTable.addNode(READSTAT);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(READSTAT.getId(), "next", STATEMENT.getId(), "next"),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                    )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("RETURNSTAT") 
                        && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
                    SemanticNode RETURNSTAT = root.getChild(0);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(STATEMENT.getId(), "code", RETURNSTAT.getId(), "code")
                    )));
//                    STATEMENT.code = RETURNSTAT.code
                } else if (root.getChild(0).getName().equalsIgnoreCase("IFSTAT")) {
                    SemanticNode IFSTAT = root.getChild(0);
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(IFSTAT.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(IFSTAT.getId(),"next"));
                    
                    IFSTAT.stringAttributes.put("next", STATEMENT.getStringAttributes("next"));
                    semanticTable.addNode(IFSTAT);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(IFSTAT.getId(), "next", STATEMENT.getId(), "next"),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                    )));
//                    IFSTAT.next = STATEMENT.next
//                    STATEMENT.code = IFSTAT.code || label(IFSTAT.next)

                } else if (root.getChild(0).getName().equalsIgnoreCase("FORSTAT")) {
//                    FORSTAT.next = FORSTAT.next
//                    STATEMENT.code = FORSTAT.code || label(FORSTAT.next) DONE
                    SemanticNode FORSTAT = root.getChild(0);
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(),"code"));
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(),"next"));
                    
                    FORSTAT.stringAttributes.put("next", STATEMENT.getStringAttributes("next"));
                    semanticTable.addNode(FORSTAT);
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(FORSTAT.getId(), "next", FORSTAT.getId(), "next"),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                    )));
                } else if (root.getChild(0).getName().equalsIgnoreCase("obrace") 
                        && root.getChild(1).getName().equalsIgnoreCase("STATLIST") 
                        && root.getChild(2).getName().equalsIgnoreCase("cbrace")) {
                    SemanticNode STATLIST = root.getChild(1);
                    
                    STATLIST.stringAttributes.put("next", STATEMENT.getStringAttributes("next"));
                    semanticTable.addNode(STATLIST);
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(STATEMENT.getId(), "code", STATLIST.getId(), "code")
                            )));
//                    STATEMENT.code = STATLIST.code DONE
                } else if (root.getChild(0).getName().equalsIgnoreCase("break") 
                        && root.getChild(1).getName().equalsIgnoreCase("semicomma")) {
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new generatorStringeAssertionBundle("goto ", STATEMENT.getId(), "break"));
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(STATEMENT.getId(), "addr", bundle)
                            )));
//                    STATEMENT.code = gen(‘goto’ STATEMENT.break) 
                } else if (root.getChild(0).getName().equalsIgnoreCase("semicomma")) {
                     semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    //                    STATEMENT1.addr = getName(STATEMENT1.her)
                                    new atributeAssertionString(STATEMENT.getId(), "code", "")
                            )));
//                    STATEMENT.code = ‘’         
                } else if (root.getChild(0).getName().equalsIgnoreCase("int") 
                        && root.getChild(1).getName().equalsIgnoreCase("ident") 
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECL1") 
                        && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {
//                   
                    SemanticNode VARDECL1 = root.getChild(2);
                    
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECL1.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(STATEMENT.getId(), 
                            "aloc", "=", STATEMENT.getId(), "width", " * ", VARDECL1.getId(), "width"));
                    bundle.add(new generatorStringeAssertionBundle("aloc ".concat(tableValue), STATEMENT.getId(), "aloc"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    //                    STATEMENT1.addr = getName(STATEMENT1.her)
//                                    new newLeaf(VARDECL1.getId(), "her", "int"),
//                                    addType
                                    new addType(semanticTable.getNode(root.getChild(1).getId()).getTableId(), VARDECL1.getId(), "sin"),
                                    new atributeAssertionString(STATEMENT.getId(), "width", "4"),
                                    new atributeAssertionString(STATEMENT.getId(), "aloc", newTemp()),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                            )));
                    
//                     VARDECL1.her =  new leaf(id,"int")
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT.width = ‘4’
//                    STATEMENT.aloc = newTemp()
//                    STATEMENT.code = VARDECL1.code 
//                    || gen(STATEMENT.aloc ‘=’ STATEMENT.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT.aloc ) 
                } else if (root.getChild(0).getName().equalsIgnoreCase("string") 
                        && root.getChild(1).getName().equalsIgnoreCase("ident") 
                        && root.getChild(2).getName().equalsIgnoreCase("VARDECL1") 
                        && root.getChild(3).getName().equalsIgnoreCase("semicomma")) {

                    SemanticNode VARDECL1 = root.getChild(2);
                    
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(1).getId()).getTableId());
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECL1.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(STATEMENT.getId(), 

                            "aloc", "=", STATEMENT.getId(), "width", " * ", VARDECL1.getId(), "width"));

                    bundle.add(new generatorStringeAssertionBundle("aloc ".concat(tableValue), STATEMENT.getId(), "aloc"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new newLeaf(VARDECL1.getId(), "her", "string"),
//                                    addType
                                    new addType(semanticTable.getNode(root.getChild(1).getId()).getTableId(), VARDECL1.getId(), "sin"),
                                    new atributeAssertionString(VARDECL1.getId(), "addr", VARDECL1.getId(), "her"),
                                    new atributeAssertionString(STATEMENT.getId(), "width", "4"),
                                    new atributeAssertionString(STATEMENT.getId(), "aloc", newTemp()),
                                    new atributeAssertionString(STATEMENT.getId(), "code", bundle)
                            )));
                    //                    VARDECL1.her = new leaf(id,"string") 
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT.width = ‘4’
//                    STATEMENT.aloc = newTemp()
//                    STATEMENT.code = VARDECL1.code 
//                    || gen(STATEMENT.aloc ‘=’ STATEMENT.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT.aloc )
                } else if (root.getChild(0).getName().equalsIgnoreCase("ident") 
                        && root.getChild(1).getName().equalsIgnoreCase("STATEMENT1")) {
                    SemanticNode STATEMENT1 = root.getChild(1);
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new newLeaf(STATEMENT.getId(), "her", tableValue),
                                    new atributeAssertionString(STATEMENT.getId(), "code", STATEMENT1.getId(), "code")
                            )));
//                    STATEMENT1.her = new leaf(id,tabSimbolo(ident))
//                    STATEMENT.code = STATEMENT1.code
                    

                }
                break;
            }
            case "STATEMENT1":
                if (root.getChild(0).getName().equalsIgnoreCase("ident") && 
                        root.getChild(1).getName().equalsIgnoreCase("VARDECL1") && 
                        root.getChild(2).getName().equalsIgnoreCase("semicomma")) {
                    
                    SemanticNode STATEMENT1 = root, 
                            VARDECL1 = root.getChild(1);
                    
                    String tableValue = symbolsTable
                            .getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(VARDECL1.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(STATEMENT1.getId(), 
                            "addr", "=", STATEMENT1.getId(), "width", " * ", VARDECL1.getId(), "width"));
                    bundle.add(new generatorStringeAssertionBundle("aloc ".concat(tableValue), STATEMENT1.getId(), "aloc"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    //                    STATEMENT1.addr = getName(STATEMENT1.her)
                                    new atributeAssertionString(VARDECL1.getId(), "addr", VARDECL1.getId(), "her"),
//                                    addType
                                    new addType(semanticTable.getNode(root.getChild(0).getId()).getTableId(), VARDECL1.getId(), "sin"),
                                    new atributeAssertionString(STATEMENT1.getId(), "width", "4"),
                                    new atributeAssertionString(STATEMENT1.getId(), "aloc", newTemp()),
                                    new atributeAssertionString(STATEMENT1.getId(), "code", bundle)
                            )));
//                    VARDECL1.her = STATEMENT1.her
//                    addType(ident,VARDECL1.sin)
//                    STATEMENT1.width = ‘4’
//                    STATEMENT1.aloc = newTemp()
//                    STATEMENT1.code = VARDECL1.code 
//                    || gen(STATEMENT1.aloc ‘=’ STATEMENT1.width ‘*’ VARDECL1.width)
//                    || gen(aloc tabSimbolo(ident) STATEMENT1.aloc ) DONE

                } else if (root.getChild(0).getName().equalsIgnoreCase("at") && 
                        root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION") 
                        && root.getChild(2).getName().equalsIgnoreCase("semicomma")) {
                    
                    SemanticNode STATEMENT1 = root, 
                            NUMEXPRESSION = root.getChild(1);
                      
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(STATEMENT1.getId(), "addr", "=", NUMEXPRESSION.getId(), "addr"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    //                    STATEMENT1.addr = getName(STATEMENT1.her)
                                    new atributeAssertionString(STATEMENT1.getId(), "addr", STATEMENT1.getId(), "her"),
                                    new atributeAssertionString(STATEMENT1.getId(), "code", bundle)
                            )));
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
//                    READSTAT.code = LVALUE.code || gen(‘in’ LVALUE.addr)  DONE
                    SemanticNode READSTAT = root, LVALUE = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new simpleStringAssertionBundle(LVALUE.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("in", LVALUE.getId(), "addr"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(READSTAT.getId(), "code", bundle)
                            )));
                }
                break;

            case "RETURNSTAT":
                if (root.getChild(0).getName().equalsIgnoreCase("return") 
                        && root.getChild(1).getName().equalsIgnoreCase("RETURNSTAT1")) {
                    SemanticNode RETURNSTAT = root, RETURNSTAT1 = root.getChild(1),
                            currentSTATMENT = semanticTable.getNode(statmentContext.get(0));
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    
                    bundle.add(new simpleStringAssertionBundle(RETURNSTAT1.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto ", currentSTATMENT.getId(), "return"));
                    
//                     gen(‘goto’ STATEMENT.return) DONE?
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(RETURNSTAT.getId(), "code", bundle)
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
                    bundle.add(new generatorStringeAssertionBundle("if", NUMEXPRESSION.getId(), "addr", " != 0 ", "goto ", NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "false"));
                    bundle.add(new simpleStringAssertionBundle(IFSTAT1.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto ", STATEMENT.getId(), "next"));
                    
                    NUMEXPRESSION.stringAttributes.put("true", newLabel());
                    NUMEXPRESSION.stringAttributes.put("false", newLabel());
                    STATEMENT.stringAttributes.put("next", IFSTAT.getStringAttributes("next"));
                    IFSTAT1.stringAttributes.put("next", IFSTAT.getStringAttributes("next"));
                    
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(NUMEXPRESSION.getId(), "true", newLabel()),
//                                    new atributeAssertionString(NUMEXPRESSION.getId(), "false", newLabel()),
//                                    new atributeAssertionString(STATEMENT.getId(), "next", IFSTAT.getId(), "next"),
//                                    new atributeAssertionString(IFSTAT1.getId(), "next", IFSTAT.getId(), "next"),
                                    new atributeAssertionString(IFSTAT.getId(), "code", bundle)
                            )));

                }
                break;

            case "IFSTAT1":
                if (root.getChild(0).getName().equalsIgnoreCase("else") 
                        && root.getChild(1).getName().equalsIgnoreCase("STATEMENT") 
                        && root.getChild(2).getName().equalsIgnoreCase("end")) {
//                    STATEMENT.next = IFSTAT1.next
//                    IFSTAT1.code = STATEMENT.code || gen(‘goto’ STATEMENT.next) DONE
                    SemanticNode IFSTAT1 = root, STATEMENT = root.getChild(1);
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    STATEMENT.stringAttributes.put("next", IFSTAT1.getStringAttributes("next"));
                    semanticTable.addNode(STATEMENT);
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto ", STATEMENT.getId(), "next"));
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(STATEMENT.getId(), "next", IFSTAT1.getId(), "next"),
                                    new atributeAssertionString(IFSTAT1.getId(), "code", bundle)
                            )));

                } else if (root.getChild(0).getName().equalsIgnoreCase("end")) {
                    SemanticNode IFSTAT1 = root;
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new generatorStringeAssertionBundle("goto ", IFSTAT1.getId(), "next"));
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
                            NUMEXPRESSION = root.getChild(4),
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
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(), "begin"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("if", NUMEXPRESSION.getId(), "addr", " != 0 ", "goto ", NUMEXPRESSION.getId(), "true"));
                    bundle.add(new generatorStringeAssertionBundle("goto ", NUMEXPRESSION.getId(), "false"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "true"));
                    bundle.add(new simpleStringAssertionBundle(STATEMENT.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(FORSTAT.getId(), "end"));
                    bundle.add(new simpleStringAssertionBundle(ATRIBSTAT_.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle("goto ", FORSTAT.getId(), "begin"));

                    NUMEXPRESSION.stringAttributes.put("true", newLabel());
                    NUMEXPRESSION.stringAttributes.put("false", FORSTAT.getStringAttributes("next"));
                    FORSTAT.stringAttributes.put("begin", newLabel());
                    FORSTAT.stringAttributes.put("end", newLabel());
                    STATEMENT.stringAttributes.put("next", FORSTAT.getStringAttributes("end"));
                    STATEMENT.stringAttributes.put("break", FORSTAT.getStringAttributes("next"));
                    
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(NUMEXPRESSION.getId(), "true", newLabel()),
//                                    new atributeAssertionString(NUMEXPRESSION.getId(), "false", FORSTAT.getId(), "next"),
//                                    new atributeAssertionString(FORSTAT.getId(), "begin", newLabel()),
//                                    new atributeAssertionString(STATEMENT.getId(), "next", FORSTAT.getId(), "end"),
//                                    new atributeAssertionString(STATEMENT.getId(), "break", FORSTAT.getId(), "next"),
                                    new atributeAssertionString(FORSTAT.getId(), "temp", newTemp()),
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
                    STATEMENT.stringAttributes.put("next", newLabel());
                    
                    //STATEMENT.stringAttributes.put("next", STATLIST.getStringAttributes("next"));
                    semanticTable.addNode(STATEMENT);
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
//                                    new newLeaf((root.getChild(1).getId()), "her", tabsimbolIdent),
                                    new atributeAssertionString(root.getId(), "node", root.getChild(1).getId(), "sin"),
//                                    new atributeAssertionString(root.getId(), "addher", tabsimbolIdent),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(1).getId(), "code"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addrher")
                            )));
                }
                break;
            case "LVALUET2":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
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
//                                    new newNode(root.getChild(3).getId(), "her", "array", tabsimbolIdent, root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(3).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "local", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle),
                                    new atributeAssertionString(root.getId(), "addher", root.getId(), "addr"))));

                }
                break;

            case "NUMEXPRESSION":
                if (root.getChild(0).getName().equalsIgnoreCase("TERM") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION1")) {
                    SemanticNode NUMEXPRESSION = semanticTable.getNode(root.getId());
                    SemanticNode TERM = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode NUMEXPRESSION1 = semanticTable.getNode(root.getChild(1).getId());
                    semanticTable.addRule(TERM.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(NUMEXPRESSION1.getId(), "addrher", TERM.getId(), "addr"),
                                    new atributeAssertionString(NUMEXPRESSION1.getId(), "her", TERM.getId(), "sin"))));
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new simpleStringAssertionBundle(TERM.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION1.getId(), "code"));

                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(1).getId(), "node"),
                                    new atributeAssertionString(NUMEXPRESSION.getId(), "code", bundle),
                                    //                                    || NUMEXPRESSION.CODE
//                                    new atributeAssertionString(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(1).getId(), "addr")
                            )));
                }
                break;

            case "NUMEXPRESSION1":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "addr", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION")) {
                    SemanticNode NUMEXPRESSION1 = semanticTable.getNode(root.getId());
                    SemanticNode SUMMINUS = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode NUMEXPRESSION = semanticTable.getNode(root.getChild(1).getId());
                    
                    String node = SUMMINUS.getStringAttributes("node")+ " " + NUMEXPRESSION1.getStringAttributes("her") + " " + NUMEXPRESSION.getStringAttributes("node");
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new simpleStringAssertionBundle(NUMEXPRESSION.getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(NUMEXPRESSION1.getId(),
                            "addr", " = ", NUMEXPRESSION1.getId(), "addrher", SUMMINUS.getId(), "addr", NUMEXPRESSION.getId(), "addr"));
                    
                    
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", node),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(NUMEXPRESSION1.getId(), "code", bundle)

                            )));
                }
                break;

            case "SUMMINUS":
                if (root.getChild(0).getName().equalsIgnoreCase("plus")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", "+"),
                                    new atributeAssertionString(root.getId(), "addr", "+"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("minus")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", "-"),
                                    new atributeAssertionString(root.getId(), "addr", "-"))));
                }
                break;

            case "TERM":
                if (root.getChild(0).getName().equalsIgnoreCase("UNARYEXPR") && root.getChild(1).getName().equalsIgnoreCase("TERM3")) {
                    SemanticNode TERM = semanticTable.getNode(root.getId());
                    SemanticNode UNARYEXPR = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode TERM3 = semanticTable.getNode(root.getChild(1).getId());
                    
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();
                    bundle.add(new simpleStringAssertionBundle(UNARYEXPR.getId(), "code"));
                    bundle.add(new simpleStringAssertionBundle(TERM3.getId(), "code"));
                    
                    semanticTable.addRule(root.getChild(0).getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(TERM3.getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(TERM3.getId(), "her", UNARYEXPR.getId(), "sin"))));
                    
                    semanticTable.addRule(true,root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertionString(root.getChild(1).getId(), "her", root.getChild(0).getId(), "sin"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(1).getId(), "sin"),
//                                    new atributeAssertionString(root.getChild(1).getId(), "addrher", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(TERM.getId(), "code", bundle),
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
                    
                    SemanticNode TERM2 = semanticTable.getNode(root.getId());
                    SemanticNode MULDIVMOD = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode UNARYEXPR = semanticTable.getNode(root.getChild(1).getId());
                    SemanticNode TERM3 = semanticTable.getNode(root.getChild(2).getId());
                    
                    String node = MULDIVMOD.getStringAttributes("node")+ " " + TERM2.getStringAttributes("her") + " " + TERM3.getStringAttributes("sin");

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", node),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle))));
//                                    new atributeAssertionString(root.getChild(2).getId(), "addrher", root.getId(), "addr"))));
                }
                break;

            case "TERM3":
                if (root.getChild(0).getName().equalsIgnoreCase("")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "addr", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("TERM2")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
//                                    new atributeAssertion(root.getChild(0).getId(), "her", root.getId(), "her"),
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getChild(0).getId(), "addrher", root.getId(), "addrher"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
                }
                break;

            case "MULDIVMOD":
                if (root.getChild(0).getName().equalsIgnoreCase("mod")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", "%"),
                                    new atributeAssertionString(root.getId(), "addr", "%"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("div")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", "/"),
                                    new atributeAssertionString(root.getId(), "addr", "/"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("mul")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", " * "),
                                    new atributeAssertionString(root.getId(), "addr", " * "))));

                }
                break;
            case "UNARYEXPR":
                if (root.getChild(0).getName().equalsIgnoreCase("FACTOR")) {
                    semanticTable.addRule(true,root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "sin", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("SUMMINUS") && root.getChild(1).getName().equalsIgnoreCase("FACTOR")) {
                    ArrayList<StringAssertionBundle> bundle = new ArrayList<>();

                    bundle.add(new simpleStringAssertionBundle(root.getChild(1).getId(), "code"));
                    bundle.add(new generatorStringeAssertionBundle(root.getId(),
                            "addr", "=", root.getChild(0).getId(), "addr",
                            root.getChild(1).getId(), "addr"));
                    
                    SemanticNode UNARYEXPR = semanticTable.getNode(root.getId());
                    SemanticNode SUMMINUS = semanticTable.getNode(root.getChild(0).getId());
                    SemanticNode FACTOR = semanticTable.getNode(root.getChild(1).getId());
                    
                    String node = SUMMINUS.getStringAttributes("node")+ " " + FACTOR.getStringAttributes("node");

                    
                    semanticTable.addRule(true,root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", node),
                                    new atributeAssertionString(root.getId(), "addr", newTemp()),
                                    new atributeAssertionString(root.getId(), "code", bundle))));
                }
                break;
            case "FACTOR":
                if (root.getChild(0).getName().equalsIgnoreCase("null")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("stringconst")) {
                    SemanticNode node = semanticTable.getNode(root.getChild(0).getId());
                    String tableValue = symbolsTable.getSymbol(node.getTableId());
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("intconst")) {
                    String tableValue = symbolsTable.getSymbol(semanticTable.getNode(root.getChild(0).getId()).getTableId());

                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", tableValue),
                                    new atributeAssertionString(root.getId(), "addr", tableValue),
                                    new atributeAssertionString(root.getId(), "code", ""))));

                } else if (root.getChild(0).getName().equalsIgnoreCase("LVALUE")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", root.getChild(0).getId(), "node"),
                                    new atributeAssertionString(root.getId(), "addr", root.getChild(0).getId(), "addr"),
                                    new atributeAssertionString(root.getId(), "code", root.getChild(0).getId(), "code"))));
                } else if (root.getChild(0).getName().equalsIgnoreCase("OPAR") && root.getChild(1).getName().equalsIgnoreCase("NUMEXPRESSION") && root.getChild(2).getName().equalsIgnoreCase("CPAR")) {
                    semanticTable.addRule(root.getId(),
                            new ArrayList<>(Arrays.asList(
                                    new atributeAssertionString(root.getId(), "node", root.getChild(1).getId(), "sin"),
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
        return "L" + labelIdCounter + ":";
    }

}
