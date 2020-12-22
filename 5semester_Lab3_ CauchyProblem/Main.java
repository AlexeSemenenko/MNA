package com.company;

public class Main {
    static double a = 0;
    static double b = 1;
    static double t = (b - a) / 10;

    public static void main(String[] args) {
	    //Неявный метод Эйлера с помощью метода Ньютона
        double[] y = new double[11];
        double y0;
        double y1;
        y[0] = -1;
        for (int j = 0; j < 10; j++) {
            y1 = y0 = y[j];

            for (int k = 1; k == 1 || Math.abs(y0 - y1) > Math.pow(t, 2); k++) {
                y0 = y1;
                y1 = y0 - (y0 - y[j] - t * f(a + (j + 1) * t, y0)) / (1 - t * df(a + (j + 1) * t, y0));
            }
            y[j + 1] = y1;
        }

        double[] euler = new double[11];
        System.out.println("Применяя неяный метод Ньютона, получим:");
        for (int i = 0; i < y.length; i++) {
            euler[i] = y[i];
            System.out.println(y[i]);
        }


        //Метод Рунге-Кутта
        y = new double [11];
        y[0] = -1;

        for (int j = 0; j < 10; j++) {
            y[j + 1] = y[j] + t * (k1(a + j * t, y[j]) + k2(a + j * t, y[j])) / 2;
        }

        double[] rungeKutta = new double[11];
        System.out.println("\nПрименяя метод Рунге-Кутта, получим:");
        for (int i = 0; i < y.length; i++) {
            rungeKutta[i] = y[i];
            System.out.println(y[i]);
        }


        //Метод последовательного повышения порядка точности
        double[] y3 = new double [11];
        double[] y2 = new double [11];
        y2[0] = y3[0] = -1;

        for (int j = 0; j < 10; j++) {
            y2[j + 1] = y3[j] + t * f(a + t * j, y2[j]);
            y3[j + 1] = y3[j] + t / 2 * (f(a + t * j, y2[j]) + f(a + t * (j + 1), y2[j + 1]));
        }

        double[] successiveAccuracy = new double[11];
        System.out.println("\nПрименяя метод последовательного повышения порядка точности, получим:");
        for (int i = 0; i < y3.length; i++) {
            successiveAccuracy[i] = y3[i];
            System.out.println(y3[i]);
        }


        //Экстраполяционный метод Адамса 3-ого порядка
        y = new double [11];
        y[0] = -1;
        y[1] = y[0] + t / 4 * (f(a, y[0]) + 3 * f(a + 2. / 3 * t, y[0] + 2. / 3 * t *
                f(a + t / 3, y[0] + t / 3 * f(a, y[0]))));
        y[2] = y[1] + t / 4 * (f(a + t, y[1]) + 3 * f(a + (1 + 2. / 3) * t, y[1] + 2. / 3 * t *
                f(a + (1 + 1. / 3) * t, y[1] + 1. / 3 * t * f(a + t, y[1]))));

        for (int j = 2; j < 10; j++) {
            y[j + 1] = y[j] + t / 12 * (23 * f(a + t * j, y[j]) - 16 * f(a + t * (j - 1), y[j - 1]) +
                    5 * f(a + t * (j - 2), y[j - 2]));
        }

        System.out.println("\nПрименяя экстраполяционный метод Адамса 3-ого прядка, получим:");
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }


        //Погрешности методов относительно метода Адамса
        System.out.println("\nПогрешность для неявного метода Эйлера:");
        for(int i = 0; i < y.length; i++) {
            System.out.println(y[i] - euler[i]);
        }

        System.out.println("\nПогрешность для метода Рунге-Кутта:");
        for(int i = 0; i < y.length; i++) {
            System.out.println(y[i] - rungeKutta[i]);
        }

        System.out.println("\nПогрешность для метода последовательного повышения порядка точности:");
        for(int i = 0; i < y.length; i++) {
            System.out.println(y[i] - successiveAccuracy[i]);
        }

    }

    static double f(double t, double y) {
        return -y * Math.cos(t) + Math.sin(t) * Math.cos(t);
    }

    static double df(double t, double y) {
        return -Math.cos(t);
    }

    static double k1(double t, double y) {
        return f(t, y);
    }

    static double k2(double t, double y) {
        return f(t + Main.t, y + Main.t * k1(t, y));
    }

}
