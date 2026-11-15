package org.example;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;


    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public List<Vehicle> getVehicleByPrice(BigDecimal min, BigDecimal max){
        List<Vehicle> matches = new ArrayList<>();

        for (Vehicle vehicle : inventory){
            if (vehicle.getPrice().compareTo(min) >= 0 && vehicle.getPrice().compareTo(max) <= 0){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public  List<Vehicle> getVehiclesByMakeModel(String make, String model){
        List<Vehicle> matches = new ArrayList<>();

        for (Vehicle vehicle : inventory){
            boolean sameMake = vehicle.getMake().equalsIgnoreCase(make);
            boolean sameModel = vehicle.getModel().equalsIgnoreCase(model);
            if (sameMake && sameModel){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public List<Vehicle> getVehiclesByYear(int min, int max){
        List<Vehicle> matches = new ArrayList<>();

        for (Vehicle vehicle : inventory){
            if (vehicle.getYear() >= min && vehicle.getYear() <= max){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public List<Vehicle> getVehiclesByColor(String color){
        List<Vehicle> matches = new ArrayList<>();

        for (Vehicle vehicle : inventory){
            if (vehicle.getColor().equalsIgnoreCase(color)){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public List<Vehicle> getVehiclesByMilage(int min, int max){
        List<Vehicle> matches = new ArrayList<>();

        for(Vehicle vehicle : inventory){
            if (vehicle.getOdometer() >= min && vehicle.getOdometer() <= max){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public List<Vehicle> getVehiclesByType(String vehicleType){
        List<Vehicle> matches = new ArrayList<>();

        for (Vehicle vehicle : inventory){
            if (vehicle.getVehicleType().equalsIgnoreCase(vehicleType)){
                matches.add(vehicle);
            }
        }
        return matches;
    }
    public List<Vehicle> getAllVehicles(){
        return new ArrayList<>(inventory);
    }
    public void addVehicle(Vehicle vehicle){
        inventory.add(vehicle);
    }
    public void removeVehicle(Vehicle vehicle){
        inventory.remove(vehicle);
    }
}
