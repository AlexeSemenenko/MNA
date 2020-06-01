package com.company;

public class Main {

    static double eps = 0.00001;
    static double x_0 = -0.03;
    static double y_0 = 2;
    static double[] mf = {x_0, y_0};
    static int count = 0;

    public static void main(String[] args) {

        while (Math.max(Math.abs(mf[0]), Math.abs(mf[1])) > eps) {
            double[][] inverse = inverse(jacobi(x_0, y_0));

            x_0 += (inverse[0][0] * mf[0] + inverse[0][1] * mf[1]);
            y_0 += (inverse[1][0] * mf[0] + inverse[1][1] * mf[1]);

            mf = mf(x_0, y_0);

            ++count;
        }

        System.out.println("x = " + x_0 + " || y = " + y_0 + " || n = " + count);
    }

    public static double[] mf(double x, double y) {
        double[] res = new double[2];

        res[0] = -(Math.sinh(x * y) - 12 * Math.tanh(x) - 0.311);
        res[1] = -(x * x + y * y - 4);

        return res;
    }

    public static double[][] inverse(double[][] matrix) {
        int size = matrix.length;

        double[][] res = new double[size][size];

        double mult;
        double div;

        for(int i = 0; i < size; ++i) {
            res[i][i] = 1;
        }

        for(int i = 0; i < size; ++i) {
            div = matrix[i][i];

            for(int j = 0; j < size; ++j) {
                if(j >= i) {
                    matrix[i][j] /= div;
                }

                res[i][j] /= div;
            }

            for(int k = 0; k < i; ++k) {
                mult = matrix[k][i];

                for(int j = 0; j < size; ++j) {
                    if(j >= i) {
                        matrix[k][j] -= matrix[i][j] * mult;
                    }

                    res[k][j] -= res[i][j] * mult;
                }
            }

            for(int k = i + 1; k < size; ++k) {
                mult = matrix[k][i];

                for(int j = 0; j < size; ++j) {
                    if(j >= i) {
                        matrix[k][j] -= matrix[i][j] * mult;
                    }

                    res[k][j] -= res[i][j] * mult;
                }
            }
        }

        return res;
    }

    public static double[][] jacobi(double x, double y) {
        double[][] res = new double[2][2];

        res[0][0] = y * Math.cosh(x * y) - 12 / Math.pow(Math.cosh(x), 2);
        res[0][1] = x * Math.cosh(x * y);
        res[1][0] = 2 * x;
        res[1][1] = 2 * y;

        return res;
    }
}

