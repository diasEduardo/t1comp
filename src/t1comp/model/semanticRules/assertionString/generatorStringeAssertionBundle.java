/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model.semanticRules.assertionString;

import t1comp.model.SemanticNode;
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
                this.sender1Att = sender1Att;
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
                this.sender3 = sender3;
                this.sender3Att = sender3Att;
            
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
            
            SemanticNode node = table.getNode(receiver);
            
            if (node.getName().equalsIgnoreCase("NUMEXPRESSION1")) {
                System.out.print("");
            } 
            returnValue = node.getStringAttributes(receiveAtt)
                        .concat(" " + operator + " ");

            if (command != null) {
                if (command2 != null) {
                    return command.concat(" " + returnValue + command2)
                            .concat(table.getNode(sender1).stringAttributes.get(sender1Att));
                }
                String value = table.getNode(receiver).getStringAttributes(receiveAtt);
                return command.concat(" " + value);
                
            }
            if (simpleStringGen != null) {
                return returnValue.concat(simpleStringGen);
            }
            SemanticNode node2 = table.getNode(sender1);
            returnValue = returnValue.concat(node2.getStringAttributes(sender1Att));
            
            if (operator2 != null) {
                SemanticNode node3 = table.getNode(sender2);
                returnValue = returnValue.concat(operator2).concat(node3.getStringAttributes(sender2Att));
            } else if (sender3 != null && sender3Att != null) {
                SemanticNode node3 = table.getNode(sender2);
                returnValue = returnValue
                            .concat(node3.getStringAttributes(sender2Att))
                            .concat(table.getNode(sender3).getStringAttributes(sender3Att));
            } 
            
            return returnValue;
        }
}
