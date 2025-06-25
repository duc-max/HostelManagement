package com.example.hostelmanagement.model;


public class Hostel {
    // Thông tin phòng
    private int id;
    private String address;
    private String owner;
    private String phone;

    // Các loại phí
    private int electricPrice;   // VNĐ/KWH
    private int waterPrice;      // VNĐ/phòng
    private int vehiclePrice;    // VNĐ/chiếc
    private int internetPrice;   // VNĐ/phòng
    private int laundryPrice;    // VNĐ/phòng
    private int elevatorPrice;   // VNĐ/phòng
    private int tvPrice;         // VNĐ/phòng
    private int garbagePrice;    // VNĐ/phòng
    private int servicePrice;    // VNĐ/phòng

    // Thông tin chuyển khoản
    private String bankOwner;
    private String bankName;
    private String bankAccount;

    public Hostel() {
    }

    public Hostel(String address, String owner, String phone,
                    int electricPrice, int waterPrice, int vehiclePrice,
                    int internetPrice, int laundryPrice, int elevatorPrice,
                    int tvPrice, int garbagePrice, int servicePrice,
                    String bankOwner, String bankName, String bankAccount) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.electricPrice = electricPrice;
        this.waterPrice = waterPrice;
        this.vehiclePrice = vehiclePrice;
        this.internetPrice = internetPrice;
        this.laundryPrice = laundryPrice;
        this.elevatorPrice = elevatorPrice;
        this.tvPrice = tvPrice;
        this.garbagePrice = garbagePrice;
        this.servicePrice = servicePrice;
        this.bankOwner = bankOwner;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
    }

    // ===== GETTER & SETTER =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getElectricPrice() { return electricPrice; }
    public void setElectricPrice(int electricPrice) { this.electricPrice = electricPrice; }

    public int getWaterPrice() { return waterPrice; }
    public void setWaterPrice(int waterPrice) { this.waterPrice = waterPrice; }

    public int getVehiclePrice() { return vehiclePrice; }
    public void setVehiclePrice(int vehiclePrice) { this.vehiclePrice = vehiclePrice; }

    public int getInternetPrice() { return internetPrice; }
    public void setInternetPrice(int internetPrice) { this.internetPrice = internetPrice; }

    public int getLaundryPrice() { return laundryPrice; }
    public void setLaundryPrice(int laundryPrice) { this.laundryPrice = laundryPrice; }

    public int getElevatorPrice() { return elevatorPrice; }
    public void setElevatorPrice(int elevatorPrice) { this.elevatorPrice = elevatorPrice; }

    public int getTvPrice() { return tvPrice; }
    public void setTvPrice(int tvPrice) { this.tvPrice = tvPrice; }

    public int getGarbagePrice() { return garbagePrice; }
    public void setGarbagePrice(int garbagePrice) { this.garbagePrice = garbagePrice; }

    public int getServicePrice() { return servicePrice; }
    public void setServicePrice(int servicePrice) { this.servicePrice = servicePrice; }

    public String getBankOwner() { return bankOwner; }
    public void setBankOwner(String bankOwner) { this.bankOwner = bankOwner; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
}
