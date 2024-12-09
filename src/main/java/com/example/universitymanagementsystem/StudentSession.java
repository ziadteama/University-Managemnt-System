package com.example.universitymanagementsystem;

public class StudentSession {
    private static Student currentStudent;

    // Private constructor to prevent instantiation
    private StudentSession() {}

    // Method to set the current student
    public static void setCurrentStudent(Student student) {
        currentStudent = student;
    }

    // Method to get the current student
    public static Student getCurrentStudent() {
        return currentStudent;
    }
}

