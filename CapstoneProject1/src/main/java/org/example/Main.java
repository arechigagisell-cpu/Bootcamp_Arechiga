package org.example;

import jdk.swing.interop.SwingInterOpUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = FileManager.loadTransactions();
    public static void main(String[] args) {
        boolean running = true;

        while(running){
            System.out.println("\n\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC36  HAPPY PAWS VETERINARY LEDGER  \uD83D\uDC36\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E  ");
            System.out.println("D) \uD83D\uDCB0 Add some Funds(Deposit)");
            System.out.println("P) \uD83D\uDCB8 Pay the Bills (Debit)");
            System.out.println("L) \uD83D\uDCD6  Peek at the paw Ledger");
            System.out.println("C) \uD83D\uDC36 Customer and Pet Profiles");
            System.out.println("A) \uD83D\uDCC5 Pa-some Appointment Center");
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
                case "C":
                    customerProfileMenu();
                    break;
                case "A":
                    showAppointments();
                    break;
                case "X":
                    System.out.println("\uD83D\uDC3E Thank you for visiting Happy Paws!");
                    System.out.println("Keep those tails wagging!");
                    running = false;
                    break;
                default:
                    System.out.println("\uD83D\uDC3E Oops! Wrong paw!");
                    System.out.println("Please choose D, P, L, C, A or X.");
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
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

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
        System.out.println("\n\uD83D\uDCB3✨\uD83D\uDCB3✨ PAYMENTS ✨\uD83D\uDCB3✨\uD83D\uDCB3 ");
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
        System.out.println("-".repeat(93));
        System.out.printf("%-12s %-10s %-26s %-28s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");

        System.out.println("-".repeat(93));
        for (Transaction transaction: sortedList){
            System.out.printf("%-12s %-10s %-26.26s %-28.28s %12.2f%n", transaction.getDate(),transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
        }
    }
    public static void showReports(){
        boolean viewingReports = true;

        while(viewingReports){
            System.out.println("\n\uD83D\uDC3E\uD83D\uDCC8\uD83D\uDC3E\uD83D\uDCC8 REPORTS \uD83D\uDCC8\uD83D\uDC3E\uD83D\uDCC8\uD83D\uDC3E");
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
                    customSearch();
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
        System.out.println("\n\uD83D\uDCC5\uD83D\uDC3E\uD83D\uDCC5\uD83D\uDC3E\uD83D\uDCC5\uD83D\uDC3E DATE REPORT \uD83D\uDC3E\uD83D\uDCC5\uD83D\uDC3E\uD83D\uDCC5\uD83D\uDC3E\uD83D\uDCC5 ");
        displayTransactions(results);
    }
    public static void  searchByVendor(){
        System.out.print("Enter vendor name: ");
        String vendorName = scanner.nextLine().trim();

        ArrayList<Transaction> results = new ArrayList<>();

        for (Transaction transaction : transactions){
            if (transaction.getVendor().toLowerCase().contains(vendorName.toLowerCase())){
                results.add(transaction);
            }
        }
        System.out.println("\n\uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E VENDOR SEARCH \uD83D\uDC3E\uD83D\uDC3E\uD83D\uDC3E");
        displayTransactions(results);
    }
    public static void  customSearch(){
        System.out.println("\n\uD83D\uDD0D\uD83D\uDC36 \uD83D\uDD0D\uD83D\uDC36 \uD83D\uDD0D\uD83D\uDC36 CUSTOM SEARCH \uD83D\uDC36\uD83D\uDD0D\uD83D\uDC36\uD83D\uDD0D\uD83D\uDC36\uD83D\uDD0D ");
        System.out.println("\uD83E\uDDB4\uD83E\uDDB4\uD83E\uDDB4 leave a field blank to skip it.\uD83E\uDDB4\uD83E\uDDB4\uD83E\uDDB4");

        LocalDate startDate = readOptionalDate("Start date (YYYY-MM-DD): ");
        LocalDate endDate = readOptionalDate("End date (YYYY-MM-DD): ");

        System.out.print("Description: ");
        String description = scanner.nextLine().trim().toLowerCase();
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        Double amount = readOptionalAmount("Amount: ");

        if (startDate != null && endDate != null && startDate.isAfter(endDate)){
            System.out.println("Start date cannot be after end date");
            return;
        }
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction transaction : transactions){
            boolean matches = true;

            if (startDate != null && transaction.getDate().isBefore(startDate)){
                matches = false;
            }
            if (endDate != null && transaction.getDate().isAfter(endDate)){
                matches = false;
            }
            if (!description.isEmpty() && !transaction.getDescription().toLowerCase().contains(description)){
                matches = false;
            }
            if (!vendor.isEmpty() && !transaction.getVendor().toLowerCase().contains(vendor)){
                matches = false;
            }
            if (amount != null){
                if(Math.abs(Math.abs(transaction.getAmount()) - Math.abs(amount)) > 0.001){
                    matches = false;
            }
            }
            if (matches){
                results.add(transaction);
            }
        }
        System.out.println("\n\uD83E\uDDB4\uD83D\uDC36 \uD83E\uDDB4\uD83D\uDC36  CUSTOM SEARCH RESULTS \uD83D\uDC36\uD83E\uDDB4\uD83D\uDC36\uD83E\uDDB4");
        displayTransactions(results);
    }
    public static double readAmount(String message){
        while(true){
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                double amount = Double.parseDouble(input);
                if (Double.isFinite(amount) && amount >0){
                    return amount;
                }
                System.out.println("Enter an amount greater than zero.");
            }catch (NumberFormatException ex){
                System.out.println("Please enter a valid number.");
            }
        }
    }
    public static LocalDate readOptionalDate(String message){
        while(true){
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()){
                return null;
            }
            try {
                return LocalDate.parse(input);
            }catch (Exception ex){
                System.out.println("Use the format YYYY-MM-DD.");
            }
        }
    }
    public static Double readOptionalAmount(String message){
        while(true){
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()){
                return null;
            }
            try {
                double amount = Double.parseDouble(input);
                if (Double.isFinite(amount)){
                    return amount;
                }
                System.out.println("Enter a valid amount.");
            }catch (NumberFormatException ex){
                System.out.println("Please enter a valid number.");
            }
        }
    }
    public static void customerProfileMenu(){
        boolean viewingProfiles = true;

        while(viewingProfiles){
            System.out.println("\n\uD83D\uDC36\uD83D\uDC36\uD83D\uDC36 CUSTOMER AND PET PROFILES \uD83D\uDC36\uD83D\uDC36\uD83D\uDC36");
            System.out.println("1) Register a Furry friend");
            System.out.println("2) View All Profiles");
            System.out.println("3) Sniff Out a Patient");
            System.out.println("0) Back to Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch(choice){
                case "1":
                    creatClientProfile();
                    break;
                case "2":
                    viewClientProfiles();
                    break;
                case "3":
                    searchClientProfile();
                    break;
                case "0":
                    viewingProfiles = false;
                    break;
                default:
                    System.out.println("\uD83D\uDC3E Invalid profile option.");
            }
        }
    }
    }
