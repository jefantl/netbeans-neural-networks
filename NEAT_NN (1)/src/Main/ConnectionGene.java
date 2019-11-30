/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author jason
 */
public class ConnectionGene {
    
    
    NodeGene inNode, outNode;
    double weight;
    boolean enabled;
    int innovation;
    
    public ConnectionGene(NodeGene inNode_, NodeGene outNode_, double weight_, boolean enabled_, int innovation_) {
        inNode = inNode_;
        outNode = outNode_;
        weight = weight_;
        enabled = enabled_;
        innovation = innovation_;
    }
    
    
    public ConnectionGene copy() {
        return new ConnectionGene(inNode, outNode, weight, enabled, innovation);
    }
}
