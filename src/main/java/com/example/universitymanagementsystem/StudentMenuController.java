package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentMenuController {
    private Student student;

    @FXML
    private Label helloUser;

    private StudentDAO studentDAO;

    public StudentMenuController() {
        try {
            // Initialize the database connection
            Connection dbConnection = DataBaseConnection.getConnection();
            this.studentDAO = new StudentDAO(dbConnection); // Pass the connection to DAO
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        student = StudentSession.getCurrentStudent();
        if (student != null) {
            helloUser.setText("Hello, " + student.getName());
        } else {
            System.out.println("Student object is null!"); // Debugging log
        }

    }

    public void setStudent(Student student) {
        this.student = student;
        System.out.println("Student object set in StudentMenuController: " + student.getName()); // Debugging log
        initialize();  // Ensure the label is updated after the student is set
    }
}
