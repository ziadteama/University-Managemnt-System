package com.example.universitymanagementsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentRegisterController {
    private Student student;
    private EnrollmentsDAO enrollmentsDAO;
    private List<CanEnroll> canEnroll;
    private List<Node> courseCards;

    @FXML
    private FlowPane coursesContainer; // FlowPane to hold the course cards

    @FXML
    private Button submitButton;

    public StudentRegisterController() {
        try {
            // Initialize the database connection
            Connection dbConnection = DataBaseConnection.getConnection();
            this.enrollmentsDAO = new EnrollmentsDAO(dbConnection); // Pass the connection to DAO
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the `canEnroll` list and renders course cards dynamically.
     */
    @FXML
    public void initialize() {
        student = StudentSession.getCurrentStudent();
        canEnroll = enrollmentsDAO.getCanEnroll(student.getUserId());

        // Print the canEnroll list to check if data is being retrieved
        System.out.println("Courses the student can enroll in:");
        if (canEnroll != null && !canEnroll.isEmpty()) {
            for (CanEnroll course : canEnroll) {
                System.out.println(course);
            }
        } else {
            System.out.println("No courses available for enrollment.");
        }

        // Render the course cards dynamically
        renderCourseCards();
    }

    /**
     * Renders course cards dynamically using the `createCard` function in `CourseCardController`.
     */
    private void renderCourseCards() {
        // Check if coursesContainer is initialized
        if (coursesContainer == null) {
            throw new IllegalStateException("coursesContainer is not initialized. Please check the FXML file.");
        }

        // Clear any existing cards
        coursesContainer.getChildren().clear();

        courseCards = new ArrayList<>();

        for (CanEnroll course : canEnroll) {
            // Create a card dynamically
            Node courseCard = CourseCardController.createCard(course.getSectionId(), course.getCourseName(), course.getCourseCode(), course.getCreditHours(), course.getLecturerName(), course.getTutorName(), course.getLectureTime(), course.getTutorialTime(), this);

            // Add the card to the container
            if (courseCard != null) {
                coursesContainer.getChildren().add(courseCard);
                courseCards.add(courseCard);
            }
        }
    }

    /**
     * Removes the course card from the FlowPane after it is added to the schedule.
     */
    public void removeCourseCard(Node courseCard) {
        if (coursesContainer != null) {
            coursesContainer.getChildren().remove(courseCard);
            courseCards.remove(courseCard);
        } else {
            throw new IllegalStateException("coursesContainer is not initialized. Please check the FXML file and controller setup.");
        }
    }

    /**
     * Removes other cards with the same course name.
     */
    public void removeOtherCardsWithSameCourseName(String courseName) {
        // Temporary list to store cards to be removed
        List<Node> toRemove = new ArrayList<>();

        // First, find all the cards to be removed
        for (Node card : courseCards) {
            CourseCardController cardController = (CourseCardController) card.getUserData();
            if (cardController != null && cardController.getCourseName().equals(courseName)) {
                toRemove.add(card); // Add to the removal list
            }
        }

        // Then, remove the cards outside the iteration loop
        for (Node card : toRemove) {
            removeCourseCard(card);
        }
    }

    /**
     * Handles the submit button click event to enroll in selected courses.
     */
    @FXML
    private void handleSubmitButtonClick() {
        // Check if the student is already enrolled in a course
        if (enrollmentsDAO.isAlreadyEnrolled(student.getUserId())) {
            // Show an alert to the student
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Already Enrolled");
            alert.setHeaderText("Already Enrolled");
            alert.setContentText("You are already enrolled in a course. Please head to your TA to discuss further enrollment options.");
            alert.showAndWait();
            return;
        }

        List<String> selectedSectionIds = new ArrayList<>();
        ObservableList<RowData> rowData = ScheduleData.getInstance().getScheduleList();
        if (rowData != null && !rowData.isEmpty()) {
            Set<String> uniqueSectionIds = new HashSet<>(); // Use a Set to prevent duplicates

            for (RowData row : rowData) {
                String sectionId = row.getSectionId(); // Assuming rowData objects have a method to get section IDs
                if (sectionId != null) {
                    uniqueSectionIds.add(sectionId); // Duplicates will be automatically filtered
                }
            }

            // If you need it as a List
            selectedSectionIds = new ArrayList<>(uniqueSectionIds);
        }

        if (!selectedSectionIds.isEmpty()) {
            System.out.println("Selected Section IDs for enrollment: " + selectedSectionIds);
            enrollmentsDAO.insertEnrollments(student.getUserId(), selectedSectionIds, java.sql.Date.valueOf(java.time.LocalDate.now()), student.getCurrentSemester());
            // Show an alert for successful enrollment
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enrollment Successful");
            alert.setHeaderText("Enrolled Successfully");
            alert.setContentText("You have been enrolled in the selected courses.");
            alert.showAndWait();
        } else {
            System.out.println("No courses selected for enrollment.");
        }
    }
    @FXML
    private void handleClearSchedule() {
        // Get the current schedule
        ObservableList<RowData> schedule = ScheduleData.getInstance().getScheduleList();

        // Clear the periods for each row
        for (RowData row : schedule) {
            row.setPeriod1("");
            row.setPeriod2("");
            row.setPeriod3("");
            row.setPeriod4("");
            row.setPeriod5("");
            row.setPeriod6("");
        }

        // Re-render the course cards
        renderCourseCards();

        // Show an alert for successful clear
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Schedule Cleared");
        alert.setHeaderText("Schedule Cleared");
        alert.setContentText("Your schedule has been cleared.");
        alert.showAndWait();
    }}