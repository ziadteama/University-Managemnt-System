package com.example.universitymanagementsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
            Node courseCard = CourseCardController.createCard(
                    course.getSectionId(),
                    course.getCourseName(),
                    course.getCourseCode(),
                    course.getCreditHours(),
                    course.getLecturerName(),
                    course.getTutorName(),
                    course.getLectureTime(),
                    course.getTutorialTime(),
                    this
            );

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
        // Iterate over all course cards
        for (Node card : courseCards) {
            CourseCardController cardController = (CourseCardController) card.getUserData();
            if (cardController != null && cardController.getCourseName().equals(courseName)) {
                // Remove the card from the parent container
                removeCourseCard(card);
            }
        }
    }

    /**
     * Handles the submit button click event to enroll in selected courses.
     */
    @FXML
    private void handleSubmitButtonClick() {
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
        } else {
            System.out.println("No courses selected for enrollment.");
        }
    }
}