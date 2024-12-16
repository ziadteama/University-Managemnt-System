package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DoctorCourseDisplayController {

    @FXML
    private Label nameLabel;

    @FXML
    private GridPane courseGrid;

    @FXML
    private Button viewScheduleButton;

    private DoctorDAO doctorDAO = new DoctorDAO();

    public void initialize() {
        Doctor dr = (Doctor) UserSession.getInstance().getLoggedInUser();

        // Get the courses for the doctor using the DAO
        List<Course> courses = doctorDAO.getCoursesForDoctor(dr.getUserId());

        int column = 0;
        int row = 0;
        int index = 0;

        for (Course course : courses) {
            String courseName = course.getCourseName();
            String courseId = course.getCourseId();
            String sectionId = course.getSectionId();

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

        nameLabel.setText("Hello Dr. " + courses.getFirst().getDoctorName() + "!");

        // Add action listener for the View Schedule button

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

            // Make the window resize to fit the content
            stage.sizeToScene();  // Adjust the size based on the content
            stage.setResizable(true);  // Allow resizing if needed

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

