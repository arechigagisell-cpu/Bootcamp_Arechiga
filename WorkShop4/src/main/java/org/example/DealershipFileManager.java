package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DealershipFileManager {
    private static final Path FILE_PATH = Path.of("inventory.csv");

    public Dealership getDealership() throws IOException{
        List<String> lines = Files.readAllLines(FILE_PATH);

        if (lines.isEmpty()){
            throw new IOException("inventory.csv is empty.");
        }
        String[] dealershipInfo = lines.get(0).split("\\|", -1);

        if(dealershipInfo.length !=3){
            throw new IOException("Invalid dealership information on line 1.");
        }
        Dealership dealership = new Dealership(
                dealershipInfo[0],
                dealershipInfo[1],
                dealershipInfo[2]
        );
        for (int i = 1; i < lines.size(); i++){
            String line = lines.get(i);
            if (line.isBlank()){
                continue;
            }
            String[] parts = line.split("\\|", -1);
            if (parts.length !=8){
                throw new IOException("invlaid vehicle data on line" + (i + 1));
            }
            try{
                int vin = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                String make = parts[2];
                String model = parts[3];
                String vehicleType = parts[4];
                String color = parts[5];
                int odometer = Integer.parseInt(parts[6]);
                BigDecimal price = new BigDecimal(parts[7]);

                Vehicle vehicle = new Vehicle(vin,year,make,model,vehicleType,color,odometer,price);
                dealership.addVehicle(vehicle);
            }catch (NumberFormatException ex){
                throw new IOException("Invalid number on line" + (i + 1), new Exception());
            }
        }
        return dealership;
    }
    public void saveDealership(Dealership dealership) throws IOException{
        StringBuilder contents = new StringBuilder();

        contents.append(dealership.getName()).append("|").append(dealership.getAddress()).append("|").append(dealership.getPhone()).append(System.lineSeparator());

        for (Vehicle vehicle : dealership.getAllVehicles()){
            contents.append(vehicle.getVin()).append("|").append(vehicle.getYear()).append("|").append(vehicle.getMake()).append("|").append(vehicle.getModel()).append("|").append(vehicle.getVehicleType()).append("|").append(vehicle.getColor()).append("|").append(vehicle.getOdometer()).append("|").append(vehicle.getPrice().toPlainString()).append(System.lineSeparator());
        }
        Files.writeString(FILE_PATH,contents.toString());
    }
}
