package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorCourseDisplayController {

    @FXML
    private Label nameLabel;

    @FXML
    private GridPane courseGrid;

    private DataBaseConnection databaseConnection = new DataBaseConnection();

    public void initialize(int drId) {
        String doctorName = "Doctor";

        String query = "SELECT sections.course_id, sections.section_id, users.name AS doctor_name, courses.course_name " +
                "FROM sections " +
                "JOIN users ON sections.dr_id = users.user_id " +
                "JOIN courses ON sections.course_id = courses.course_id " +
                "WHERE sections.dr_id = ? AND sections.year = '2023' AND sections.period = 'Fall'";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, drId);
            ResultSet resultSet = statement.executeQuery();

            int column = 0;
            int row = 0;
            int index = 0;

            while (resultSet.next()) {
                if (doctorName.equals("Doctor")) {
                    doctorName = resultSet.getString("doctor_name");
                }

                String courseName = resultSet.getString("course_name");
                String courseId = resultSet.getString("course_id");
                String sectionId = resultSet.getString("section_id");

                StackPane card = createCourseCard(courseName, courseId, sectionId, index);

                courseGrid.add(card, column, row);
                column++;

                // Move to next row after 3 columns
                if (column == 3) {
                    column = 0;
                    row++;
                }

                index++; // Increment index for the next color switch
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        nameLabel.setText("Hello Dr. " + doctorName + "!");
    }

    private StackPane createCourseCard(String courseName, String courseId, String sectionId, int index) {
        StackPane card = new StackPane();
        card.getStyleClass().add("card");

        // Add color classes based on the index
        switch (index % 4) {
            case 0:
                card.getStyleClass().add("card-blue");
                break;
            case 1:
                card.getStyleClass().add("card-teal");
                break;
            case 2:
                card.getStyleClass().add("card-yellow");
                break;
            case 3:
                card.getStyleClass().add("card-purple");
                break;
        }

        VBox content = new VBox(5);
        content.setStyle("-fx-alignment: center;");

        Text courseNameText = new Text(courseName);
        courseNameText.getStyleClass().add("card-text");

        Text courseIdText = new Text("ID: " + courseId);
        courseIdText.getStyleClass().add("card-secondary-text");

        Text sectionIdText = new Text("Section: " + sectionId);
        sectionIdText.getStyleClass().add("card-secondary-text");

        content.getChildren().addAll(courseNameText, courseIdText, sectionIdText);
        card.getChildren().add(content);

        // On-click event to navigate to the Grade Entry UI
        card.setOnMouseClicked(event -> openGradeEntryView(sectionId));

        return card;
    }

    private void openGradeEntryView(String sectionId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GradeEntry.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Grade Entry - Section: " + sectionId);
            stage.setScene(scene);

            // Pass the sectionId to the GradeEntryController
            GradeEntryController controller = loader.getController();
            controller.setSectionId(sectionId);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleLogout(ActionEvent event) {
        try {
            // Load hello-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setResizable(false);

            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}