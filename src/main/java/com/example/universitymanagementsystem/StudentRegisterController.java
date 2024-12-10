package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentRegisterController {
    private Student student;
    private EnrollmentsDAO enrollmentsDAO;
    private List<CanEnroll> canEnroll;

    @FXML
    private FlowPane coursesContainer; // VBox to hold the course cards

    @FXML
    private TableView<CourseSchedule> scheduleTable; // The schedule table where courses will be added

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
        // Clear any existing cards
        coursesContainer.getChildren().clear();

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
                    course.getTutorialTime()
            );

            // Add the card to the container
            if (courseCard != null) {
                coursesContainer.getChildren().add(courseCard);
            }
        }
    }

    /**
     * Adds the selected course to the schedule table.
     */
    public void addCourseToSchedule(CourseSchedule courseSchedule) {
        // Add the course to the schedule (you can define the schedule logic)
        scheduleTable.getItems().add(courseSchedule);
    }

    /**
     * Removes the course card from the FlowPane after it is added to the schedule.
     */
    public void removeCourseCard(Node courseCard) {
        coursesContainer.getChildren().remove(courseCard);
    }
}
