package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GradeEntryController {

    @FXML
    private VBox studentListContainer; // VBox to dynamically contain each student's row
    @FXML
    private Label sectionNameLabel; // Label to show section info at the top
    @FXML
    private Button attendanceButton; // Attendance button

    @FXML
    private Button saveButton;  // This will be linked to the button in FXML
    private DataBaseConnection databaseConnection = new DataBaseConnection(); // Database connection
    private String currentSectionId; // Holds the current section ID
    private Map<String, StudentMarks> studentMarksMap = new HashMap<>(); // Holds marks for all users

    // Method to initialize the controller with section information
    public void setSectionId(String sectionId) {
        sectionNameLabel.setText("Grade Entry - Section: " + sectionId);
        this.currentSectionId = sectionId;

        // Load students dynamically based on the section ID
        loadStudents();

        // Set action for the Save button
        saveButton.setOnAction(event -> saveMarks());
    }

    private String getScheduleIdFromSectionId(String sectionId) {
        String scheduleId = null;
        String query = "SELECT schedule_id FROM schedules WHERE section_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sectionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                scheduleId = resultSet.getString("schedule_id");
                System.out.println("Mapped section_id: " + sectionId + " to schedule_id: " + scheduleId);
            } else {
                System.out.println("No schedule_id found for section_id: " + sectionId);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving schedule_id.");
            e.printStackTrace();
        }
        return scheduleId;
    }

    @FXML
    private void handleAttendance() {
        try {
            // Load the Attendance view (Attendance.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendance.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the AttendanceController and pass the schedule ID after resolving it
            AttendanceController controller = loader.getController();
            String scheduleId = getScheduleIdFromSectionId(currentSectionId);
            if (scheduleId == null) {
                System.out.println("Error: No schedule ID found for section ID: " + currentSectionId);
                return; // Exit if no valid schedule ID is found
            }
            controller.setScheduleId(scheduleId);

            // Switch to the Attendance scene
            Stage stage = (Stage) attendanceButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error: Unable to load Attendance view.");
            e.printStackTrace();
        }
    }

    // Load students with attendance and grades
    private void loadStudents() {
        // Clear existing student rows in the VBox to prevent duplicates
        studentListContainer.getChildren().clear();

        String query = "SELECT users.user_id, users.name, enrollments.seventh_exam, " +
                "enrollments.twelfth_exam, enrollments.cw, enrollments.final_exam, enrollments.grade, " +
                "(SELECT COUNT(*) FROM attendance " +
                "WHERE attendance.user_id = users.user_id AND attendance.schedule_id = ? " +
                "AND attendance.present = 0) AS absence_count " +
                "FROM users " +
                "JOIN enrollments ON users.user_id = enrollments.user_id " +
                "WHERE enrollments.section_id = ? ORDER BY users.name";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Use getScheduleIdFromSectionId to resolve schedule_id dynamically
            String scheduleId = getScheduleIdFromSectionId(currentSectionId);
            if (scheduleId == null) {
                System.out.println("Error: No schedule ID found for section ID: " + currentSectionId);
                return; // Exit if no valid schedule ID is found
            }

            statement.setString(1, scheduleId); // Use the scheduleId here
            statement.setString(2, currentSectionId);  // Section ID
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String userName = resultSet.getString("name");
                Integer marks7th = resultSet.getObject("seventh_exam") != null ? resultSet.getInt("seventh_exam") : null;
                Integer marks12th = resultSet.getObject("twelfth_exam") != null ? resultSet.getInt("twelfth_exam") : null;
                Integer courseworkMarks = resultSet.getObject("cw") != null ? resultSet.getInt("cw") : null;
                Integer finalExamMarks = resultSet.getObject("final_exam") != null ? resultSet.getInt("final_exam") : null;
                String grade = resultSet.getString("grade");
                int absenceCount = resultSet.getInt("absence_count");

                studentMarksMap.put(userId, new StudentMarks(marks7th, marks12th, courseworkMarks, finalExamMarks, grade));

                // Add student row to the UI
                addStudentRow(userId, userName, marks7th, marks12th, courseworkMarks, finalExamMarks, grade, absenceCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addStudentRow(String userId, String userName, Integer marks7th, Integer marks12th, Integer courseworkMarks,
                               Integer finalExamMarks, String grade, int absenceCount) {
        GridPane studentRow = new GridPane();
        studentRow.setHgap(10);
        studentRow.setVgap(10);

        Label nameLabel = new Label(userName);
        nameLabel.getStyleClass().add("student-name-label");

        TextField marks7thField = new TextField(marks7th != null ? marks7th.toString() : "");
        marks7thField.setPromptText("7th");


        TextField marks12thField = new TextField(marks12th != null ? marks12th.toString() : "");
        marks12thField.setPromptText("12th");

        TextField cwField = new TextField(courseworkMarks != null ? courseworkMarks.toString() : "");
        cwField.setPromptText("CW");

        TextField finalField = new TextField(finalExamMarks != null ? finalExamMarks.toString() : "");
        finalField.setPromptText("Final");

        Label gradeLabel = new Label(grade != null ? grade : "U "); // Display saved grade if available

        // Capture changes in marks fields in the studentMarks map
        marks7thField.textProperty().addListener((obs, oldVal, newVal) -> {
            studentMarksMap.get(userId).setSeventhExam(parseMarks(newVal));
            updateGradeAndLabel(userId, gradeLabel);
        });

        marks12thField.textProperty().addListener((obs, oldVal, newVal) -> {
            studentMarksMap.get(userId).setTwelfthExam(parseMarks(newVal));
            updateGradeAndLabel(userId, gradeLabel);
        });

        cwField.textProperty().addListener((obs, oldVal, newVal) -> {
            studentMarksMap.get(userId).setCw(parseMarks(newVal));
            updateGradeAndLabel(userId, gradeLabel);
        });

        finalField.textProperty().addListener((obs, oldVal, newVal) -> {
            studentMarksMap.get(userId).setFinalExam(parseMarks(newVal));
            updateGradeAndLabel(userId, gradeLabel);
        });

        studentRow.add(nameLabel, 0, 0);
        studentRow.add(marks7thField, 1, 0);
        studentRow.add(marks12thField, 2, 0);
        studentRow.add(cwField, 3, 0);
        studentRow.add(finalField, 4, 0);
        studentRow.add(gradeLabel, 5, 0);

        // Add Withdraw button if absences are 3 or more
        if (absenceCount >= 3) {
            Button withdrawButton = new Button("Withdraw");
            withdrawButton.setOnAction(event -> handleWithdrawal(userId, gradeLabel));
            studentRow.add(withdrawButton, 6, 0);
        }

        studentListContainer.getChildren().add(studentRow);
    }

    private void updateGradeAndLabel(String userId, Label gradeLabel) {
        String newGrade = calculateGrade(userId);
        gradeLabel.setText(newGrade);
        studentMarksMap.get(userId).setGrade(newGrade); // Update grade in StudentMarks object
    }

    private String calculateGrade(String userId) {
        StudentMarks marks = studentMarksMap.get(userId);

        if ("W".equals(marks.getGrade())) {
            return "W"; // If the student is withdrawn, return "W"
        }

        if (Double.isNaN(marks.getSeventhExam()) || Double.isNaN(marks.getTwelfthExam()) || Double.isNaN(marks.getCw()) || Double.isNaN(marks.getFinalExam())) {
            return "U"; // If any mark is missing, return "U" (Incomplete)
        }

        double totalMarks = marks.getSeventhExam() + marks.getTwelfthExam() + marks.getCw() + marks.getFinalExam();
        if (totalMarks >= 97) return "A+";
        if (totalMarks >= 93) return "A";
        if (totalMarks >= 90) return "A-";
        if (totalMarks >= 87) return "B+";
        if (totalMarks >= 83) return "B";
        if (totalMarks >= 80) return "B-";
        if (totalMarks >= 77) return "C+";
        if (totalMarks >= 73) return "C";
        if (totalMarks >= 70) return "C-";
        return "F"; // If below passing marks
    }

    private Integer parseMarks(String value) {
        try {
            return value.isEmpty() ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // Handle invalid input
        }
    }

    private void handleWithdrawal(String userId, Label gradeLabel) {
        // Handle withdrawal logic (e.g., mark as withdrawn in database)
        gradeLabel.setText("W"); // Set grade to "W" for withdrawn
        studentMarksMap.get(userId).setGrade("W"); // Update StudentMarks object
    }

    @FXML
    private void saveMarks() {
        try (Connection connection = databaseConnection.getConnection()) {
            String query = "UPDATE enrollments SET seventh_exam = ?, twelfth_exam = ?, cw = ?, final_exam = ?, grade = ? WHERE user_id = ? AND section_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            for (Map.Entry<String, StudentMarks> entry : studentMarksMap.entrySet()) {
                String userId = entry.getKey();
                StudentMarks marks = entry.getValue();

                statement.setObject(1, marks.getSeventhExam());
                statement.setObject(2, marks.getTwelfthExam());
                statement.setObject(3, marks.getCw());
                statement.setObject(4, marks.getFinalExam());
                statement.setString(5, marks.getGrade());
                statement.setString(6, userId);
                statement.setString(7, currentSectionId);

                statement.addBatch();
            }

            int[] updateCounts = statement.executeBatch();
            System.out.println("Marks successfully saved for " + updateCounts.length + " students!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
