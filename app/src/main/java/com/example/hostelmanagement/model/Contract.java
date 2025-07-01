package com.example.hostelmanagement.model;

public class Contract {
    private int id;
    private String contractNumber;
    private String customerName;
    private String phone;
    private String address;
    private String room;
    private int numPeople;
    private int roomPrice;
    private int deposit;
    private String startDate;
    private int duration;
    private int reminder;
    private int electricIndex;
    private int hasParking;
    private int bikeCount;
    private int hasInternet;
    private int hasLaundry;
    private String note;

    public Contract() {
    }

    public Contract( String contractNumber, String customerName, String phone, String address, String room, int numPeople,
                    int roomPrice, int deposit, String startDate, int duration, int reminder, int electricIndex,
                    int hasParking, int bikeCount, int hasInternet, int hasLaundry, String note) {
        this.contractNumber = contractNumber;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.room = room;
        this.numPeople = numPeople;
        this.roomPrice = roomPrice;
        this.deposit = deposit;
        this.startDate = startDate;
        this.duration = duration;
        this.reminder = reminder;
        this.electricIndex = electricIndex;
        this.hasParking = hasParking;
        this.bikeCount = bikeCount;
        this.hasInternet = hasInternet;
        this.hasLaundry = hasLaundry;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public int getElectricIndex() {
        return electricIndex;
    }

    public void setElectricIndex(int electricIndex) {
        this.electricIndex = electricIndex;
    }

    public int getHasParking() {
        return hasParking;
    }

    public void setHasParking(int hasParking) {
        this.hasParking = hasParking;
    }

    public int getBikeCount() {
        return bikeCount;
    }

    public void setBikeCount(int bikeCount) {
        this.bikeCount = bikeCount;
    }

    public int getHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(int hasInternet) {
        this.hasInternet = hasInternet;
    }

    public int getHasLaundry() {
        return hasLaundry;
    }

    public void setHasLaundry(int hasLaundry) {
        this.hasLaundry = hasLaundry;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
