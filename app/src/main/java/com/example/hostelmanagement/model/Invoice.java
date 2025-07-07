package com.example.hostelmanagement.model;

public class Invoice {
    private int id;
    private int roomId;
    private int total;
    private int contractId;

    // Constructor
    public Invoice(int id, int roomId, int total, int contractId) {
        this.id = id;
        this.roomId = roomId;
        this.total = total;
        this.contractId = contractId;
    }

    // Default constructor
    public Invoice() {
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for roomId
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // Getter and Setter for total
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    // Getter and Setter for contractId
    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
}
