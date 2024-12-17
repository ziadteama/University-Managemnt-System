package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TaStudentListController {
    @FXML
    private VBox studentVBox; // VBox from FXML where the list will be added dynamically


    private StudentDAO studentDAO = new StudentDAO(DataBaseConnection.getConnection());

    public TaStudentListController() throws Exception {

    }

    public void setStudentList(List<Student> students) {
        studentVBox.getChildren().clear(); // Clear existing items (if any)

        for (Student student : students) {
            // Create a label for the student
            Label studentLabel = new Label(student.getUserId() + " - " + student.getName());

            // Create a "Register" button for each student
            Button registerButton = new Button("Register");

            // Set the action when the button is clicked
            registerButton.setOnAction(event -> {
                handleRegisterClick(student.getUserId());
            });

            // Combine the label and button into an HBox
            HBox studentRow = new HBox(10); // Spacing between elements
            studentRow.getChildren().addAll(studentLabel, registerButton);

            // Add the HBox to the VBox
            studentVBox.getChildren().add(studentRow);
        }
    }

    // Method to handle register button click
    private void handleRegisterClick(int studentId) {
        try {
            // Load the registration FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/universitymanagementsystem/taregisteration.fxml"));
System.out.println("line 60 "+studentId);
            // Assuming you have a 'student' object already created and available
            Student student = studentDAO.getStudentById(studentId);  // Replace with your actual method to get the student

            // Check if the student was found
            if (student == null) {
                // Handle the case where the student could not be found (e.g., show an alert)
                showErrorAlert("Student Not Found", "No student found with ID: " + studentId);
                return;
            }

            // Load the FXML file
            Parent root = loader.load();

            // Get the controller from the FXMLLoader
            TaRegistrationController controller = loader.getController();

            // Pass the student object to the controller
            controller.setStudent(student);

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) studentVBox.getScene().getWindow();
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
