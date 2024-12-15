package com.example.universitymanagementsystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AttendanceController {

    @FXML
    private VBox studentListContainer;

    @FXML
    private ComboBox<Week> weekDropdown;



    @FXML
    private Button backButton;

    private DataBaseConnection databaseConnection = new DataBaseConnection();

    private String currentScheduleId;
    private Map<String, Boolean> attendanceMap = new HashMap<>();

    public enum Week {
        FIRST("1st Week"),
        SECOND("2nd Week"),
        THIRD("3rd Week"),
        FOURTH("4th Week"),
        FIFTH("5th Week"),
        SIXTH("6th Week"),
        SEVENTH("7th Week"),
        EIGHTH("8th Week"),
        NINTH("9th Week"),
        TENTH("10th Week"),
        ELEVENTH("11th Week"),
        TWELFTH("12th Week"),
        THIRTEENTH("13th Week"),
        FOURTEENTH("14th Week"),
        FIFTEENTH("15th Week");

        private final String displayName;

        Week(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public void setScheduleId(String scheduleId) {
        this.currentScheduleId = scheduleId;
        populateWeekDropdown();
        weekDropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAttendanceForWeek(newValue);
            }
        });
    }

    private void loadAttendanceForWeek(Week selectedWeek) {
        attendanceMap.clear();
        studentListContainer.getChildren().clear();

        if (selectedWeek == null || currentScheduleId == null) return;

        String query = "SELECT enrollments.user_id, users.name, attendance.present " +
                "FROM users " +
                "JOIN enrollments ON users.user_id = enrollments.user_id " +
                "LEFT JOIN attendance ON enrollments.user_id = attendance.user_id " +
                "AND attendance.schedule_id = ? AND attendance.week = ? " +
                "WHERE enrollments.section_id IN (SELECT section_id FROM schedules WHERE schedule_id = ?) ORDER BY users.name";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, currentScheduleId);
            statement.setString(2, selectedWeek.toString());
            statement.setString(3, currentScheduleId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String name = resultSet.getString("name");
                Boolean present = resultSet.getBoolean("present");

                attendanceMap.put(userId, present != null && present);
                addStudentAttendanceRow(userId, name, present);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addStudentAttendanceRow(String userId, String name, Boolean present) {
        CheckBox checkBox = new CheckBox(name);
        checkBox.setSelected(present != null && present);

        checkBox.setOnAction(event -> attendanceMap.put(userId, checkBox.isSelected()));
        studentListContainer.getChildren().add(checkBox);
    }

    private void populateWeekDropdown() {
        weekDropdown.getItems().addAll(Week.values());
    }

    @FXML
    private void saveAttendance() {
        try (Connection connection = databaseConnection.getConnection()) {
            String query = "INSERT INTO attendance (user_id, schedule_id, week, present) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE present = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Map.Entry<String, Boolean> entry : attendanceMap.entrySet()) {
                    String userId = entry.getKey();
                    Boolean present = entry.getValue();

                    statement.setString(1, userId);
                    statement.setString(2, currentScheduleId);
                    statement.setString(3, weekDropdown.getValue().toString());
                    statement.setBoolean(4, present);
                    statement.setBoolean(5, present);

                    statement.addBatch();
                }

                statement.executeBatch();
            }

            showAlert("Success", "Attendance data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred  while saving the attendance.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GradeEntry.fxml"));
            Scene scene = new Scene(loader.load());

            GradeEntryController controller = loader.getController();
            controller.setSectionId(currentScheduleId); // Pass the current section ID to reload students

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
