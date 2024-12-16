package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.util.List;

public class StudentsMarksSceneController {
    @FXML
    private ComboBox<Semester> semesterComboBox;

    @FXML
    private VBox studentMarksContainer;

    private Connection databaseConnection;

    public void initialize() throws Exception {
        // Initialize database connection
        databaseConnection = DataBaseConnection.getConnection(); // Replace with your actual connection method
        StudentDAO studentDAO = new StudentDAO(databaseConnection);

        // Fetch the semesters taken by the user (replace `userId` with the actual user ID)
        List<Semester> semesters = studentDAO.getSemestersTaken(UserSession.getInstance().getLoggedInUser().getUserId());

        // Populate the ComboBox with semesters
        if (semesters != null && !semesters.isEmpty()) {
            semesterComboBox.setItems(FXCollections.observableArrayList(semesters));
        }

        // Handle ComboBox selection
        semesterComboBox.setOnAction(event -> {
            Semester selectedSemester = semesterComboBox.getValue();
            if (selectedSemester != null) {
                // Clear the current marks display
                studentMarksContainer.getChildren().clear();

                // Fetch and display student marks for the selected semester
                displayStudentMarks(studentDAO, selectedSemester);
            }
        });
    }

    private void displayStudentMarks(StudentDAO studentDAO, Semester selectedSemester) {
        try {
            studentMarksContainer.getChildren().clear(); // Clear previous marks

            int userId = UserSession.getInstance().getLoggedInUser().getUserId();
            List<StudentMarks> marks = studentDAO.getStudentMarks(userId, selectedSemester.getPeriod(), selectedSemester.getYear());

            if (marks != null && !marks.isEmpty()) {
                for (StudentMarks mark : marks) {
                    // Load the card FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMarkCard.fxml"));
                    Node cardNode = loader.load();

                    // Get the controller of the card and set data
                    StudentMarkCardController cardController = loader.getController();
                    cardController.setData(mark);

                    // Add the card to the VBox container
                    studentMarksContainer.getChildren().add(cardNode);
                }
            } else {
                // No marks found
                Label noMarksLabel = new Label("No marks available for the selected semester.");
                studentMarksContainer.getChildren().add(noMarksLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error fetching student marks.");
            studentMarksContainer.getChildren().add(errorLabel);
        }
    }
}
