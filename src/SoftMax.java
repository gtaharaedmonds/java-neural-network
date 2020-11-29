public class SoftMax implements ActivationFunction {
    public double evaluate(double x, Matrix output) {
        double expSum = 0;
        for(int row = 0; row < output.getRows(); row++) {
            expSum += Math.exp(output.get(row, 0));
        }

        return Math.exp(x) / expSum;
    }

    public double evaluateDerivative(double x) {
        return -1;
    }

    public String getName() {
        return "SoftMax";
    }
}
