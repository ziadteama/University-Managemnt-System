package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class CourseCardController {

    @FXML
    private Label courseNameLabel2;

    @FXML
    private Label courseNameLabel21;

    @FXML
    private Label courseNameLabel212;

    @FXML
    private Label courseNameLabel211;

    @FXML
    private Label courseNameLabel2111;

    @FXML
    private Label lectureTime;

    @FXML
    private Label tutorialTime;

    @FXML
    private Button addCourseButton;

    private StudentRegisterController studentRegisterController; // Reference to the controller

    private ScheduleDAO scheduleDAO;  // Reference to ScheduleDAO
    private String sectionId;

    /**
     * Initializes the course details for this card.
     */
    public void setCourseDetails(String sectionId, String courseName, String courseCode, int creditHours, String lecturer, String tutor, String lectureTimeText, String tutorialTimeText) {
        this.sectionId = sectionId;
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
    public static Node createCard(String sectionId, String courseName, String courseCode, int creditHours, String lecturer, String tutor, String lectureTime, String tutorialTime, StudentRegisterController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(CourseCardController.class.getResource("registerationcard.fxml"));
            Node cardNode = loader.load();

            CourseCardController controller = loader.getController();
            controller.setCourseDetails(sectionId, courseName, courseCode, creditHours, lecturer, tutor, lectureTime, tutorialTime);

            // Initialize the database connection and ScheduleDAO here
            Connection dbConnection = DataBaseConnection.getConnection(); // Get the DB connection
            controller.scheduleDAO = new ScheduleDAO(dbConnection); // Initialize ScheduleDAO

            // Link the parent controller (StudentRegisterController)
            controller.studentRegisterController = parentController;

            return cardNode;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle "Add Course" button click.
     * Adds the course to the schedule and removes the card.
     */
    @FXML
    private void handleAddCourse() {
        String sectionId = this.sectionId;

        // Get course schedules from ScheduleDAO using the sectionId
        List<CourseSchedule> scheduleItems = scheduleDAO.getScheduleBySectionId(sectionId);

        // If schedules are found
        System.out.println("Retrieved Schedule Items: " + scheduleItems);
        if (scheduleItems != null && !scheduleItems.isEmpty()) {
            try {
                // Load the RegisterTableViewController using its FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("registertableview.fxml")); // Make sure the FXML file name is correct
                loader.load();

                // Get the controller and update the schedule
                RegisterTableViewController tableController = loader.getController();
                boolean updateSuccessful = tableController.updateCells(scheduleItems);

                // Only remove the course card if the update was successful
                if (updateSuccessful) {
                    studentRegisterController.removeCourseCard((Node) addCourseButton.getParent());

                    // Show confirmation message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Course Added");
                    alert.setHeaderText("Courses Added Successfully");
                    alert.setContentText("The course(s) have been added to your schedule.");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Controller Loading Failed");
                alert.setContentText("Failed to load the course schedule controller. Please try again.");
                alert.showAndWait();
            }
        } else {
            // Show error if no course schedule found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Course Not Found");
            alert.setContentText("Unable to retrieve course schedule information.");
            alert.showAndWait();
        }
    }}



