package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaStudentListController {
    @FXML
    private VBox studentVBox; // VBox from FXML where the list will be added dynamically

    public void setStudentList(List<Student> students) {
        studentVBox.getChildren().clear(); // Clear existing items (if any)

        for (Student student : students) {
            // Create a new label for each student and add it to the VBox
            Label studentLabel = new Label(student.getUserId() + " - " + student.getName());
            studentVBox.getChildren().add(studentLabel);
        }
    }
}
