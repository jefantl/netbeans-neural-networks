package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genome {

    public Map<Integer, NodeGene> nodes;
    public Map<Integer, ConnectionGene> connections;
    public Counter innovationCounter;
    public double fitness;

    public Genome(int inputs, int outputs, Counter inCounter) {
        fitness = 0;
        innovationCounter = inCounter;
        nodes = new HashMap<>(); //ID to node
        connections = new HashMap<>(); //innovation to connection
        for (int i = 1; i <= inputs; i++) {
            NodeGene newNodeGene = new NodeGene(i, NodeGene.TYPE.INPUT);
            nodes.put(newNodeGene.ID, newNodeGene);
        }
        for (int i = 1; i <= outputs; i++) {
            NodeGene newNodeGene = new NodeGene(i + inputs, NodeGene.TYPE.OUTPUT);
            nodes.put(newNodeGene.ID, newNodeGene);
        }
    }

    public void mutateAddConnection() {
        int maxTrys = 20;
        List<NodeGene> keysAsArray = new ArrayList<>(nodes.values());
        NodeGene node1 = keysAsArray.get((int) (Math.random() * keysAsArray.size()));
        NodeGene node2 = keysAsArray.get((int) (Math.random() * keysAsArray.size()));
        int trys = 0;
        while (addConnection(node1, node2, Math.random()) && trys < maxTrys) {
            node1 = keysAsArray.get((int) (Math.random() * keysAsArray.size()));
            node2 = keysAsArray.get((int) (Math.random() * keysAsArray.size()));
            trys++;
        }
    }

    public boolean addConnection(NodeGene node1, NodeGene node2, double weight) {
        if (node1.ID == node2.ID || node2.type == NodeGene.TYPE.INPUT || node1.type == NodeGene.TYPE.OUTPUT) { //not allowed to make connection
            return true;
        }
        for (ConnectionGene possibleConnection : connections.values()) { //connection exists
            if (possibleConnection.inNode == node1 && possibleConnection.outNode == node2) {
                return true;
            }
        }

//need to add recurrent checker
        ConnectionGene newConnection = new ConnectionGene(node1, node2, weight, true, innovationCounter.getNext());
        connections.put(newConnection.innovation, newConnection);
        return false;
    }

    public void manualAddConnection(NodeGene node1, NodeGene node2, double weight, int inno) {
        if (node1.ID == node2.ID || node2.type == NodeGene.TYPE.INPUT) { //not allowed to make connection
            return;
        }
        for (ConnectionGene possibleConnection : connections.values()) { //connection exists
            if (possibleConnection.inNode == node1 && possibleConnection.outNode == node2) {
                return;
            }
        }
        ConnectionGene newConnection = new ConnectionGene(node1, node2, weight, true, inno);
        connections.put(newConnection.innovation, newConnection);
    }

    public void mutateAddNode() {
        if (connections.size() > 0) {
            List<ConnectionGene> keysAsArray = new ArrayList<>(connections.values());
            ConnectionGene connectionToMutate = keysAsArray.get((int) (Math.random() * keysAsArray.size()));
            addNode(connectionToMutate);
        }
    }

    public void addNode(ConnectionGene connectionToMutate) {
        connectionToMutate.enabled = false;
        NodeGene newNode = new NodeGene(nodes.size() + 1, NodeGene.TYPE.HIDDEN);
        addConnection(nodes.get(connectionToMutate.inNode.ID), newNode, 1);
        addConnection(newNode, nodes.get(connectionToMutate.outNode.ID), connectionToMutate.weight);

        nodes.put(newNode.ID, newNode);
    }

    public void mutateWeights() {
        for (ConnectionGene conn : connections.values()) {
            if (Math.random() < 0.9) {
                conn.weight += 0.4 * Math.random() - 0.2;
            } else {
                conn.weight = Math.random();
            }
        }
    }

    public double[] feedForward(double[] inputs) {
        int ins = getInputCount();
        int outs = getOutputCount();
        double[] finalOuts = new double[outs];

        if (inputs.length == ins) {//correct data

            for (int i = 0; i < ins; i++) { //set input node values
                nodes.get(i + 1).value = inputs[i];
            }

            for (int i = 1; i <= outs; i++) { //feedforward by recursively going backward
                nodes.replace(i + ins, searchBack(nodes.get(i + ins)));
                finalOuts[i - 1] = nodes.get(i + ins).value;
            }
            for (NodeGene n : nodes.values()) {
                n.visted = false;
            }
        } else {
            System.out.println("Input size doesnt match genome input size");
        }
        return finalOuts;
    }

    private NodeGene searchBack(NodeGene inNodeGene) {
        NodeGene newNodeGene = new NodeGene(inNodeGene.ID, inNodeGene.type);

        if (inNodeGene.type == NodeGene.TYPE.INPUT || inNodeGene.visted) {
            newNodeGene.value = nodes.get(newNodeGene.ID).value;
        } else {
            for (ConnectionGene conn : connections.values()) { //checking parent 1s connections
                if (conn.outNode.ID == newNodeGene.ID && conn.enabled) {
                    inNodeGene.visted = true;
                    newNodeGene.value += searchBack(conn.inNode).value * conn.weight;
                }
            }
            newNodeGene.value = sigmoid(newNodeGene.value);
        }
        return newNodeGene;
    }
private double sigmoid(double inV) {
    return  1/(1 + Math.pow(Math.E, -inV));
}
    public Genome copy() {
        Genome newGenome = new Genome(0, 0, innovationCounter);
        newGenome.connections = new HashMap<>(connections);
        newGenome.nodes = new HashMap<>(nodes);
        return newGenome;
    }

    public void mutate(double weightChance, double connectionChance, double nodeChance) {
        if (Math.random() < weightChance) {
            mutateWeights();
        }
        if (Math.random() < connectionChance) {
            mutateAddConnection();
        }
        if (Math.random() < nodeChance) {
            mutateAddNode();
        }
    }

    public int getInputCount() {
        int ins = 0;
        for (NodeGene n : nodes.values()) {
            if (n.type == NodeGene.TYPE.INPUT) {
                ins++;
            }
        }
        return ins;
    }

    public int getOutputCount() {
        int outs = 0;
        for (NodeGene n : nodes.values()) {
            if (n.type == NodeGene.TYPE.OUTPUT) {
                outs++;
            }
        }
        return outs;
    }
}
