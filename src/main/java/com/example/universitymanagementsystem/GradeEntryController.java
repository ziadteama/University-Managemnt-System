package com.example.universitymanagementsystem;

import javafx.application.Platform;
import javafx.concurrent.Task;
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


            stage.show();
        } catch (IOException e) {
            System.out.println("Error: Unable to load Attendance view.");
            e.printStackTrace();
        }
    }

    // Load students with attendance and grades
    private void loadStudents() {
        studentListContainer.getChildren().clear(); // Clear UI before loading

        Task<Void> loadStudentsTask = new Task<>() {
            @Override
            protected Void call() {
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
                    String scheduleId = getScheduleIdFromSectionId(currentSectionId);
                    if (scheduleId == null) {
                        throw new RuntimeException("No schedule ID found for section ID: " + currentSectionId);
                    }

                    statement.setString(1, scheduleId);
                    statement.setString(2, currentSectionId);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String userId = resultSet.getString("user_id");
                        String userName = resultSet.getString("name");

                        Double marks7th = resultSet.getObject("seventh_exam") != null ? resultSet.getDouble("seventh_exam") : Double.NaN;
                        Double marks12th = resultSet.getObject("twelfth_exam") != null ? resultSet.getDouble("twelfth_exam") : Double.NaN;
                        Double courseworkMarks = resultSet.getObject("cw") != null ? resultSet.getDouble("cw") : Double.NaN;
                        Double finalExamMarks = resultSet.getObject("final_exam") != null ? resultSet.getDouble("final_exam") : Double.NaN;
                        String grade = resultSet.getString("grade");
                        int absenceCount = resultSet.getInt("absence_count");

                        // Store the student marks in the map
                        studentMarksMap.put(userId, new StudentMarks(marks7th, marks12th, courseworkMarks, finalExamMarks, grade));

                        // Add the row in the UI thread
                        String finalUserId = userId; // Effectively final variables for lambda
                        String finalUserName = userName;
                        Platform.runLater(() -> addStudentRow(finalUserId, finalUserName, marks7th, marks12th, courseworkMarks, finalExamMarks, grade, absenceCount));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Run the task in a background thread
        Thread thread = new Thread(loadStudentsTask);
        thread.setDaemon(true); // Mark the thread as a daemon, so it exits when the application exits
        thread.start();
    }



    private void addStudentRow(String userId, String userName, Double marks7th, Double marks12th, Double courseworkMarks,
                               Double finalExamMarks, String grade, int absenceCount) {
        GridPane studentRow = new GridPane();
        studentRow.setHgap(10);
        studentRow.setVgap(10);

        Label nameLabel = new Label(userName);
        nameLabel.getStyleClass().add("student-name-label");

        // Create TextFields for marks and check if the grade is "W"
        TextField marks7thField = new TextField(Double.isNaN(marks7th) ? "" : marks7th.toString());
        marks7thField.setPromptText("7th");
        marks7thField.setDisable("W".equals(grade)); // Disable if grade is "W"

        TextField marks12thField = new TextField(Double.isNaN(marks12th) ? "" : marks12th.toString());
        marks12thField.setPromptText("12th");
        marks12thField.setDisable("W".equals(grade)); // Disable if grade is "W"

        TextField cwField = new TextField(Double.isNaN(courseworkMarks) ? "" : courseworkMarks.toString());
        cwField.setPromptText("CW");
        cwField.setDisable("W".equals(grade)); // Disable if grade is "W"

        TextField finalField = new TextField(Double.isNaN(finalExamMarks) ? "" : finalExamMarks.toString());
        finalField.setPromptText("Final");
        finalField.setDisable("W".equals(grade)); // Disable if grade is "W"

        // Create the grade label
        Label gradeLabel = new Label(grade != null ? grade : "U");

        // Add listeners only if the grade is not "W"
        if (!"W".equals(grade)) {
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
        }

        // Add components to the GridPane
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
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() {
                try (Connection connection = databaseConnection.getConnection()) {
                    String query = "UPDATE enrollments SET seventh_exam = ?, twelfth_exam = ?, cw = ?, final_exam = ?, grade = ? WHERE user_id = ? AND section_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);

                    for (Map.Entry<String, StudentMarks> entry : studentMarksMap.entrySet()) {
                        String userId = entry.getKey();
                        StudentMarks marks = entry.getValue();

                        statement.setObject(1, Double.isNaN(marks.getSeventhExam()) ? null : marks.getSeventhExam());
                        statement.setObject(2, Double.isNaN(marks.getTwelfthExam()) ? null : marks.getTwelfthExam());
                        statement.setObject(3, Double.isNaN(marks.getCw()) ? null : marks.getCw());
                        statement.setObject(4, Double.isNaN(marks.getFinalExam()) ? null : marks.getFinalExam());
                        statement.setString(5, marks.getGrade());
                        statement.setString(6, userId);
                        statement.setString(7, currentSectionId);

                        statement.addBatch();
                    }

                    int[] updateCounts = statement.executeBatch();
                    System.out.println("Marks successfully saved for " + updateCounts.length + " students!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                // Show a confirmation dialog or message on the UI thread
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Save Success");
                    alert.setHeaderText("Marks Saved");
                    alert.setContentText("All marks were successfully saved!");
                    alert.showAndWait();
                });
            }

            @Override
            protected void failed() {
                // Show an error dialog or message on the UI thread
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Save Failed");
                    alert.setHeaderText("Error Saving Marks");
                    alert.setContentText("An error occurred while saving the marks. Please try again.");
                    alert.showAndWait();
                });
            }
        };

        // Run the task in a background thread
        Thread thread = new Thread(saveTask);
        thread.setDaemon(true);
        thread.start();
    }

}