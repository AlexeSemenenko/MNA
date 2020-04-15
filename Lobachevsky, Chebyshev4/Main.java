package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println("Chebyshev's fourth method:");
	    System.out.println("1 root = " + Methods.chebyshevFourth(0.9));
        System.out.println("2 root = " + Methods.chebyshevFourth(-0.7));
        System.out.println("3 root = " + Methods.chebyshevFourth(-0.3));
        System.out.println("4 root = " + Methods.chebyshevFourth(0.25));

        System.out.println("\nLobachevsky's method:");
        double[] coeff = {30, -11, -19, -1, 1};
        double[] roots = Methods.lobachevsky(coeff);
        for (int i = 0; i < roots.length; i++) {
            System.out.println(i + 1 +" abs root = " + roots[i]);
        }
    }
}
