package org.example;

import jdk.swing.interop.SwingInterOpUtils;

import java.beans.beancontext.BeanContextServiceRevokedEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
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
    public static void addTransaction(boolean isDeposit){

        if (isDeposit){
            System.out.println("\n\uD83D\uDC3E ADD DEPOSIT \uD83D\uDC3E");
        }else{
            System.out.println("\n\uD83D\uDCB8 MAKE PAYMENT \uD83D\uDCB8");
        }
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter vendor or customer: ");
        String vendor = scanner.nextLine().trim();

        if(description.isEmpty() || vendor.isEmpty() || description.contains("|") || vendor.contains("|")){
            System.out.println("Description and vendor must be empty");
            System.out.println("or contain the | character.");
            return;
        }
        double amount = readAmount("Enter amount: ");

        if (!isDeposit){
            amount = -amount;
        }
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(chronoUnit.SECONDS);

        Transaction newTransaction = new Transaction(date, time,description, vendor, amount);

        if(FileManager.saveTransaction(newTransaction)){
            transactions.add(newTransaction);
            System.out.println("Transaction saved successfully!");
        }
    }
    public static void showLedger(){
        boolean viewingLedger = true;

        while(viewingLedger){
            System.out.println("\n\uD83D\uDCD6\uD83D\uDCD6\uD83D\uDCD6 LEDGER \uD83D\uDCD6\uD83D\uDCD6\uD83D\uDCD6");
            System.out.println("A- All Transactions");
            System.out.println("D- Deposits");
            System.out.println("P- Payments");
            System.out.println("R- Reports");
            System.out.println("H- Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice){
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    showDeposits();
                    break;
                case "P":
                    showPayments();
                    break;
                case "R":
                    showReports();
                    break;
                case "H":
                    viewingLedger = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public static void  showDeposits(){
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : transactions){
            if (transaction.getAmount() > 0){
                deposits.add(transaction);
            }
        }
        System.out.println("\n\uD83D\uDCB0\uD83D\uDCB0\uD83D\uDCB0 DEPOSITS \uD83D\uDCB0\uD83D\uDCB0\uD83D\uDCB0");
        displayTransactions(deposits);
    }
    public static void  showPayments(){
        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : transactions){
            if (transaction.getAmount() < 0){
                payments.add(transaction);
            }
        }
        System.out.println("\n PAYMENTS ");
        displayTransactions(payments);
    }
    public static void  displayTransactions(
            ArrayList<Transaction> transactionList){
        if (transactionList.isEmpty()){
            System.out.println("No transactions found.");
            return;
        }
        ArrayList<Transaction> sortedList = new ArrayList<>(transactionList);
        sortedList.sort((first, second) ->{
            int dateOrder = second.getDate().compareTo(first.getDate());

            if (dateOrder != 0){
                return dateOrder;
            }
            return second.getTime().compareTo(first.getTime());
        });
        System.out.printf("%-12s %-10s %-25s %-25s %12s%n", "date", "Time", "Description", "Vendor", "Amount");
        System.out.println("____________________" + "____________________");

        for (Transaction transaction: sortedList){
            System.out.printf("%-12s %-10s %-25s %-25s %12.2f%n", transaction.getDate(),transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
        }
    }
    public static void showReports(){
        boolean viewingReports = true;

        while(viewingReports){
            System.out.println("\n REPORTS ");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");
            System.out.println("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1":
                    showDateReport(1);
                    break;
                case "2":
                    showDateReport(2);
                    break;
                case "3":
                    showDateReport(3);
                    break;
                case "4":
                    showDateReport(4);
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "6":
                    customeSearch();
                    break;
                case "0":
                    viewingReports = false;
                    break;
                default:
                    System.out.println("Invalid choice. try again.");
            }
        }
    }
    public static void showDateReport(int reportType){
        ArrayList<Transaction> results = new ArrayList<>();

        LocalDate today = LocalDate.now();
        YearMonth thisMonth = YearMonth.from(today);
        YearMonth lastMonth = thisMonth.minusMonths(1);

        for (Transaction transaction : transactions){
            LocalDate date = transaction.getDate();

            if (reportType ==1){
                if (YearMonth.from(date).equals(thisMonth) && !date.isAfter(today)){
                    results.add(transaction);
                }
            }else if (reportType == 2){
                if (YearMonth.from(date).equals(lastMonth)){
                    results.add(transaction);
                }
            }else if (reportType == 3){
                if (date.getYear() == today.getYear() && !date.isAfter(today)){
                    results.add(transaction);
                }
            }else if (reportType == 4){
                if (date.getYear() == today.getYear() - 1){
                    results.add(transaction);
                }
            }
        }
        System.out.println("\n DATE REPORT ");
        displayTransactions(results);
    }
    }
