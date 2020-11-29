public class CrossEntropy implements LossFunction {
    public double evaluate(Matrix real, Matrix expected) {
        double sum = 0;
        for(int row = 0; row < real.getRows(); row++) {
            double realValue = real.get(row, 0);
            double expectedValue = expected.get(row, 0);

            if(realValue == 0) {
                System.out.println("Real value is 0!");
                realValue = Double.MIN_VALUE;
            }

            sum += expectedValue * Math.log(realValue);
            System.out.println(sum);
        }

        return sum * -1;
    }
}
