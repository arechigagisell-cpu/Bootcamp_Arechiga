package org.example;

public class ClientProfile {
    private int clientId;
    private String ownerName;
    private String phoneNumber;
    private String petName;
    private String petType;
    private String petBreed;

    public ClientProfile(int clientId, String ownerName, String phoneNumber, String petName , String petType, String petBreed) {
        this.clientId = clientId;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.petName = petName;
        this.petType = petType;
        this.petBreed = petBreed;
    }

    public int getClientId() {
        return clientId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetType() {
        return petType;
    }

    public String getPetBreed() {
        return petBreed;
    }
    public String toCsv(){
        return clientId + "|" + ownerName + "|" + phoneNumber + "|" + petName + "|" + petType + "|" + petBreed;
    }
}
