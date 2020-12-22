package com.company;

public class Main {

    public static void main(String[] args) {
        double a = Math.PI / 2;
        double b = 3 * Math.PI / 2;
        double eps = 0.00001;

        //Правило Рунге к составной формуле левых прямоугольников
        int N = 1;
        double h = (b - a) / N;
        double I1 = getI(a, h, N);
        double I2 = getI(a, h / 2, N * 2);

        while (Math.abs(2 * (I1 - I2)) > eps) {
            N *= 2;
            h = (b - a) / N;

            I1 = getI(a, h, N);
            I2 = getI(a, h / 2, N * 2);
        }

        System.out.println("Применяя правило Рунге (составные левые прямоугольники), получим:\nI = " + I2 + "\nN = " + N * 2 + "\nh = " + h / 2);


        //Составная формула трапеций
        N = 719;
        h = (b - a) / N;
        double sum = (h / 2) * (f(a) + f(b));

        for (int i = 1; i < N; i++) {
            sum += h * f(a + i * h);
        }

        System.out.println("\nПрименяя составную формулу трапеций, получим:\nI = " + sum);


        //Составная формула Симпсона
        N = 23;
        h = (b - a) / N;
        double x = a;
        sum = 0;

        for(int i = 0; i < N; i++) {
            sum += f(x) + 4 * f((x + x + h) / 2) + f(x + h);
            x += h;
        }

        sum *= h / 6;

        System.out.println("\nПрименяя составную формулу Симпсона, получим:\nI = " + sum);


        //Формула НАСТ Гаусса
        sum = (b - a) / 2 * (0.34785484 * newF(a, b,0.86113631) +  0.34785484 * newF(a, b,-0.86113631) +
                0.65214516 * newF(a, b, 0.33998104) + 0.65214516 * newF(a, b,-0.33998104));

        System.out.println("\nПрименяю формулу НАСТ Гаусса, получим:\nI = " + sum);


    }

    public static double f (double x) {
        return 1 / (3 + 2 * Math.cos(x));
    }

    public static double getI (double a, double h, double N) {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += f(a + h * i);
        }

        return h * sum;
    }

    public static double newX (double a, double b,double x) {
        return (b - a) / 2 * x + (b + a) / 2;
    }

    public static double newF (double a, double b,double x) {
        return 1 / (3 + 2 * Math.cos(newX(a, b, x)));
    }

}
