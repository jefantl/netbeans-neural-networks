

public class Layer {

    double[] nuerons;
    double[][] weights;
    double[] bias;
    double[] biasWeights;
    Layer prevLayer;

    public Layer(Layer lastOne, int numNuerons) {
        prevLayer = lastOne;
        nuerons = new double[numNuerons];
        weights = new double[prevLayer.nuerons.length][numNuerons];
        bias = new double[numNuerons];
        biasWeights = new double[numNuerons];
        for (int i = 0; i < numNuerons; i++) {
            bias[i] = 1;
            biasWeights[i] = (Math.random() * 2) - 1;
        }
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = (Math.random() * 2) - 1;
            }
        }
    }

    public Layer(double[] inputs) {
        nuerons = inputs;
        weights = null;
        bias = null;
    }

    void feedForward() {
        for (int i = 0; i < nuerons.length; i++) {
            nuerons[i] = bias[i] * biasWeights[i];
            for (int j = 0; j < prevLayer.nuerons.length; j++) {
                nuerons[i] += weights[j][i] * prevLayer.nuerons[j];
            }
            nuerons[i] = normalizeInt(nuerons[i]);
        }
    }

    void feedForward(double[] inLayer) {
        for (int i = 0; i < nuerons.length; i++) {
            nuerons[i] = bias[i] * biasWeights[i];
            for (int j = 0; j < inLayer.length; j++) {
                nuerons[i] += weights[j][i] * inLayer[j];
            }
            nuerons[i] = normalizeInt(nuerons[i]);
        }
    }

    double normalizeInt(double x) {
        return 1.0f / (1.0f + (float) Math.exp(-x));
    }

    @Override
    public String toString() {
        String rString = "";

        if (weights != null) {
            rString += "Weights:\n";
            for (double[] i : weights) {
                rString += "[";
                for (double j : i) {
                    rString += (float) (j) + ", ";
                }
                rString += "]\n";
            }
        }
        if (bias != null) {
            rString += "Biases:\n[";
            for (double i : bias) {
                rString += (float) (i) + ", ";
            }
            rString += "]\n";
        }
        rString += "Nuerons:\n[";
        for (double n : nuerons) {
            rString += (float) (n) + ", ";
        }
        rString += "]\n";
        return rString;
    }
}
