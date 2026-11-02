package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "transactions.csv";
    public static ArrayList<Transaction> loadTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;

            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                if(parts.length != 5){
                    continue;
                }
                if (parts[0].equalsIgnoreCase("date")){
                    continue;
                }
                try {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim());
                    String description = parts[2].trim();
                    String vendor = parts[3].trim();
                    double amount = Double.parseDouble(parts[4].trim());

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }catch (Exception ex){
                    System.out.println("Skipping invalid transaction: " + line);
                }
            }
        }catch (FileNotFoundException ex){
            System.out.println("No transactions file found yet.");
            System.out.println("A new file will be created when you save.");
        }catch (IOException ex){
            System.out.println("Error reading transactions: " + ex.getMessage());
        }
        return transactions;
    }
    public static boolean saveTransaction(Transaction transaction){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))){
            writer.write(transaction.toCsv());
            writer.newLine();

            return true;
        }catch (IOException ex){
            System.out.println("Error saving transaction: " + ex.getMessage());
            return false;
        }
    }
}
