package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TaStudentListController {
    @FXML
    private ListView<HBox> studentListView;  // ListView to hold student data in HBox

    private StudentDAO studentDAO = new StudentDAO(DataBaseConnection.getConnection());

    public TaStudentListController() throws Exception {
        // Constructor
    }

    public void setStudentList(List<Student> students) {
        // Create an ObservableList to hold the HBox rows
        ObservableList<HBox> studentItems = FXCollections.observableArrayList();

        for (Student student : students) {
            // Create an HBox to layout the student information horizontally (ID, Name, Register Button)
            HBox studentRow = new HBox(10);  // 10 is the spacing between elements
            studentRow.setPadding(new javafx.geometry.Insets(5));  // Padding around the HBox

            // Create a label for the student ID
            Label studentIdLabel = new Label(String.valueOf(student.getUserId()));
            studentIdLabel.setStyle("-fx-font-weight: bold;");

            // Create a label for the student name
            Label studentNameLabel = new Label(student.getName());

            // Create a "Register" button for each student
            Button registerButton = new Button("Register");

            // Set the action when the button is clicked
            registerButton.setOnAction(event -> {
                handleRegisterClick(student.getUserId());
            });

            // Add the student details to the HBox
            studentRow.getChildren().addAll(studentIdLabel, studentNameLabel, registerButton);

            // Add the HBox to the ObservableList
            studentItems.add(studentRow);
        }

        // Set the items of the ListView to the list of student rows (HBox)
        studentListView.setItems(studentItems);
    }

    // Method to handle register button click
    private void handleRegisterClick(int studentId) {
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

        try {
            // Load the registration FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/universitymanagementsystem/taregisteration.fxml"));
            // Assuming you have a 'student' object already created and available
            Student student = studentDAO.getStudentById(studentId);  // Replace with your actual method to get the student
            UserSession.getInstance().getLoggedInUser().setCurrentStudent(student);

            // Check if the student was found
            if (student == null) {
                // Handle the case where the student could not be found (e.g., show an alert)
                showErrorAlert("Student Not Found", "No student found with ID: " + studentId);
                return;
            }

            // Load the FXML file
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) studentListView.getScene().getWindow();
            if (stage == null) {
                // Handle the case where the stage could not be retrieved (e.g., show an alert)
                showErrorAlert("Window Error", "Could not retrieve the current window.");
                return;
            }

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show error alert if FXML loading fails
            showErrorAlert("FXML Load Error", "Failed to load the registration FXML file.");
        } catch (Exception e) {
            e.printStackTrace();
            // General error handler for any other exceptions
            showErrorAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    // Method to show error alerts
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
