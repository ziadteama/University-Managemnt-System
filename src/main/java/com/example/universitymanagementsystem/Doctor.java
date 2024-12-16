package com.example.universitymanagementsystem;

import java.util.Date;

public class Doctor extends User {

    public Doctor(int userId, String name, String role, String major, String email, String gender, Date dot, String phoneNumber) {
        super(userId, name, role, major, email, gender, dot, phoneNumber);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Doctor");
    }
}
