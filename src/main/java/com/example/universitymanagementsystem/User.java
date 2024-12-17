package com.example.universitymanagementsystem;

import java.util.Date;

public abstract class User {
    private int userId;
    private String name;
    private String email;
    private String gender;
    private Date dot;
    private String phoneNumber;
    private String role;
    private String major;
    public User(int userId, String name,String role, String major, String email, String gender, Date dot, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dot = dot;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.role = role;
    }

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public Date getDot() {
        return dot;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getRole() {
        return role;
    }

    public abstract void displayRole();
}
