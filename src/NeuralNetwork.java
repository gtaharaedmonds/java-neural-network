import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {
    List<Layer> layers;
    List<Matrix> weights;
    List<Matrix> biases;
    LossFunction lossFunction;
    List<Matrix> as;
    List<Matrix> zs;
    double stepSize;

    public NeuralNetwork(LossFunction lossFunction) {
        layers = new LinkedList<>();
        weights = new LinkedList<>();
        biases = new LinkedList<>();
        as = new LinkedList<>();
        zs = new LinkedList<>();
        this.lossFunction = lossFunction;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public void compile() {
        weights.clear();
        biases.clear();
        as.clear();
        zs.clear();

        weights.add(null);
        biases.add(null);

        for(int i = 1; i < layers.size(); i++) {
            Layer current = layers.get(i);
            Layer last = layers.get(i - 1);

            Matrix biasesMatrix = new Matrix(current.getSize(), 1);
            Matrix weightsMatrix = new Matrix(current.getSize(), last.getSize());
            weightsMatrix.setRandomValues();

            biases.add(biasesMatrix);
            weights.add(weightsMatrix);

            as.add(new Matrix(current.getSize(), 1));
            zs.add(new Matrix(current.getSize(), 1));

            System.out.println("Added layer size: " + current.getSize());
        }
    }

    public Matrix propagateForward(Matrix input) {
        Matrix output = input;
        as.add(input);
        zs.add(null); //placeholder for first layer to make indexing easier

        for(int i = 1; i < layers.size(); i++) {
            Layer layer = layers.get(i); //ignore first layer which is just our input data
            Matrix layerWeights = weights.get(i);
            Matrix layerBiases = biases.get(i);

            Matrix matmul = layerWeights.multiply(output).add(layerBiases);
            zs.set(i, matmul);
            output = layer.evaluateActivationFunction(matmul);
            as.set(i, output);
        }

        return output;
    }

    public void propagateBackwards(Matrix expected) {
        Matrix dCda = as.get(layers.size() - 1).subtract(expected).multiply(2);

        for(int l = layers.size() - 1; l >= 1; l--) {
            updateWeightMatrix(dCda, l);
            dCda = costDerivativesBackOnce(dCda, l);
            System.out.println("Updated weights for Layer #" + l);
        }
    }

    public void updateWeightMatrix(Matrix dCost_da, int l) {
        Layer currentLayer = layers.get(l);
        Matrix weightMatrix = weights.get(l);
        Matrix gradient = new Matrix(weightMatrix.getRows(), weightMatrix.getCols());

        for(int j = 0; j < weightMatrix.getRows(); j++) { //j is the index of the current layer l
            for(int k = 0; k < weightMatrix.getCols(); k++) { //k is the index of the previous layer l-1
                double aLast = as.get(l - 1).get(k, 0);
                double dActivation = currentLayer.evaluateActivationDerivative(zs.get(l).get(j, 0));
                double dCost_dWeight = aLast * dActivation * dCost_da.get(j, 0);
                gradient.set(j, k, dCost_dWeight);
            }
        }

        weights.set(l, weightMatrix.add(gradient).multiply(1));
    }

    public Matrix costDerivativesBackOnce(Matrix dCdaL, int layer) {
        Matrix dCdaLBack = new Matrix(layers.get(layer - 1).getSize(), 1);

        for(int k = 0; k < dCdaLBack.getRows(); k++) {
            double sum = 0;
            for(int j = 0; j < dCdaL.getRows(); j++) {
                double weight = weights.get(layer).get(j, k);
                double z = zs.get(layer).get(j, 0);
                double activDeriv = layers.get(layer).evaluateActivationDerivative(z);
                double dCda = dCdaL.get(j, 0);
                sum += weight * activDeriv * dCda;
            }

            dCdaLBack.set(k, 0, sum);
        }

        return dCdaLBack;
    }

    public void trainOnce(Matrix input, Matrix target) {
        Matrix output = propagateForward(input);
        System.out.println("Output");
        output.print();
        System.out.println("Expected Output");
        target.print();
        double loss = lossFunction.evaluate(output, target);
        System.out.println("Loss");
        System.out.println(loss);
    }

    void printLayerData() {
        System.out.println("Note: Layer #0 is ignored (the input)");
        for(int layerNum = 1; layerNum < layers.size(); layerNum++) {
            System.out.println("Layer #" + layerNum);

            for(int neuronNum = 0; neuronNum < layers.get(layerNum).getSize(); neuronNum++) {
                double z = zs.get(layerNum).get(neuronNum, 0);
                double a = as.get(layerNum).get(neuronNum, 0);
                System.out.println("z = " + z + ", f(z) = " + a);
            }
        }
    }
}

/*  public void test() {
        double[][] inputData = {{0.2}, {0.8}};
        double[][] targetData = {{1}, {0}};
        Matrix input = new Matrix(inputData);
        Matrix target = new Matrix(targetData);
        double loss = lossFunction.evaluate(input, target);
        System.out.println(loss);
    }
*/

/*    public void propagateBackwards(Matrix expected) {
        int l = 2;
        Layer currentLayer = layers.get(l);
        Matrix weightMatrix = weights.get(l);

        Matrix gradient = new Matrix(weightMatrix.getRows(), weightMatrix.getCols());

        for(int j = 0; j < weightMatrix.getRows(); j++) { //j is the index of the current layer l
            for(int k = 0; k < weightMatrix.getCols(); k++) { //k is the index of the previous layer l-1
                double aLast = as.get(l - 1).get(k, 0);
                double dActivation = currentLayer.evaluateActivationDerivative(zs.get(l).get(j, 0));
                double dCost_da = 2 * (as.get(l).get(j, 0) - expected.get(j, 0));

                double dCost_dWeight = aLast * dActivation * dCost_da;
                gradient.set(j, k, dCost_dWeight);
            }
        }

//        System.out.println("Current weights: ");
//        weightMatrix.print();
//
//        System.out.println("Projected adjustment: ");
//        gradient.print();
//
//        System.out.println("New weights: ");
        weights.set(l, weightMatrix.add(gradient).multiply(1));
        //weights.get(l).print();
    }
*/
