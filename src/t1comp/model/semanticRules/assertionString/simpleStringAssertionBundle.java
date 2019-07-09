/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules.assertionString;

import t1comp.model.SemanticTable;

/**
 *
 * @author nathangodinho
 */
public class simpleStringAssertionBundle implements StringAssertionBundle{
        public int rootid;
        public String att, singleString;
        private final SemanticTable table = SemanticTable.getInstance();
        
        
        public simpleStringAssertionBundle(Integer rootid, String att) {
            this.rootid = rootid;
            this.att = att;
        }
        
        public simpleStringAssertionBundle(String singleString) {
            this.singleString = singleString;
        }
        
        public String getValue() {
            if (singleString != null) {
                return singleString;
            }
            
            return table.getNode(rootid).stringAttributes.get(att);
        }
}
