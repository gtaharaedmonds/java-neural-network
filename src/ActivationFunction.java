public interface ActivationFunction {
     public double evaluate(double x, Matrix output);

     public double evaluateDerivative(double x);

     public String getName();
}
