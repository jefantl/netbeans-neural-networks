
public class NueralNetwork {

    Layer[] layers;
    double[][] outputs;
    double[][] inputs;

    NueralNetwork(double[][] ins, double[][] outs, int Hlayers, int HlayerAmount) {
        outputs = outs;
        inputs = ins;
        layers = new Layer[2 + Hlayers];

        layers[0] = new Layer(ins[0]);
        for (int i = 1; i < layers.length; i++) {
            layers[i] = new Layer(layers[i - 1], HlayerAmount);
        }
        layers[layers.length - 1] = new Layer(layers[layers.length - 2], outputs[0].length);

    }

    double forwardPropogate(double[] in) {
        layers[0].nuerons = in;
        for (int i = 1; i < layers.length; i++) {
            layers[i].feedForward();
        }
        return layers[layers.length - 1].nuerons[0];
    }

    public String toString() {
        String rString = "";
        for (int i = 0; i < layers.length; i++) {
            rString += "Layer " + i + "\n";
            rString += layers[i].toString();
            rString += "\n";
        }

        return rString;
    }
    
    
}
