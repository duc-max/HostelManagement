package com.example.hostelmanagement.model;

public class Room {
    private int id;
    private int hostelId;
    private int price;
    private String roomName;
    private boolean status;

    public Room( int hostelId, int price, String roomName, boolean status) {
        this.hostelId = hostelId;
        this.price = price;
        this.roomName = roomName;
        this.status = status;
    }

    public Room() {
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
