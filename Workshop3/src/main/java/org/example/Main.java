package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner  scanner = new Scanner(System.in);
    static ArrayList<Product> storeProducts = FileManager.loadProducts("src/main/resources/products.csv");
    static ArrayList<Product> shoppingCart = new ArrayList<>();
    public static void main(String[] args) {

        boolean storeIsOpen = true;
        while (storeIsOpen) {
            System.out.println("\n******** WELCOME TO GIGIS SHOP ********");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String homeChoice = scanner.nextLine();

            if (homeChoice.equals("1")) {
                productMenu();
            } else if (homeChoice.equals("2")) {
                cartMenu();
            } else if (homeChoice.equals("3")) {
                System.out.println("Thank you for shopping!");
                storeIsOpen = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
    public static void productMenu(){
        boolean browsingProducts = true;

        while(browsingProducts){
            System.out.println("\n******** Product Menu ********");
            System.out.println("1. View ALL Products");
            System.out.println("2. Search by Name");
            System.out.println("3.Search by Price Range");
            System.out.println("4. Search by Department");
            System.out.println("5. Add Product to Cart");
            System.out.println("6. Back to Home");
            System.out.print("Enter your choice: ");

            String productChoice = scanner.nextLine();

            if (productChoice.equals("1")){
                displayProducts();
            }else if (productChoice.equals("2")){
                searchByName();
            }else if (productChoice.equals("3")){
                searchByPrice();
            }else if(productChoice.equals("4")){
                searchByDepartment();
            }else if(productChoice.equals("5")){
                addToCart();
            }else if (productChoice.equals("6")){
                browsingProducts = false;
            }else {
                System.out.println("Invalid choice.");
            }
        }
    }
    public static void displayProducts(){
        System.out.println("\n******** Available Products ********");

        if(storeProducts.isEmpty()){
            System.out.println("No products available");
        }else{
            for (Product currentProduct : storeProducts){
                currentProduct.displayProduct();
            }
        }
    }
    public static void  searchByName(){
        System.out.print("Enter product name: ");
        String searchName = scanner.nextLine();

        boolean productFound = false;

        for(Product currentProduct : storeProducts){
            if (currentProduct.getProductName().toLowerCase().contains(searchName.toLowerCase())){
                currentProduct.displayProduct();
                productFound = true;
            }
        }
        if(!productFound){
            System.out.println("No products found.");
        }
    }
    public static void searchByPrice(){
        try {
            System.out.println("Enter minimum price: ");
            double minimumPrice = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter the maximum price: ");
            double maximumPrice = Double.parseDouble(scanner.nextLine());

            if(minimumPrice<0 || maximumPrice<minimumPrice){
                System.out.println("please enter a valid price range.");
                return;
            }
            boolean priceFound = false;
            for (Product currentProduct : storeProducts){
                if(currentProduct.getPrice() >= minimumPrice && currentProduct.getPrice() <= maximumPrice){
                    currentProduct.displayProduct();
                    priceFound = true;
                }
            }
            if (!priceFound){
                System.out.println("No products in that price range.");
            }
        }catch (NumberFormatException ex){
            System.out.println("Please enter a valid number.");
        }
    }
    public static void  searchByDepartment(){
        System.out.print("Enter department: ");
        String searchDepartment = scanner.nextLine();

        boolean departmentFound = false;

        for(Product currentproduct : storeProducts){
            if (currentproduct.getDepartment().equalsIgnoreCase(searchDepartment)){
                currentproduct.displayProduct();
                departmentFound = true;
            }
        }
        if (!departmentFound){
            System.out.println("No products found.");
        }
    }
    public static void addToCart(){
        displayProducts();
        System.out.print("Enter product SKU: ");
        String selectedSku = scanner.nextLine();

        boolean itemFound = false;

        for(Product currentProduct : storeProducts){
            if (currentProduct.getSku().equalsIgnoreCase(selectedSku)){
                shoppingCart.add(currentProduct);
                System.out.println(currentProduct.getProductName() + " added to your cart!");

                itemFound = true;
                break;
            }
        }
        if (!itemFound){
            System.out.println("Product not found.");
        }
    }
    public static  void cartMenu(){

        boolean viewingCart = true;

        while(viewingCart){
            displayCart();

            System.out.println("\n******** Shopping Cart Menu ********");
            System.out.println("1. Remove Product");
            System.out.println("2. Check Out");
            System.out.println("3. Back to Home");
            System.out.print("Enter your choice: ");

            String cartChoice = scanner.nextLine();

            if (cartChoice.equals("1")){
                removeFromCart();
            }else if ( cartChoice.equals("2")){
                boolean checkoutComplete = checkOut();

                if (checkoutComplete){
                    viewingCart = false;
                }
            }else if (cartChoice.equals("3")){
                viewingCart = false;
            }else {
                System.out.println("Invalid choice.");
            }
        }
    }
    public static void  displayCart(){
        System.out.println("\n******** Your Shopping Cart ********");

        if(shoppingCart.isEmpty()){
            System.out.println("Your cart is empty");
            return;
        }
        double cartTotal = 0;
        ArrayList<String> displayedSkus = new ArrayList<>();

        for (Product cartProduct : shoppingCart){
            if(!displayedSkus.contains(cartProduct.getSku())){

                int quantity = 0;

                for (Product currentProduct : shoppingCart){
                    if (currentProduct.getSku().equalsIgnoreCase(cartProduct.getSku())){

                        quantity++;
                    }
                }
                double itemTotal = cartProduct.getPrice() * quantity;

                System.out.printf("%s | Quantity: %d | Price: $%.2f | Total: $%.2f%n", cartProduct.getProductName(), quantity, cartProduct.getPrice(), itemTotal);

                cartTotal += itemTotal;
                displayedSkus.add(cartProduct.getSku());
            }
        }
        System.out.printf("Cart Total: $%.2f%n", cartTotal);
    }
    public static void  removeFromCart(){
        if (shoppingCart.isEmpty()){
            System.out.println("Yupur cart is empty.");
            return;
        }
        System.out.print("Enter SKU to remove: ");
        String skuToRemove = scanner.nextLine();

        boolean itemRemoved = false;

        for (int itemIndex = 0; itemIndex < shoppingCart.size(); itemIndex++){
            Product cartProduct = shoppingCart.get(itemIndex);
            if (cartProduct.getSku().equalsIgnoreCase(skuToRemove)){
                shoppingCart.remove(itemIndex);

                System.out.println("One " + cartProduct.getProductName() + " removed from cart!");

                itemRemoved = true;
                break;
            }
        }
        if (!itemRemoved){
            System.out.println("Product not found in cart.");
        }
    }
    public static double calculateCartTotal(){
        double cartTotal = 0;
        for (Product cartProduct : shoppingCart){
            cartTotal += cartProduct.getPrice();
        }
        return cartTotal;
    }
    public static boolean checkOut(){
        if (shoppingCart.isEmpty()){
            System.out.println("Your cart is empty. Add products first.");
            return false;
        }
        double cartTotal = calculateCartTotal();
        System.out.printf("Your Total is: $%.2f%n", cartTotal);

        try {
            System.out.print("Enter cash payment: $");
            double amountPaid = Double.parseDouble(scanner.nextLine());

            if (!Double.isFinite(amountPaid) || amountPaid < cartTotal){
                System.out.println("Insufficient or invalid payment.");
                return false;
            }
            double changeGiven = amountPaid - cartTotal;
            System.out.printf("Your change is: $%.2f%n", changeGiven);

            printReceipt(cartTotal, amountPaid, changeGiven);
            shoppingCart.clear();
            System.out.println("Thank you for your purchase :)");
            return true;
        }catch (NumberFormatException ex){
            System.out.println("Please enter a valid payment amount.");
            return false;
        }
    }
    public static void  printReceipt(double cartTotal, double amountPaid, double changeGiven){
        LocalDateTime orderDate = LocalDateTime.now();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/DD/YYYY hh:mm a");
        DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
        StringBuilder receipt = new StringBuilder();
        receipt.append("\n******** Sales Receipt ********");
        receipt.append("Order Date: ").append(orderDate.format(dateFormat)).append("\n");
        receipt.append("******************************************\n");
        ArrayList<String> displayedSkus = new ArrayList<>();

        for(Product cartProduct : shoppingCart){
            if (!displayedSkus.contains(cartProduct.getSku())){
                int quantity = 0;
                for (Product currentProduct : shoppingCart){
                    if (currentProduct.getSku().equalsIgnoreCase(cartProduct.getSku())){
                        quantity++;
                    }
                }
                double itemTotal = cartProduct.getPrice() * quantity;
                receipt.append(String.format("%s | Qty: %d | $%.2f%n", cartProduct.getProductName(), quantity, itemTotal));
                displayedSkus.add(cartProduct.getSku());
            }
        }
        receipt.append("******************************************\n");
        receipt.append(String.format("Sales Total: $%.2f%n", cartTotal));
        receipt.append(String.format("Amount Paid: $%.2f%n", amountPaid));
        receipt.append(String.format("Change Given: $%.2f%n", changeGiven));
        receipt.append("Thank you for shopping!\n");
        receipt.append("******************************************\n");

        System.out.println(receipt);
        String filename = orderDate.format(fileFormat) + ".txt";
        FileManager.saveReceipt(receipt.toString(), filename);
    }
    }
