package NueralNet;

import MainPackage.Board;
import java.util.ArrayList;
import processing.core.*;

public class genePool {

    public Board myBoard;
    public Agent[] myNets;
public Agent bestPlayer;
    public genePool(int popSize, PApplet p) {
        myBoard = new Board(p);
        myNets = new Agent[popSize];
        for (int i = 0; i < myNets.length; i++) {
            myNets[i] = new Agent();
        }
    }

    public void updatFitness() {
        for (int i = 0; i < myNets.length - 1; i++) {
            for (int j = i + 1; j < myNets.length; j++) {
                pitNets(myNets[i], myNets[j]);
            }
        }
bestPlayer = myNets[0];
        for (int i = 1; i < myNets.length; i++) {
            if (myNets[i].fitness > bestPlayer.fitness) {
                bestPlayer = myNets[i];
            }
        }
    }

    public void pitNets(Agent in1, Agent in2) {
        myBoard.reset();
        while (myBoard.findwin() == 0) {
            in1.place(myBoard, 1);
            if (myBoard.findwin() == 0) {
             in2.place(myBoard, -1);
            }
        }
        if (myBoard.findwin() == 1) {
            in1.fitness++;
        } else if (myBoard.findwin() == -1) {
            in2.fitness++;
        }
    }

    public void repopulate() {
        ArrayList<NueralNetwork> grabPop = new ArrayList<>();
        for (Agent myNet : myNets) {
            for (int addNum = -2; addNum < myNet.fitness; addNum++) {
                grabPop.add(myNet.brain);
            }
        }
        for (int i = 0; i < myNets.length; i++) {
            myNets[i] = new Agent(grabPop.get((int) (Math.random() * (grabPop.size() - 1))));
            myNets[i].mutate();
        }
    }
}
