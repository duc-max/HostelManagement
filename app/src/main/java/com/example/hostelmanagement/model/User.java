package com.example.hostelmanagement.model;

public class User {
    private int id;
    private String username;
    private String phone;
    private String password;
    private String role;

    // Constructor mặc định
    public User() {}

    // Constructor đầy đủ
    public User(String username, String phone, String password, String role) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public User( String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
