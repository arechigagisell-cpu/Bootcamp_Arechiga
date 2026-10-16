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

        double dayRate = annualInterest /100 /365;
        int totalDay = years * 365;








    }
}
