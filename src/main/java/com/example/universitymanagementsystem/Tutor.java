package com.example.universitymanagementsystem;

import java.util.Date;

public class Tutor extends User {
private Student currentStudent;
    public Tutor(int userId, String name, String role, String major, String email, String gender, Date dot, String phoneNumber) {
        super(userId, name, role, major, email, gender, dot, phoneNumber);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Tutor");
    }
    public Student getCurrentStudent() {
        return currentStudent;
    }
    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }
}
