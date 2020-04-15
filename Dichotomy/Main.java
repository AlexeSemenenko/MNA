package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println(dichotomyForFirst(0.5, 1, 0.001));
        System.out.println(dichotomyForFirst(-3, -2, 0.001));
        System.out.println(dichotomyForSecond(-0.5, 1.5, 0.001));
    }

    public static double firstF(double x){
        return 3 * Math.pow(x, 4) + 8 * Math.pow(x, 3) + 6 * Math.pow(x, 2) - 10;
    }

    public static double secondF(double x){
        return Math.log10(2 + x) + 2 * x - 3;
    }

    public static double dichotomyForFirst(double a, double b, double eps){

         while (b - a > eps){
             double c = (a + b) / 2;

             if(firstF(b) * firstF(c) < 0){
                 a = c;
             } else {
                 b = c;
             }
         }

         return (a + b) / 2;
    }

    public static double dichotomyForSecond(double a, double b, double eps){

        while (b - a > eps){
            double c = (a + b) / 2;

            if(secondF(b) * secondF(c) < 0){
                a = c;
            } else {
                b = c;
            }
        }

        return (a + b) / 2;
    }

}


