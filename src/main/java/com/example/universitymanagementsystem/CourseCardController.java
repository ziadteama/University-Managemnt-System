package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;

public class CourseCardController {

    @FXML
    private Label courseNameLabel2;

    @FXML
    private Label courseNameLabel21;

    @FXML
    private Label courseNameLabel211;

    @FXML
    private Label courseNameLabel212;

    @FXML
    private Label courseNameLabel2111;

    @FXML
    private Label lectureTime;

    @FXML
    private Label tutorialTime;

    @FXML
    private Button addCourseButton;

    /**
     * Initializes the course details for this card.
     */
    public void setCourseDetails(String courseName, String courseCode, int creditHours, String lecturer, String tutor, String lectureTimeText, String tutorialTimeText) {
        courseNameLabel2.setText(courseName);
        courseNameLabel21.setText(courseCode);
        courseNameLabel212.setText(String.valueOf(creditHours));
        courseNameLabel211.setText(lecturer);
        courseNameLabel2111.setText(tutor);
        lectureTime.setText(lectureTimeText);
        tutorialTime.setText(tutorialTimeText);
    }

    /**
     * Creates a new course card dynamically.
     *
     * @param courseName   Course Name
     * @param courseCode   Course Code
     * @param lecturer     Lecturer Name
     * @param tutor        Tutor Name
     * @param lectureTime  Lecture Time
     * @param tutorialTime Tutorial Time
     * @return Node representing the course card
     */
    public static Node createCard(String courseName, String courseCode,int creditHours, String lecturer, String tutor, String lectureTime, String tutorialTime) {
        try {
            // Load the FXML file for the course card
            FXMLLoader loader = new FXMLLoader(CourseCardController.class.getResource("registerationcard.fxml"));
            Node cardNode = loader.load();

            // Get the controller and set course details
            CourseCardController controller = loader.getController();
            controller.setCourseDetails(courseName, courseCode,creditHours, lecturer, tutor, lectureTime, tutorialTime);

            return cardNode;

        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
            return null; // Return null if an error occurs
        }
    }

    @FXML
    private void handleAddCourse() {
        System.out.println("Course added: " + courseNameLabel2.getText());
    }
}
