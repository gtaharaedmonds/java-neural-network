
public class Sigmoid implements ActivationFunction {
    public double evaluate(double x, Matrix output) {
        return 1 / (1 + Math.exp(-x));
    }

    public double evaluateDerivative(double x) {
        return evaluate(x, null) * (1-evaluate(x, null));
    }

    public String getName() {
        return "Sigmoid";
    }
}
