import java.util.Random;

public class Matrix {
    double[][] matrix;

    public Matrix(double[][] data) {
        if(data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Matrix has to have at least one row and at least one column!");
        }

        matrix = data;
    }

    public Matrix(int rows, int cols) {
        if(rows == 0 || cols == 0) {
            throw new IllegalArgumentException("Matrix has to have at least one row and at least one column!");
        }

        matrix = new double[rows][cols];
    }

    public int getRows() {
        return matrix.length;
    }

    public int getCols() {
        return matrix[0].length;
    }

    public void set(int row, int col, double value) {
        matrix[row][col] = value;
    }

    public double get(int row, int col) {
        return matrix[row][col];
    }

    public Matrix add(Matrix other) {
        if(this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            throw new IllegalArgumentException("Matrices need to have the same dimensions!");
        }

        Matrix added = new Matrix(this.getRows(), this.getCols());
        for(int row = 0; row < added.getRows(); row++) {
            for(int col = 0; col < added.getCols(); col++) {
                added.set(row, col, this.get(row, col) + other.get(row, col));
            }
        }

        return added;
    }

    public Matrix subtract(Matrix other) {
        if(this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            throw new IllegalArgumentException("Matrices need to have the same dimensions!");
        }

        Matrix added = new Matrix(this.getRows(), this.getCols());
        for(int row = 0; row < added.getRows(); row++) {
            for(int col = 0; col < added.getCols(); col++) {
                added.set(row, col, this.get(row, col) - other.get(row, col));
            }
        }

        return added;
    }

    public Matrix multiply(double constant) {
        Matrix added = new Matrix(this.getRows(), this.getCols());
        for(int row = 0; row < added.getRows(); row++) {
            for(int col = 0; col < added.getCols(); col++) {
                added.set(row, col, this.get(row, col) * constant);
            }
        }

        return added;
    }

    public Matrix multiply(Matrix other) {
        if(this.getCols() != other.getRows()) {
            throw new IllegalArgumentException("Rows of matrix 1 must match columns of matrix 2 for multiplication");
        }

        Matrix added = new Matrix(this.getRows(), other.getCols());
        for(int row = 0; row < added.getRows(); row++) {
            for(int col = 0; col < added.getCols(); col++) {
                double dot = 0;

                for(int i = 0; i < this.getCols(); i++) {
                    dot += this.get(row, i) * other.get(i, col);
                }

                added.set(row, col, dot);
            }
        }

        return added;
    }

    void print() {
        for(int row = 0; row < this.getRows(); row++) {
            for(int col = 0; col < this.getCols(); col++) {
                System.out.print(this.get(row, col) + " ");
            }

            System.out.println();
        }
    }

    void setRandomValues() {
        Random r = new Random();
        r.setSeed(123456789);

        for(int row = 0; row < this.getRows(); row++) {
            for(int col = 0; col < this.getCols(); col++) {
                set(row, col, -0.2 + (0.2 - -0.2) * r.nextDouble());
            }
        }
    }


}
