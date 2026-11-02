package org.example;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = FileManager.loadTransactions();
    public static void main(String[] args) {
        boolean running = true;

        while(running){
            System.out.println("\n\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC36  HAPPY PAWS VETERINARY LEDGER  \uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC36");
            System.out.println("D) \uD83D\uDCB0 Add some funds(Deposit)");
            System.out.println("P) \uD83D\uDCB8 Pay the Bills (Debit)");
            System.out.println("L) \uD83D\uDCD6  Peek at the paw Ledger");
            System.out.println("X) \uD83D\uDC36 Time to Paw Out! (Exit)");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice){
                case "D":
                    addTransaction(true);
                    break;
                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    System.out.println("\uD83D\uDC3E Thank you for visiting Happy Paws!");
                    System.out.println("Keep those tails wagging!");
                    running = false;
                    break;
                default:
                    System.out.println("\uD83D\uDC3E Oops! Wrong paw!");
                    System.out.println("Please choose D, P, L, or X.");
            }
        }
        scanner.close();
        }

    }
