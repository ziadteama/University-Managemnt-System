package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class CourseCardController {

    @FXML
    private Label courseNameLabel2, courseNameLabel21, courseNameLabel212, courseNameLabel211, courseNameLabel2111;

    @FXML
    private Label lectureTime, tutorialTime;

    @FXML
    private Button addCourseButton;

    private String sectionId;

    private ScheduleDAO scheduleDAO;
    private StudentRegisterController studentRegisterController;
    private TaRegistrationController taRegistrationController;

    /**
     * Sets the course details on the card.
     */
    public void setCourseDetails(String sectionId, String courseName, String courseCode, int creditHours,
                                 String lecturer, String tutor, String lectureTimeText, String tutorialTimeText) {
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
     */
    public static Node createCard(String sectionId, String courseName, String courseCode, int creditHours,
                                  String lecturer, String tutor, String lectureTime, String tutorialTime,
                                  Object parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(CourseCardController.class.getResource("/com/example/universitymanagementsystem/registerationcard.fxml"));
            Node cardNode = loader.load();

            CourseCardController controller = loader.getController();
            controller.setCourseDetails(sectionId, courseName, courseCode, creditHours, lecturer, tutor, lectureTime, tutorialTime);

            Connection dbConnection = DataBaseConnection.getConnection();
            controller.scheduleDAO = new ScheduleDAO(dbConnection);

            if (parentController instanceof StudentRegisterController) {
                controller.studentRegisterController = (StudentRegisterController) parentController;
            } else if (parentController instanceof TaRegistrationController) {
                controller.taRegistrationController = (TaRegistrationController) parentController;
            }

            cardNode.setUserData(controller);
            return cardNode;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles the "Add Course" button click event.
     */
    @FXML
    private void handleAddCourse() {
        if (UserSession.getInstance().getLoggedInUser().getRole().equals("student")) {
            processCourseAddition(studentRegisterController);
        } else {
            processCourseAddition(taRegistrationController);
        }
    }

    /**
     * Processes course addition and removes the card.
     */
    private void processCourseAddition(Object controller) {
        List<CourseSchedule> scheduleItems = scheduleDAO.getScheduleBySectionId(sectionId);

        if (scheduleItems == null || scheduleItems.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Schedule Not Found", "No schedule available for this course.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/universitymanagementsystem/registertableview.fxml"));
            Node tableViewNode = loader.load();

            RegisterTableViewController tableController = loader.getController();
            boolean updateSuccessful = tableController.updateCells(scheduleItems);

            if (updateSuccessful) {
                if (controller instanceof StudentRegisterController) {
                    ((StudentRegisterController) controller).removeCourseCard(addCourseButton.getParent());
                    ((StudentRegisterController) controller).removeOtherCardsWithSameCourseName(courseNameLabel2.getText());
                } else if (controller instanceof TaRegistrationController) {
                    ((TaRegistrationController) controller).removeCourseCard(addCourseButton.getParent());
                    ((TaRegistrationController) controller).removeOtherCardsWithSameCourseName(courseNameLabel2.getText());
                }

                showAlert(Alert.AlertType.INFORMATION, "Course Added", "The course(s) have been added to your schedule.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the table view.");
        }
    }

    /**
     * Utility method to show alerts.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
