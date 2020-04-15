package com.company;

public class Methods{

    static final double eps = 0.001;


    public static double func(double x){
        return 2 * x * Math.cos(2 * x) - Math.pow(x - 2, 2);
    }

    public static double func1(double x){
        return -2 * x - 4 * x * Math.sin(2 * x) + 2 * Math.cos(2 * x) + 4;
    }

    public static double func2(double x){
        return -8 * Math.sin(2 * x) - 8 * x * Math.cos(2 * x) - 2;
    }

    public static double fi(double x, double c){
        return x + c * func(x);
    }

    public static double newtonFunc(double x){
        return x - func(x) / func1(x);
    }

    public static double steffFunc(double x, double c){
        return (x * fi(fi(x, c), c) - Math.pow(fi(x, c), 2)) / (fi(fi(x, c), c) - 2 * fi(x, c) + x);
    }

    public static double chebyshevThirdFunc(double x){
        return x - func(x) / func1(x) - Math.pow(func(x), 2) * func2(x) / (2 * Math.pow(func1(x), 3));
    }

    public static double simpleIterations(double x_n){
        double c = -2 / func1(2.5);
        double x_n_1 = fi(x_n, c);
        int n = 1;

        while (Math.abs(x_n - x_n_1) > eps){
            x_n = x_n_1;
            x_n_1 = fi(x_n_1, c);
            n++;
        }

        System.out.println("n = " + n);

        return x_n_1;
    }

    public static double newton(double x_n){
        double x_n_1 = newtonFunc(x_n);
        int n = 1;

        while ((Math.abs(x_n - x_n_1)) > eps){
            x_n = x_n_1;
            x_n_1 = newtonFunc(x_n);
            n++;
        }

        System.out.println("n = " + n);

        return x_n_1;
    }

    public static double steffensen(double x_n){
        double c = -2 / func1(2.5);
        double x_n_1 = steffFunc(x_n, c);
        int n = 1;

        while (Math.abs(x_n - x_n_1) > eps){
            x_n = x_n_1;
            x_n_1 = steffFunc(x_n_1, c);
            n++;
        }

        System.out.println("n = " + n);

        return x_n_1;
    }

    public static double chebyshevThird(double x_n){
        double x_n_1 = chebyshevThirdFunc(x_n);
        int n = 1;

        while (Math.abs(x_n - x_n_1) > eps){
            x_n = x_n_1;
            x_n_1 = chebyshevThirdFunc(x_n);
            n++;
        }

        System.out.println("n = " + n);

        return x_n_1;
    }
}
