public class Layer {
    private int size;
    private LayerType layerType;
    private ActivationFunction activationFunction;

    public Layer(int size, LayerType layerType, ActivationFunction activationFunction) {
        this.size = size;
        this.layerType = layerType;
        this.activationFunction = activationFunction;
    }

    public int getSize() {
        return size;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public Matrix evaluateActivationFunction(Matrix matrix) {
        Matrix evaluated = new Matrix(matrix.getRows(), matrix.getCols());
        for(int row = 0; row < evaluated.getRows(); row++) {
            for(int col = 0; col < evaluated.getCols(); col++) {
                evaluated.set(row, col, activationFunction.evaluate(matrix.get(row, col), matrix));
            }
        }

        return evaluated;
    }

    public double evaluateActivationDerivative(double x) {
        return activationFunction.evaluateDerivative(x);
    }

    public String getEvaluationFunctionName() {
        return activationFunction.getName();
    }
}
