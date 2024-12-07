package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MenuController {

    private Student currentStudent; // This holds the logged-in student object

    @FXML
    private Button logoutButton;

    // Initialize the controller with the student object (passed from LoginController)
    public void initialize(Student student) {
        this.currentStudent = student;
    }

    @FXML
    private void handleLogout() {
        // Step 1: Destruct (clear) the student object
        currentStudent = null;

        // Step 2: Redirect the user back to the login scene
        try {
            // Load the login scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene (login screen)
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene loginScene = new Scene(root);
            stage.setScene(loginScene);
            stage.setTitle("Login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
