package com.company;

public class Main {

    public static void main(String[] args) {
        double x_si = Methods.simpleIterations(2.35);
        System.out.println("SI = " + x_si);

        double x_n = Methods.newton(2.5);
        System.out.println("N = " + x_n);

        double x_st = Methods.steffensen(2.25);
        System.out.println("St = " + x_n);

        double x_cht = Methods.chebyshevThird(2.25);
        System.out.println("Ch = " + x_n);
    }
}
