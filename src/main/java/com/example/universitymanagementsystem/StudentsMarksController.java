package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.List;

public class StudentsMarksController
{
    private StudentsMarksController parentController;
    @FXML
    private HBox semesterTabsContainer;

    // Method to initialize the semester tabs
    public void initialize() {
        // Example list of semesters
        List<String> semesters = StudentSession.getCurrentStudent().getSemestersString();

        // Create and add buttons for each semester
        for (String semester : semesters) {
            Node semesterTab = StudentSemestersTab.createButton(semester, parentController); // Pass the parent controller if needed
            semesterTabsContainer.getChildren().add(semesterTab);
        }
    }
}
