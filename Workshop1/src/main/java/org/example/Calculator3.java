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

        double monthlyRate = annualRate/100/12;

    }
}
