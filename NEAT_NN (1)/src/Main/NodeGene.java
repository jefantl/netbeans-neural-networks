/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.ArrayList;

public class NodeGene {

    public enum TYPE {
        INPUT,
        OUTPUT,
        HIDDEN;
    }
    int ID;
    TYPE type;
    public double value;
    public boolean visted = false;
//    ArrayList<ConnectionGene> inConnections;
//     ArrayList<ConnectionGene> outConnections;

    public NodeGene(int inID, TYPE inType) {
        value = 0;
        ID = inID;
        type = inType;
//        inConnections = new ArrayList<>();
//        outConnections = new ArrayList<>();
    }
    
    public NodeGene copy() {
        return new NodeGene(ID, type);
    }

}
