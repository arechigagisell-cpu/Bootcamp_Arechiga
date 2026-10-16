package org.example;

import java.util.Scanner;

public class Calculator1 {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("Enter your loan amount: ");
        double Principal = scanner.nextDouble();
        System.out.println("Enter the annual interest rate: ");
        double annualRate = scanner.nextDouble();
        System.out.println("Enter the length of the loan: ");
        int years = scanner.nextInt();

        double monthlyRate = annualRate / 12 / 100;
        int monthlyPayments  = years * 12;

        double monthlyPayment = Principal * (monthlyRate * Math.pow(1 + monthlyRate, monthlyPayments)) / (Math.pow((1 + monthlyRate),monthlyPayments) - 1);

        double totalInterest = (monthlyPayment * monthlyPayments) - Principal;

        System.out.printf("The monthly payment is %.2f%n", monthlyPayment);
        System.out.printf("The total interest is $%.2f%n", totalInterest);


        scanner.close();

    }
}
