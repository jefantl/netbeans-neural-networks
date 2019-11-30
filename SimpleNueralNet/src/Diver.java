
public class Diver {

    public static void main(String[] args) {
        double[][] input = {{0, 1}, {1,1},{1,0},{0,0}};
        double[][] outputs = {{0},{1},{0},{1}};
        NueralNetwork NN = new NueralNetwork(input, outputs, 3, 3); //ins, outs, num of hidden layers, height of layers
        
        System.out.println(NN.toString());
    }
}
