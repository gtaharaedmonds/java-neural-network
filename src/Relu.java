public class Relu implements ActivationFunction{
    public double evaluate(double x, Matrix output) {
        return Math.max(0, x);
    }

    public double evaluateDerivative(double x) {
        return -1;
    }

    public String getName() {
        return "Relu";
    }
}
