/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NueralNet;

import MainPackage.Board;

/**
 *
 * @author jason
 */
public class Agent {

    double mutateRate = 0.5;
    NueralNetwork brain; //nueral net
    public int fitness = 0;

    Agent() {
        brain = new NueralNetwork(9, 9, 9, 9, 9); //new net
    }

    Agent(NueralNetwork inBrain) {
        brain = inBrain;
    }

    public void place(Board inB, int player) {
        int attempt = 0;
        int[] toPlace = decide(inB, player);
        int x = toPlace[attempt] % 3;
        int y = toPlace[attempt] / 3;
        while (!inB.place(x, y, player)) {
                        attempt++;
                        x = toPlace[attempt] % 3;
            y = toPlace[attempt] / 3;
        }
    }

    public int[] decide(Board inB, int player) {
        double[] brainIn = new double[inB.board.length * inB.board[0].length];
        for (int i = 0; i < inB.board.length; i++) {
            for (int j = 0; j < inB.board[i].length; j++) {
                brainIn[i + j * inB.board.length] = inB.board[i][j] * player;
            }
        }
        double[] brainOuts = brain.feedForward(brainIn);
        int[] positions = new int[brainOuts.length];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = -1;
        }
        int posIndex = 0;
        while (positions[positions.length - 1] == -1) {
            double maxV = 0;
            int maxI = 0;
            for (int i = 0; i < brainOuts.length; i++) {
                if (brainOuts[i] > maxV) {
                    maxV = brainOuts[i];
                    maxI = i;
                }
            }
            brainOuts[maxI] = 0;
            positions[posIndex] = maxI;
            posIndex++;
        }
        //int[9] indexs = brainOuts.sorted.indexs
        //needs to return sorted array of indexs to place
        return positions;
    }

    public void mutate() {
        for (int layer = 0; layer < brain.weights.length; layer++) {
            for (int n1 = 0; n1 < brain.weights[layer].length; n1++) {
                for (int n2 = 0; n2 < brain.weights[layer][n1].length; n2++) {
                    if (Math.random() < mutateRate) {
                        brain.weights[layer][n1][n2] += (Math.random() - 0.5) / 5;
                    }
                }
            }
        }
    }
}
