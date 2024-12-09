package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CourseCardController {

    @FXML
    private Label courseNameLabel;

    @FXML
    private Label courseNameLabel2;

    @FXML
    private Label courseNameLabel1;

    @FXML
    private Label courseNameLabel21;

    @FXML
    private Label courseNameLabel11;

    @FXML
    private Label courseNameLabel211;

    @FXML
    private Label courseNameLabel111;

    @FXML
    private Label courseNameLabel2111;

    @FXML
    private Label lectureTimeLabel;

    @FXML
    private Label lectureTime;

    @FXML
    private Label tutorialTimeLabel;

    @FXML
    private Label tutorialTime;

    @FXML
    private Button addCourseButton;

    // Example method to initialize the course information
    public void initializeCourseDetails(String courseName, String courseCode, String lecturer, String tutor, String lectureTimeText, String tutorialTimeText) {
        // Set the course information dynamically
        courseNameLabel2.setText(courseName);
        courseNameLabel21.setText(courseCode);
        courseNameLabel211.setText(lecturer);
        courseNameLabel2111.setText(tutor);
        lectureTime.setText(lectureTimeText);
        tutorialTime.setText(tutorialTimeText);
    }

    @FXML
    private void handleAddCourse() {
        // Handle the logic to add the course to the student's selected list
        System.out.println("Course added: " + courseNameLabel2.getText());
    }
}

