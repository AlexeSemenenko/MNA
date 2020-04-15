package com.company;

import java.util.Arrays;

public class Methods {
    static final double eps = 0.001;

    private static double func(double x) {
        return 30 * Math.pow(x, 4) - 11 * Math.pow(x, 3) - 19 * Math.pow(x, 2) - x + 1;
    }

    private static double func1(double x) {
        return 120 * Math.pow(x, 3) - 33 * Math.pow(x, 2) - 38 * x - 1;
    }

    private static double func2(double x) {
        return 360 * Math.pow(x, 2) - 66 * x - 38;
    }

    private static double func3(double x) {
        return 720 * x - 66;
    }

    private static double ksi1(double x) {
        return -1 / func1(x);
    }

    private static double ksi2(double x) {
        return -func2(x) / (2 * Math.pow(func1(x), 3));
    }

    private static double ksi3(double x) {
        return -1.5 * (Math.pow(func2(x), 2) - func1(x) * func3(x)) / Math.pow(func1(x), 5);
    }

    private static double chebyshevFourthFunc(double x) {
        return x + ksi1(x) * func(x) + ksi2(x) * Math.pow(func(x), 2) + ksi3(x) * Math.pow(func(x), 3);
    }

    public static double chebyshevFourth(double x_n) {
        double x_n_1 = chebyshevFourthFunc(x_n);
        int n = 1;

        while (Math.abs(x_n - x_n_1) > eps) {
            x_n = x_n_1;
            x_n_1 = chebyshevFourthFunc(x_n);
            n++;
        }

        System.out.println("n = " + n);

        return x_n_1;
    }

    private static double[] new_coefficients(double[] coeff) {
        double[] new_coeff = new double[coeff.length];

        new_coeff[0] = Math.pow(coeff[0], 2);
        new_coeff[1] = 2 * coeff[0] * coeff[2] - Math.pow(coeff[1], 2);
        new_coeff[2] = 2 * coeff[0] * coeff[4] - 2* coeff[1] * coeff[3] + Math.pow(coeff[2], 2);
        new_coeff[3] = 2 * coeff[2] * coeff[4] - Math.pow(coeff[3], 2);
        new_coeff[4] = Math.pow(coeff[4], 2);

        return new_coeff;
    }

    private static boolean isStronglySeparated(double[] new_coeff, double[] coeff) {
        for (int i = 1; i < coeff.length; i++) {
            if (Math.abs(new_coeff[i] / Math.pow(coeff[i], 2) + Math.pow(-1, i + 1)) > eps) {
                return false;
            }
        }
        return true;
    }

    public static double[] lobachevsky(double[] coeff) {
        double[] new_coeff = new_coefficients(coeff);
        int n = 1;

        while (!isStronglySeparated(new_coeff, coeff)) {
            coeff = new_coeff;
            new_coeff = new_coefficients(coeff);
            n++;
        }

        double[] roots = new double[coeff.length - 1];

        for (int i = 0; i < roots.length; i++) {
            roots[i] = Math.pow(-new_coeff[i + 1] / new_coeff[i], 1 / Math.pow(2, n));
        }

        System.out.println("n = " + n);

        return roots;
    }
}
