package org.example;

import java.util.Scanner;

public class Calculator3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter monthly payout: ");
        double monthlyPayout = scanner.nextDouble();
        System.out.println("Enter annual interest rate: ");
        double annualRate = scanner.nextDouble();
        System.out.println("Enter number of years: ");
        int years = scanner.nextInt();

        double monthlyRate = annualRate/100/12;
        int totalPayments = years*12;

        double presentValue = monthlyPayout * ((1-Math.pow(1 + monthlyRate, - totalPayments)) / monthlyRate);

        System.out.printf("The present value is: %.2f%n",presentValue);

        scanner.close();


    }
}
