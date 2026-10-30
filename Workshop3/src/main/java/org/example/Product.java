package org.example;

public class Product {
    private String sku;
    private String productName;
    private double price;
    private String department;

    public Product(String sku, String productName, double price, String department) {
        this.sku = sku;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    public String getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public String getDepartment() {
        return department;
    }
    public void displayProduct(){
        System.out.printf("SKU: %s | Products %s | Price: $%.2f | Department: %s%n", sku, productName, price, department);
    }
}
