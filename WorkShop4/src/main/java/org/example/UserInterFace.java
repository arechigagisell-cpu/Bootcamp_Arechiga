package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UserInterFace {


    private Dealership dealership;
    private DealershipFileManager fileManager;
    private Scanner scanner;

    public UserInterFace(){
        scanner = new Scanner(System.in);
        fileManager = new DealershipFileManager();
    }
    private void init() throws IOException{
        dealership = fileManager.getDealership();
    }
    public void display(){
        try{
            init();
        } catch (IOException ex) {
            System.out.println("Oops! could not load inventory: " + ex.getMessage());
            return;
        }
        System.out.println();
        System.out.println("====================================");
        System.out.println("   ♡ WELCOME TO THE PINK GARAGE ♡ ");
        System.out.println("      Mercedes-Benz Collection");
        System.out.println("====================================");
        System.out.println("Your dream ride is waiting! ♡");
        System.out.println();

        boolean running = true;
        while (running){
            showMenu();

            int choice = readInt("Enter your choice: ");

            switch(choice){
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehicleRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 99:
                    System.out.println();
                    System.out.println("♡Thank you for visiting The Pink Garage!");
                    System.out.println("Drive safe and stay fabulous!");
                    running = false;
                    break;
                default:
                    System.out.println("Oops! Please choose a valid option.");
            }
        }
    }
    private void  showMenu(){
        System.out.println();
        System.out.println("♡=====================================♡");
        System.out.println("        THE PINK GARAGE MENU");
        System.out.println("♡=====================================♡");

        System.out.println("1 ♡Shop by Price");
        System.out.println("2 ♡Find Your Dream Mercedes");
        System.out.println("3 ♡Shop by Year");
        System.out.println("4 ♡Pick Your Perfect Color");
        System.out.println("5 ♡Shop by Mileage");
        System.out.println("6 ♡Choose Your Vehicle Style");
        System.out.println("7 ♡Explore our Entire Collection");
        System.out.println("8 ♡Add a New Beauty");
        System.out.println("9 ♡Say Goodbye to a Vehicle");
        System.out.println("99 ♡Exit the Pink Garage");

        System.out.println("♡=====================================♡");
    }
    private void displayVehicles(List<Vehicle> vehicles){
        System.out.println();
        System.out.println("♡ YOUR MERCEDES-BENZ COLLECTION ♡");

        if (vehicles == null || vehicles.isEmpty()){
            System.out.println("Sorry, gorgeous! No vehicles found.");
            return;
        }
        System.out.println("We found " + vehicles.size() + " beautiful ride(s)!");
        System.out.println("----------------------------------------");

        for (Vehicle vehicle : vehicles){
            System.out.println(vehicle);
            System.out.println("-------------------------------------------");
        }
    }
    private void processGetByPriceRequest(){
        System.out.println();
        System.out.println("♡ LET'S FIND YOUR PERFECT PRICE ♡");

        BigDecimal min = readPrice("Minimum price: $");
        BigDecimal max = readPrice("Maximum price: $");

        displayVehicles(dealership.getVehicleByPrice(min, max));
    }
    private void processGetByMakeModelRequest(){
        System.out.println();
        System.out.println("♡ FIND YOUR DREAM MERCEDES ♡");

        String make = readText("Enter make (Mercedes-Benz): ");
        String model = readText("Enter model (C300, GLE350, etc.): ");

        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }
    private void processGetByYearRequest(){
        System.out.println();
        System.out.println("♡ FIND YOUR PERFECT YEAR ♡");

        int min = readInt("Enter starting year: ");
        int max = readInt("Enter ending year: ");

        displayVehicles(dealership.getVehiclesByYear(min, max));
    }
    private void processGetByColorRequest(){
        System.out.println();
        System.out.println("♡ PICK YOUR PERFECT COLOR ♡");

        String color = readText("Enter your favorite color: ");

        displayVehicles(dealership.getVehiclesByColor(color));
    }
    private void processGetByMileageRequest(){
        System.out.println();
        System.out.println("♡ FIND YOUR IDEAL MILEAGE ♡");

        int min = readInt("Minimum mileage: ");
        int max = readInt("Maximum mileage: ");

        displayVehicles(dealership.getVehiclesByMilage(min, max));
    }
    private void processGetByVehicleTypeRequest(){
        System.out.println();
        System.out.println("♡ CHOOSE YOUR VEHICLE STYLE ♡");

        String type = readText("Enter type ( car, truck, SUV, van): ");

        displayVehicles(dealership.getVehiclesByType(type));
    }
    private void processGetAllVehicleRequest(){
        System.out.println();
        System.out.println("♡ EXPLORE OUR LUXURY COLLECTION ♡");

        displayVehicles(dealership.getAllVehicles());
    }
    private void processAddVehicleRequest(){
        System.out.println();
        System.out.println("♡ LET'S ADD A NEW BEAUTY ♡");

        int vin = readNonNegativeInt("Enter VIN number: ");

        if (findVehicleByVin(vin) != null){
            System.out.println("Oops! That VIN already exists.");
            return;
        }
        int year = readNonNegativeInt("Enter year: ");
        String make = readText("Enter make: ");
        String model = readText("Enter model: ");
        String type = readText("Enter type (car, truck, SUV, van): ");
        String color = readText("Enter color: ");
        int mileage = readNonNegativeInt("Enter mileage: ");
        BigDecimal price = readNonNegativePrice("Enter price: $");

        Vehicle newVehicle = new Vehicle(vin,year,make,model,type,color,mileage,price);
        dealership.addVehicle(newVehicle);

        try{
            fileManager.saveDealership(dealership);

            System.out.println();
            System.out.println("♡ YAY! A NEW BEAUTY HAS ARRIVED! ♡");
            System.out.println("Vehicle added and saved successfully!");
        }catch (IOException ex){
            dealership.removeVehicle(newVehicle);
            System.out.println("Oops! could not save: " + ex.getMessage());
        }
    }
    private void processRemoveVehicleRequest(){
        System.out.println();
        System.out.println("♡ SAYING GOODBYE TO A BEAUTY ♡");

        int vin = readInt("Enter the VIN to remove: ");

        Vehicle vehicle = findVehicleByVin(vin);

        if (vehicle == null){
            System.out.println("Oops! We couldn't find that vehicle.");
            return;
        }
        System.out.println();
        System.out.println("Vehicle found:");
        System.out.println(vehicle);

        String answer = readText("Are you sure? (yes/no): ");

        if (answer.equalsIgnoreCase("yes")){
            dealership.removeVehicle(vehicle);

            try{
                fileManager.saveDealership(dealership);

                System.out.println();
                System.out.println("♡ GOODBYE, BEAUTIFUL RIDE! ♡");
                System.out.println("Vehicle removed and inevntory updated!");
            }catch (IOException ex){
                dealership.addVehicle(vehicle);
                System.out.println("Oops! Could not save: " + ex.getMessage());
            }
        }else{
            System.out.println("No worries! Your vehicle is staying.");
        }
    }
    private Vehicle findVehicleByVin(int vin){
        for (Vehicle vehicle : dealership.getAllVehicles()){
            if (vehicle.getVin() == vin){
                return vehicle;
            }
        }
        return null;
    }
    private String readText(String message){
        while (true){
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() && !input.contains("|")){
                return input;
            }
            System.out.println("Please enter valid text without |.");
        }
    }
    private int readInt(String message){
        while (true){
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            }catch (NumberFormatException ex){
                System.out.println("Oops! Please enter a vaild whole number.");
            }
        }
    }
    private int readNonNegativeInt(String message){
        while (true){
            int number = readInt(message);

            if (number >= 0){
                return number;
            }
            System.out.println("Please enter 0 or greater.");
        }
    }
    private BigDecimal readPrice(String message){
        while (true){
            System.out.print(message);
            try {
                return new BigDecimal(scanner.nextLine().trim());
            }catch (NumberFormatException ex){
                System.out.println("Please enter a valid price.");
            }
        }
    }
    private BigDecimal readNonNegativePrice(String message){
        while(true){
            BigDecimal price = readPrice(message);

            if (price.compareTo(BigDecimal.ZERO) >= 0){
                return price;
            }
            System.out.println("Please enter a price of 0 or greater.");
        }
    }
}
