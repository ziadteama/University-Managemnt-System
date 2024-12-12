package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private StudentDAO studentDAO;

    public LoginController() {
        try {
            // Initialize the database connection
            Connection dbConnection = DataBaseConnection.getConnection();
            this.studentDAO = new StudentDAO(dbConnection); // Pass the connection to DAO
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database connection failed.");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();

        // Convert userId to integer (since it is an int in the DB)
        int userIdInt = Integer.parseInt(userId);

        // Check credentials using the DAO method
        boolean isAuthentic = studentDAO.checkCredentials(userIdInt, password);

        if (isAuthentic) {
            Student student = studentDAO.getStudentById(userIdInt);
            List<Semester> semesters = studentDAO.getSemestersTaken(userIdInt);
            student.setSemesters(semesters);
            for(Semester s : student.getSemesters()) {
                System.out.println("current semesters: "+s);
            }
            if (student != null) {
                // Set the student in the session
                StudentSession.setCurrentStudent(student);
                System.out.println("Student object fetched: " + student.getName()); // Debugging log

                // Load the student menu scene
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("studentmenu.fxml"));
                    Parent root = loader.load();

                    // Pass the current student to the StudentMenuController
                    StudentMenuController studentMenuController = loader.getController();

                    // The controller can now access the student from the session
                    studentMenuController.initialize(); // This will be automatically called

                    // Redirect to the student dashboard/menu
                    Stage stage = (Stage) userIdField.getScene().getWindow();
                    Scene dashboardScene = new Scene(root);
                    stage.setScene(dashboardScene);
                    stage.setTitle("Welcome to the Dashboard");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Student data not found!");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Invalid credentials! Please try again.");
            errorLabel.setVisible(true);
        }
    }


    private void redirectToDashboard() {
        try {
            // Load the next scene (e.g., Dashboard)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("studentmenu.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) userIdField.getScene().getWindow();
            Scene dashboardScene = new Scene(root);
            stage.setScene(dashboardScene);
            stage.setTitle("Welcome to the Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
