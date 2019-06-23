/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;

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
        symbolTable.addType(symbolTableId,semanticTable.getNode(semanticTable.getNode(nodeId)
                .getAttributeValue(attribute)).getNodeValue());
        System.out.println("Adicionando tipo: " + semanticTable.getNode(nodeId).getName()+ "." + attribute);
        return 0;
    }
}
