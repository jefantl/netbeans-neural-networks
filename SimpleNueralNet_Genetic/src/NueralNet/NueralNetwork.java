package NueralNet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NueralNetwork implements java.io.Serializable {

    double[][][] weights;
    Nueron[][] nuerons;
    double err = 0;

    public NueralNetwork(int... nueronFrame) {
        nuerons = new Nueron[nueronFrame.length][];
        for (int i = 0; i < nuerons.length; i++) {
            nuerons[i] = new Nueron[nueronFrame[i]];
            for (int j = 0; j < nuerons[i].length; j++) {
                nuerons[i][j] = new Nueron();
            }
        }
        weights = new double[nuerons.length - 1][][];

        //initalize weights
        for (int layer = 0; layer < nuerons.length - 1; layer++) {
            weights[layer] = new double[nuerons[layer].length][nuerons[layer + 1].length];
            for (int prevNueron = 0; prevNueron < weights[layer].length; prevNueron++) {
                for (int nueron = 0; nueron < weights[layer][prevNueron].length; nueron++) {
                    weights[layer][prevNueron][nueron] = (Math.random() * 2.0) - 1.0;
                }
            }
        }
    }

    public double[] feedForward(double[] in) {
        if (in.length != nuerons[0].length) {
            System.err.println("Training data doent match nets input size, " + in.length + ", " + nuerons[0].length);
            return null;
        }
        for (int i = 0; i < in.length; i++) {
            nuerons[0][i].value = in[i];
        }
        for (int layer = 1; layer < nuerons.length; layer++) {
            for (int nueron = 0; nueron < nuerons[layer].length; nueron++) {
                double sum = nuerons[layer][nueron].bias;
                for (int prevNueron = 0; prevNueron < nuerons[layer - 1].length; prevNueron++) {
                    sum += nuerons[layer - 1][prevNueron].value * weights[layer - 1][prevNueron][nueron];
                }
                nuerons[layer][nueron].value = sigmoid(sum);
            }
        }
        double[] returnVal = new double[nuerons[nuerons.length - 1].length];
        for (int i = 0; i < returnVal.length; i++) {
            returnVal[i] = nuerons[nuerons.length - 1][i].value;
        }
        return returnVal;
    }

    double sigmoid(double x) {
        return 1.0f / (1.0f + (float) Math.exp(-x));
    }

    double MSE(double[] ins, double[] outs) {
        feedForward(ins);
        double returnValue = 0;
        for (int nueron = 0; nueron < nuerons[nuerons.length - 1].length; nueron++) {
            returnValue += Math.pow(nuerons[nuerons.length - 1][nueron].value - outs[nueron], 2);
        }
        returnValue /= nuerons.length;
        return returnValue;
    }

    public void saveNetwork(String fileLoc) throws FileNotFoundException, IOException {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileLoc); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(weights);
                out.writeObject(nuerons);
            }
            System.out.printf("Serialized data is saved in " + fileLoc);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean readNetwork(String fileLoc) throws ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(fileLoc);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            double[][][] readWeights = (double[][][]) in.readObject();
            Nueron[][] readNuerons = (Nueron[][]) in.readObject();
            if (readWeights.length == weights.length && nuerons.length == readNuerons.length) {
                weights = readWeights;
                nuerons = readNuerons;
            } else {
                return false;
            }
            return true;
        } catch (IOException i) {
            return false;
        }
    }
}
