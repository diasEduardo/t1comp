/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules;
import t1comp.model.SemanticNode;
import t1comp.model.SemanticRule;
/**
 *
 * @author nathangodinho
 */
public class atributeAssertion implements SemanticRule<Integer>{
    private String left;
    private String right;
    
    public atributeAssertion(String left, String rigth) {
        this.left = left;
        this.right = rigth;
    }
    
    @Override
    public Integer action() {
//        SemanticNode n
        return 0;
    }
    
}
