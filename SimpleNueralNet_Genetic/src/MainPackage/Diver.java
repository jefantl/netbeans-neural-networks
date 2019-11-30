package MainPackage;

import static MainPackage.Board.getSketchClassName;
import NueralNet.Agent;
import NueralNet.NueralNetwork;
import java.util.Arrays;
import NueralNet.genePool;
import processing.core.PApplet;
import static processing.core.PApplet.concat;

public class Diver extends PApplet {

    Board myBoard = new Board(this);

    public static void main(String[] args) {
        String[] mainSketch = concat(new String[]{getSketchClassName()}, args);
        PApplet.main(mainSketch);
        
    }

    public static final String getSketchClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        

        myBoard.reset();
        myBoard.update();
        GANN.updatFitness();

    }
    public genePool GANN = new genePool(350, this);
int epoch = 0;
    public void draw() {

            GANN.repopulate();
            GANN.updatFitness();
            epoch++;
            if(epoch % 1 == 0) System.out.println("epoch: " + epoch); 
    }

    public void mousePressed() {
        if (myBoard.place((int) (((float) (mouseX) / (float) width) * 3), (int) (((float) (mouseY) / (float) height) * 3), 1)) {
            if (!checkWins()) {
                GANN.bestPlayer.place(myBoard, -1);
            }
            checkWins();
        }

        myBoard.update();
    }

    public boolean checkWins() {
        if (myBoard.findwin() != 0) {
            System.out.println("win state: " + myBoard.findwin());
            myBoard.reset();
            return true;
        }
        return false;
    }
}
