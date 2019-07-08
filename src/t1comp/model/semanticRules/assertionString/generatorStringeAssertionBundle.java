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
        String receiveAtt, operator, sender1Att, sender2Att, sender3Att, 
                operator2, simpleStringGen, command, command2;
        boolean singleAtt = false;
      
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
        
        public generatorStringeAssertionBundle(String command, Integer receiver, String receiveAtt) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.command = command;
                
        }
        
        public generatorStringeAssertionBundle(String command, Integer receiver, String receiveAtt, 
                String operator, String command2, Integer sender1, String sender1Att) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.command = command;
                this.operator = operator;
                this.command2 = command2;
                this.sender1 = sender1;
                this.sender1Att = sender1Att;
                
        }
        
        public generatorStringeAssertionBundle(Integer receiver, String receiveAtt, String operator, 
                Integer sender1, String sender1Att) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.operator = operator;
                this.sender1 = sender1;
                this.singleAtt = true;
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
        
        public generatorStringeAssertionBundle(Integer receiver, String receiveAtt, String operator, 
                Integer sender1, String sender1Att, String operator2, Integer sender2, String sender2Att) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.operator = operator;
                this.sender1 = sender1;
                this.sender1Att = sender1Att;
                this.sender2 = sender2;
                this.sender2Att = sender2Att;
                this.operator2 = operator2;
        }
        
         public generatorStringeAssertionBundle(Integer receiver, String receiveAtt, String operator, 
                String simpleStringGen) {
                this.receiver = receiver;
                this.receiveAtt = receiveAtt;
                this.operator = operator;
                this.simpleStringGen = simpleStringGen;
        }
        
        
        public String getValue() {
            String returnValue = "";
            
            returnValue = table.getNode(receiver).stringAttributes.get(receiveAtt)
                        .concat(" " + operator + " ");

            if (command != null) {
                if (command2 != null) {
                    return command.concat(" " + returnValue + command2)
                            .concat(table.getNode(sender1).stringAttributes.get(sender1Att));
                }
                
                return command.concat(" " + table.getNode(receiver).stringAttributes.get(receiveAtt));
                
            }
            if (simpleStringGen != null) {
                return returnValue.concat(simpleStringGen);
            }
            
            returnValue = returnValue.concat(table.getNode(sender1).stringAttributes.get(sender1Att));
            
            if (operator2 != null) {
                returnValue = returnValue.concat(table.getNode(sender2).stringAttributes.get(sender2Att));
            } else if (sender3 != null && sender3Att != null) {
                returnValue = returnValue
                            .concat(table.getNode(sender3).stringAttributes.get(sender3Att));
            } 
            
            return returnValue;
        }
}
