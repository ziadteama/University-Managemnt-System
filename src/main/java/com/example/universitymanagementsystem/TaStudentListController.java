package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaStudentListController {
    @FXML
    private VBox studentVBox; // VBox from FXML where the list will be added dynamically

    public void setStudentList(List<Student> students) {
        studentVBox.getChildren().clear(); // Clear existing items (if any)

        for (Student student : students) {
            // Create a label for the student
            Label studentLabel = new Label(student.getUserId() + " - " + student.getName());

            // Create a "Register" button for each student
            Button registerButton = new Button("Register");

            // Set the action when the button is clicked
            registerButton.setOnAction(event -> {
                handleRegisterClick(student);
            });

            // Combine the label and button into an HBox
            HBox studentRow = new HBox(10); // Spacing between elements
            studentRow.getChildren().addAll(studentLabel, registerButton);

            // Add the HBox to the VBox
            studentVBox.getChildren().add(studentRow);
        }
    }

    // Method to handle register button click
    private void handleRegisterClick(Student student) {
        System.out.println("Register button clicked for Student ID: " + student.getUserId());
    }}
