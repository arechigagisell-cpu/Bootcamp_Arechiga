package org.example;

import java.io.*;
import java.util.ArrayList;

public class ProfileManager {
    private static final String FILE_NAME = "profiles.csv";
    public static ArrayList<ClientProfile> loadProfiles(){
        ArrayList<ClientProfile> profiles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;

            while((line = reader.readLine()) != null){

                if(line.trim().isEmpty()){
                    continue;
                }
                String[] parts = line.split("\\|",-1);
                if(parts.length != 6){
                    continue;
                }
                try{
                    ClientProfile profile = new ClientProfile(Integer.parseInt(parts[0].trim()),parts[1], parts[2], parts[3], parts[4], parts[5]
                    );
                    profiles.add(profile);
                }catch (NumberFormatException ex){
                    System.out.println("Skipping invalid profile");
                }
            }
        }catch (IOException ex){
            System.out.println("Error loading profiles");
        }
        return profiles;
    }
    public static boolean saveProfile(ClientProfile profile){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true))){
            writer.write(profile.toCsv());
            writer.newLine();
            return true;
        }catch (IOException ex){
            System.out.println("Error saving profile");
            return false;
        }
    }
}
