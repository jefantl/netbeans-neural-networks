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
public class GenomeUtl {

    public static Genome crossOver(Genome parent1, Genome parent2) {
        Genome child = new Genome(0, 0, parent1.innovationCounter);
        for (NodeGene node : parent1.nodes.values()) {
            if (parent2.nodes.containsKey(node.ID)) { //matching genes
                NodeGene newNode = node.copy(); //pick random connection
            child.nodes.put(newNode.ID, newNode);
            }
        }
        for (ConnectionGene conn : parent1.connections.values()) { //checking parent 1s connections
            ConnectionGene newConnection = null;
            if (parent2.connections.containsKey(conn.innovation)) { //matching genes
                newConnection = (Math.random() > 0.5) ? conn.copy() : parent2.connections.get(conn.innovation).copy(); //pick random connection
            } else {//non-matching genes
                if (parent1.fitness > parent2.fitness) {//pick higher fitness connection 
                    newConnection = conn.copy();
                } else { //equal fitness
                    newConnection = (Math.random() > 0.5) ? conn.copy() : null; //pick random connection
                }
            }
            if (newConnection != null) {
                child.connections.putIfAbsent(newConnection.innovation, newConnection);
                child.nodes.putIfAbsent(newConnection.inNode.ID, newConnection.inNode);
                child.nodes.putIfAbsent(newConnection.outNode.ID, newConnection.outNode);
            }
        }

        for (ConnectionGene conn : parent2.connections.values()) {
            ConnectionGene newConnection = null;
            if (!parent1.connections.containsKey(conn.innovation)) { //new gene
                if (parent2.fitness > parent1.fitness) {//pick higher fitness connection 
                    newConnection = conn.copy();
                } else { //equal fitness
                    newConnection = (Math.random() > 0.5) ? conn.copy() : null; //pick random connection
                }
            }
            if (newConnection != null) {
                child.connections.putIfAbsent(newConnection.innovation, newConnection);
                child.nodes.putIfAbsent(newConnection.inNode.ID, newConnection.inNode);
                child.nodes.putIfAbsent(newConnection.outNode.ID, newConnection.outNode);
            }
        }
        return child;
    }

    public static void print(Genome inG) {
        System.out.println("Fitness: " + inG.fitness);
        System.out.println("Node genes:");

        for (NodeGene node : inG.nodes.values()) {
            System.out.println(" | " + node.ID + " " + node.type + " | ");
        }
        System.out.println("Connection genes:");
        for (ConnectionGene conn : inG.connections.values()) {
            System.out.println(" | " + conn.inNode.ID + " -> " + conn.outNode.ID + " Innov: " + conn.innovation + " Weight: " + conn.weight + " Enabled: " + conn.enabled + " | ");
        }
        System.out.println();
    }

    public static double geneticDistance(Genome parent1, Genome parent2) {
        double finalValue = 0;

        /*              c1 * Ne    c2 * Nd 
                   D = -------- +  -------- +  c3 * W
                           N          N 
            Ne, Nd - The number of excess and disjoint genes, respectively.
            N - The number of genes in the largest genome.
            W - The sum of absolute weight differences.
            c1 - importance of exsess genes
            c2 - importance of disjointed genes
            c3 - importance of weight differences
        
         */
        double c1 = 1;
        double c2 = 1;
        double c3 = 0.5;

        int N = (parent1.connections.size() > parent2.connections.size()) ? parent1.connections.size() : parent2.connections.size();

        int Nall = 0;
        for (ConnectionGene conn : parent1.connections.values()) { //checking parent 1s connections
            if (!parent2.connections.containsKey(conn.innovation)) {
                Nall++;
            }
        }
        for (ConnectionGene conn : parent2.connections.values()) { //checking parent 1s connections
            if (!parent1.connections.containsKey(conn.innovation)) {
                Nall++;
            }
        }

        double W = 0;
        int sameWeights = 0;
        for (ConnectionGene conn : parent1.connections.values()) { //checking parent 1s connections
            if (parent2.connections.containsKey(conn.innovation)) {
                W += Math.abs(conn.weight - parent2.connections.get(conn.innovation).weight);
                sameWeights++;
            }
        }
        W /= sameWeights;

        finalValue = (c1 * Nall) / N + c3 * W;
        return finalValue;
    }
}
