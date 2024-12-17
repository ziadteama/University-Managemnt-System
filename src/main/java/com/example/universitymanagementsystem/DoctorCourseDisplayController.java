package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DoctorCourseDisplayController<StudentListController> {

    @FXML
    private Label nameLabel;
    @FXML
    private Button taRegisterButton;

    @FXML
    private GridPane courseGrid;

    @FXML
    private Button viewScheduleButton;
    private StudentDAO studentDAO = new StudentDAO(DataBaseConnection.getConnection());

    private UserDAO doctorDAO = new UserDAO(DataBaseConnection.getConnection());

    public DoctorCourseDisplayController() throws Exception {
    }

    public void initialize() throws SQLException {
        String role = UserSession.getInstance().getLoggedInUser().getRole();
        User user = UserSession.getInstance().getLoggedInUser();
        if (role.equals("dr")) {
            user = (Doctor) user;
            taRegisterButton.setVisible(false);
        }
        else if (role.equals("ta"))
            user = (Tutor) user;


        // Get the courses for the doctor using the DAO
        List<Course> courses = doctorDAO.getCoursesForTeacher(user.getUserId());

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
        if (!courses.isEmpty()) {
            nameLabel.setText("Hello " + role.toUpperCase() +" "+ courses.getFirst().getDoctorName() + "!");
        } else nameLabel.setText("Hello " + role.toUpperCase() + user.getName() + "\nYou Have NO Courses!!");

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

            // Set the width and height manually
            stage.setWidth(800);  // Set your desired width
            stage.setHeight(700); // Set your desired height

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


            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewSchedule(ActionEvent event) {
        try {
            // Load the TeacherScheduleView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherScheduleView.fxml"));
            Scene scheduleScene = new Scene(loader.load());

            // Create a new stage for the schedule view
            Stage scheduleStage = new Stage();
            scheduleStage.setScene(scheduleScene);
            scheduleStage.setTitle("Teacher Schedule");

            // Show the new stage
            scheduleStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTaRegister(ActionEvent event) {
        try {
            // Load FXML for new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TaStudentList.fxml"));
            AnchorPane root = loader.load();

            // Get Controller of new scene
            TaStudentListController controller = loader.getController();

            // Fetch data from database and pass to the controller
            List<Student> students = studentDAO.getStudentsByTaId(UserSession.getInstance().getLoggedInUser().getUserId());
            controller.setStudentList(students);

            // Set the new scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Student List");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

