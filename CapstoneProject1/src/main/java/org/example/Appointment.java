package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private int clientId;
    private String petName;
    private String ownerName;
    private String reason;
    private LocalDate date;
    private LocalTime time;

    public Appointment(int id, int clientId, String petName, String ownerName, String reason, LocalDate date, LocalTime time) {
        this.id = id;
        this.clientId = clientId;
        this.petName = petName;
        this.ownerName = ownerName;
        this.reason = reason;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPetName() {
        return petName;
    }

    public int getClientId() {
        return clientId;
    }
    public String toCsv(){
        return id + "|" + clientId + "|" + petName + "|" + ownerName + "|" + reason + "|" + date + "|" + time;
    }
}
