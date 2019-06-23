/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nathangodinho
 */
public class AllocationTable {
    public HashMap<Integer, Integer> table;
    private static final AllocationTable instance = new AllocationTable();
    private static String statusMessage = "";
    
    private AllocationTable() {
        table = new HashMap();
    }
    
    public String getStatusMessage() {
        return statusMessage + "\n";
    }
    
    public void cleanStatusMessage() {
        statusMessage = "";
    }
    
    public static AllocationTable getInstance() {
        return instance;
    }
    
    public int bytesByType(String type) {
        switch (type) {
            case "int":
                return 8;
            case "string":
                return 4;
            default:
                return 8;
                 
        }
    }
    private void AllocAction(Integer nodeId, String type, Integer multiplyer) {
        Integer bytes = bytesByType(type) * multiplyer;
        table.put(nodeId, bytes);
        String printType = multiplyer > 1 ? "array(".concat(type).concat(")") : type;
        String msg = "Allocating ".concat(bytes.toString()).concat(" bytes").concat(" for ")
                .concat(printType) + "\n";
        System.out.println(msg);
        statusMessage += msg;
    }
    
    public void addAllocAction(Integer nodeId, String type, Integer multiplyer) {
        AllocAction(nodeId, type, multiplyer);
    }
    
    public void addAllocAction(Integer nodeId, String type) {
        AllocAction(nodeId, type, 1);
    }
    
    public void addAllocAction(Integer nodeId, Integer bytes) {
        table.put(nodeId, bytes);
        System.out.println("Allocing ".concat(bytes.toString()).concat(" bytes"));
    }
}
