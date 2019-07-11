/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;

import t1comp.model.AllocationTable;
import t1comp.model.SemanticRule;
import t1comp.model.SemanticTable;
import t1comp.model.SymbolsTable;

/**
 *
 * @author nathangodinho
 */
public class addType implements SemanticRule<Integer>{
    
    private Integer symbolTableId, nodeId;
    private String attribute;
    private SymbolsTable symbolTable = SymbolsTable.getInstance();
    private SemanticTable semanticTable = SemanticTable.getInstance();
         
    
    public addType(Integer symbolTableId, Integer nodeId, String attribute) {
        this.symbolTableId = symbolTableId;
        this.nodeId = nodeId;
        this.attribute = attribute;
    }

//    Criei o attributo node value para portar os valores sineteziados dos atributos
    @Override
    public Integer action() {
        String type = semanticTable.getNode(nodeId).getStringAttributes(attribute);
        symbolTable.addType(symbolTableId, type);
        System.out.println("Adicionando tipo: " + semanticTable.getNode(nodeId).getName()+ "." + attribute + "= " + semanticTable.getNode(nodeId).getStringAttributes(attribute));
        return 0;
    }
}
