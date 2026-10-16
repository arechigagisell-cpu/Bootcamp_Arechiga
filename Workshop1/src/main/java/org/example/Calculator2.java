package org.example;

import java.util.Scanner;

public class Calculator2 {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("Enter your deposit: ");
        double deposit = scanner.nextDouble();
        System.out.println("Enter the annual interest: ");
        double annualInterest = scanner.nextDouble();
        System.out.println("Enter the amount of years: ");
        int years = scanner.nextInt();

        double dayRate = (annualInterest /100);
        int totalDay = years * 365;

        double futureValue = deposit * Math.pow(1 + (dayRate/365), totalDay);
        double interestEarned = futureValue - deposit;

        System.out.printf("The end value is: %.2f%n", futureValue);
        System.out.printf("The total interest is: %.2f%n", interestEarned);

        scanner.close();


    }
}
