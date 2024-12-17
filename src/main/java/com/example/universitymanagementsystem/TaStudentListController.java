package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TaStudentListController {
    @FXML
    private VBox studentVBox; // VBox from FXML where the list will be added dynamically


    private StudentDAO studentDAO = new StudentDAO(DataBaseConnection.getConnection());

    public TaStudentListController() throws Exception {

    }

    public void setStudentList(List<Student> students) {
        studentVBox.getChildren().clear(); // Clear existing items (if any)

        for (Student student : students) {
            // Create a label for the student
            Label studentLabel = new Label(student.getUserId() + " - " + student.getName());

            // Create a "Register" button for each student
            Button registerButton = new Button("Register");

            // Set the action when the button is clicked
            registerButton.setOnAction(event -> {
                handleRegisterClick(student.getUserId());
            });

            // Combine the label and button into an HBox
            HBox studentRow = new HBox(10); // Spacing between elements
            studentRow.getChildren().addAll(studentLabel, registerButton);

            // Add the HBox to the VBox
            studentVBox.getChildren().add(studentRow);
        }
    }

    // Method to handle register button click
    private void handleRegisterClick(int studentId) {
        try {
            // Load the registration FXML and pass the studentId to the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/universitymanagementsystem/taRegistration.fxml"));
            Student student= studentDAO.getStudentById(studentId);
            loader.setController(new TaRegistrationController(student));

            // Load the scene
            Node root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) studentVBox.getScene().getWindow();
            stage.setScene(new Scene((Parent) root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
