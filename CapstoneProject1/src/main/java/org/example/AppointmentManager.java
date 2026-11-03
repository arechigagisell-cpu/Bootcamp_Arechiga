package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AppointmentManager {
    private static final String FILE_NAME = "appointments.csv";
    public static ArrayList<Appointment> loadingAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;

            while((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                if (parts.length != 7) {
                    continue;
                }
                try {
                    Appointment appointment = new Appointment(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()), parts[2], parts[3], parts[4], LocalDate.parse(parts[5].trim()), LocalTime.parse(parts[6].trim()));

                    appointments.add(appointment);
                } catch (Exception ex) {
                    System.out.println("Skipping invalid appointment");
                }
            }
        }catch (FileNotFoundException ex){

        }catch (IOException ex){
            System.out.println("Error loading appointments");
        }
            return appointments;
    }
    public static boolean saveAppointments(ArrayList<Appointment> appointments){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
            for (Appointment appointment : appointments){
                writer.write(appointment.toCsv());
                writer.newLine();
            }
            return true;
        }catch (IOException ex){
            System.out.println("Error saving appointments");
            return false;
        }
    }
}
