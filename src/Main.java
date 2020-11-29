

public class Main {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(new CrossEntropy());
        nn.addLayer(new Layer(3, LayerType.INPUT, new Sigmoid()));
        nn.addLayer(new Layer(5, LayerType.HIDDEN, new Sigmoid()));
        nn.addLayer(new Layer(3, LayerType.OUTPUT, new SoftMax()));
        nn.compile();

        double[][] inputData = {{0.8}, {0.1}, {0.1}};
        Matrix input = new Matrix(inputData);
        double[][] targetData = {{0}, {0}, {1}};
        Matrix target = new Matrix(targetData);

        System.out.println("Expected output: ");
        target.print();
        System.out.println("TAKE 1");
        Matrix output = nn.propagateForward(input);
        System.out.println("Output: ");
        output.print();

        nn.propagateBackwards(target);

        System.out.println("TAKE 2");
        output = nn.propagateForward(input);
        System.out.println("Output: ");
        output.print();

        nn.propagateBackwards(target);

        System.out.println("TAKE 3");
        output = nn.propagateForward(input);
        System.out.println("Output: ");
        output.print();
    }

    void longBackprop() {
        NeuralNetwork nn = new NeuralNetwork(new CrossEntropy());
        nn.addLayer(new Layer(2, LayerType.INPUT, new Sigmoid()));
        nn.addLayer(new Layer(3, LayerType.HIDDEN, new Sigmoid()));
        nn.addLayer(new Layer(2, LayerType.OUTPUT, new SoftMax()));
        nn.compile();

        double[][] inputData = {{1}, {1}};
        Matrix input = new Matrix(inputData);
        double[][] targetData = {{0}, {1}};
        Matrix target = new Matrix(targetData);

        System.out.println("Expected output: ");
        target.print();
        System.out.println("TAKE 1");
        Matrix output = nn.propagateForward(input);
        nn.printLayerData();

        System.out.println("Output: ");
        output.print();

        System.out.println();

        System.out.println("Propagate backwards???");
        nn.propagateBackwards(target);
        System.out.println();


        System.out.println("TAKE 2");
        output = nn.propagateForward(input);
        nn.printLayerData();

        System.out.println("Output: ");
        output.print();

        System.out.println();


        System.out.println("Propagate backwards???");
        nn.propagateBackwards(target);


        System.out.println();

        System.out.println("TAKE 3");
        output = nn.propagateForward(input);
        nn.printLayerData();

        System.out.println("Output: ");
        output.print();
    }

    void testPropForward() {
        NeuralNetwork nn = new NeuralNetwork(new CrossEntropy());
        nn.addLayer(new Layer(2, LayerType.INPUT, new Sigmoid()));
        nn.addLayer(new Layer(3, LayerType.HIDDEN, new Sigmoid()));
        nn.addLayer(new Layer(2, LayerType.OUTPUT, new SoftMax()));
        nn.compile();

        double[][] inputData = {{1}, {0}};
        double[][] targetData = {{1}, {0}};
        Matrix input = new Matrix(inputData);
        Matrix target = new Matrix(targetData);
//        Matrix output = nn.propagateForward(input);
        nn.trainOnce(input, target);
    }

    void testMultiply() {
        double[][] data1 = {{1,2}, {3,4}};
        double[][] data2    = {{5,6}, {7,8}};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = new Matrix(data2);

        m1.print();
        System.out.println();

        m2.print();
        System.out.println();

        Matrix result = m1.multiply(m2);
        result.print();
    }
}
