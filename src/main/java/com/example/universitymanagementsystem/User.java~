package com.example.universitymanagementsystem;

import java.util.Date;

public class User {
    private int userId;            // user_id (primary key, auto-incremented)
    private String name;           // name
    private String email;          // email
    private String role;           // role (enum: 'student', 'ta', 'dr')
    private String major;          // major
    private String gender;         // gender (enum: 'male', 'female', 'other')
    private Date dateOfEntry; // dot (date of transaction)
    private String phoneNumber;    // phone_number
    private String password;       // pass

    // Constructor
    public User(int userId, String name, String email, String role, String major, String gender,
                Date dateOfEntry, String phoneNumber, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.major = major;
        this.gender = gender;
        this.dateOfEntry = dateOfEntry;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfTransaction() {
        return dateOfEntry;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfEntry = dateOfTransaction;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", major='" + major + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfTransaction=" + dateOfEntry +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

