package org.example;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<Product> loadProducts(String filename) {
        ArrayList<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] productDetails = line.split("\\|");
                if (productDetails.length == 4) {
                    String sku = productDetails[0].trim();
                    String productName = productDetails[1].trim();
                    double price = Double.parseDouble(productDetails[2].trim());
                    String department = productDetails[3].trim();

                    Product newProduct = new Product(sku, productName, price, department);
                    products.add(newProduct);
                }
            }
        }catch (IOException ex){
            System.out.println("Error loading products: " + ex.getMessage());
        }catch (NumberFormatException ex){
            System.out.println("Invalid product price in CSV file.");
        }
        return products;
    }
    public static void saveReceipt(String receipt, String filename){
        File receiptFolder = new File("Receipts");

        if (!receiptFolder.exists()){
            receiptFolder.mkdirs();
        }
        File receiptFile = new File(receiptFolder, filename);

        try(PrintWriter writer = new PrintWriter(receiptFile)){
            writer.print(receipt);

            System.out.println("Receipt saved successfully!");
            System.out.println("File: " + receiptFile.getPath());
        }catch (FileNotFoundException ex){
            System.out.println("Error saving receipt: " + ex.getMessage());
        }
    }
}