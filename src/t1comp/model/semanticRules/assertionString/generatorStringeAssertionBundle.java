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
public class generatorStringeAssertionBundle implements StringAssertionBundle {
        private final SemanticTable table = SemanticTable.getInstance();
        Integer receiver, sender1, sender2, sender3;
        String receiveAtt, operator, sender1Att, sender2Att, sender3Att;
      
        public generatorStringeAssertionBundle(Integer receiver, String receiveAtt, String operator, 
                Integer sender1, String sender1Att, Integer sender2, String sender2Att) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.operator = operator;
                this.sender1 = sender1;
                this.sender1Att = sender1Att;
                this.sender2 = sender2;
                this.sender2Att = sender2Att;
        }
        
        public generatorStringeAssertionBundle(Integer receiver, String receiveAtt, String operator, 
                Integer sender1, String sender1Att, Integer sender2, String sender2Att, 
                Integer sender3, String sender3Att) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.operator = operator;
                this.sender1 = sender1;
                this.sender1Att = sender1Att;
                this.sender2 = sender2;
                this.sender2Att = sender2Att;
                this.sender3 = sender2;
                this.sender3Att = sender2Att;
            
        }
        
        public String getValue() {
            String returnValue = "";
            
            returnValue = table.getNode(receiver).stringAttributes.get(receiveAtt)
                        .concat(" " + operator + " ")
                        .concat(table.getNode(sender1).stringAttributes.get(sender1Att))
                        .concat(table.getNode(sender2).stringAttributes.get(sender2Att));
            if (sender3 != null && sender3Att != null) {
                returnValue = returnValue
                            .concat(table.getNode(sender3).stringAttributes.get(sender3Att));
            } 
            
            return returnValue;
        }
}
