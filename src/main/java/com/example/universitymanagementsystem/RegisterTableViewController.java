package com.example.universitymanagementsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class RegisterTableViewController {

    @FXML
    private TableView<RowData> scheduleTable;
    @FXML
    private TableColumn<RowData, String> dayColumn;
    @FXML
    private TableColumn<RowData, String> period1Column;
    @FXML
    private TableColumn<RowData, String> period2Column;
    @FXML
    private TableColumn<RowData, String> period3Column;
    @FXML
    private TableColumn<RowData, String> period4Column;
    @FXML
    private TableColumn<RowData, String> period5Column;
    @FXML
    private TableColumn<RowData, String> period6Column;

    @FXML
    private void initialize() {
        // Load the schedule data and set it to the TableView
        ObservableList<RowData> scheduleList = ScheduleData.getInstance().getScheduleList();
        scheduleTable.setItems(scheduleList);

        // Log the initialization status
        System.out.println("Initializing TableView...");
        System.out.println("scheduleTable: " + (scheduleTable != null ? "Loaded" : "Null"));
        System.out.println("dayColumn: " + (dayColumn != null ? "Loaded" : "Null"));

        // Set custom row height
        scheduleTable.setRowFactory(tv -> {
            TableRow<RowData> row = new TableRow<>();
            row.setPrefHeight(330/6);  // Set row height to 40 pixels
            return row;
        });

        // Ensure correct column bindings
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        period1Column.setCellValueFactory(new PropertyValueFactory<>("period1"));
        period2Column.setCellValueFactory(new PropertyValueFactory<>("period2"));
        period3Column.setCellValueFactory(new PropertyValueFactory<>("period3"));
        period4Column.setCellValueFactory(new PropertyValueFactory<>("period4"));
        period5Column.setCellValueFactory(new PropertyValueFactory<>("period5"));
        period6Column.setCellValueFactory(new PropertyValueFactory<>("period6"));
    }

    public boolean updateCells(List<CourseSchedule> schedules) {
        // Get the current items in the TableView
        ObservableList<RowData> tableData = scheduleTable.getItems();

        // Print the incoming schedules for debugging
        System.out.println("Updating schedules:");
        for (CourseSchedule schedule : schedules) {
            System.out.println("Day: " + schedule.getDayOfWeek() + ", Period: " + schedule.getPeriod() + ", Major: " + schedule.getMajor());
        }

        boolean updateSuccessful = true; // Flag to track if the update was successful
        List<String> conflictMessages = new ArrayList<>(); // List to collect conflict messages

        // First pass: Check for conflicts
        for (CourseSchedule schedule : schedules) {
            String day = schedule.getDayOfWeek();
            int periodIndex = schedule.getPeriod() - 1; // Convert to 0-based index

            // Find the row for the specified day
            for (RowData row : tableData) {
                if (row.getDay().equalsIgnoreCase(day)) {
                    // Check if the period is already occupied
                    String existingCourse = null;
                    row.setSectionId(schedule.getSectionId());
                    switch (periodIndex) {
                        case 0 -> existingCourse = row.getPeriod1();
                        case 1 -> existingCourse = row.getPeriod2();
                        case 2 -> existingCourse = row.getPeriod3();
                        case 3 -> existingCourse = row.getPeriod4();
                        case 4 -> existingCourse = row.getPeriod5();
                        case 5 -> existingCourse = row.getPeriod6();
                    }

                    // If the existing course is not empty, collect the conflict message
                    if (existingCourse != null && !existingCourse.isEmpty()) {
                        String conflictMessage = "The course for " + day + " during period " + (periodIndex + 1) + " is already occupied by " + existingCourse + ".";
                        conflictMessages.add(conflictMessage);
                        updateSuccessful = false; // Set the flag to false
                    }
                    break; // Exit the loop after checking
                }
            }
        }

        // Show a single alert if there are any conflict messages
        if (!conflictMessages.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Conflict Detected");
            alert.setHeaderText("Course Conflicts");
            alert.setContentText(String.join("\n", conflictMessages));
            alert.showAndWait();
            return false; // Return false if there are conflicts
        }

        // Second pass: Update cells only if there are no conflicts
        for (CourseSchedule schedule : schedules) {
            String day = schedule.getDayOfWeek();
            int periodIndex = schedule.getPeriod() - 1; // Convert to 0-based index
            String cellData = schedule.getCourseName()+"\n"+schedule.getClassType()+" at "+ schedule.getLocation();

            // Find the row for the specified day
            for (RowData row : tableData) {
                if (row.getDay().equalsIgnoreCase(day)) {
                    // Update the appropriate period cell for the row
                    if (periodIndex >= 0 && periodIndex < 6) {
                        switch (periodIndex) {
                            case 0 -> row.setPeriod1(cellData);
                            case 1 -> row.setPeriod2(cellData);
                            case 2 -> row.setPeriod3(cellData);
                            case 3 -> row.setPeriod4(cellData);
                            case 4 -> row.setPeriod5(cellData);
                            case 5 -> row.setPeriod6(cellData);
                        }
                    }

                    // Print the updated row data for debugging
                    System.out.println("Updated Row: " + row.getDay() + ", Periods: " +
                            row.getPeriod1() + ", " + row.getPeriod2() + ", " +
                            row.getPeriod3() + ", " + row.getPeriod4() + ", " +
                            row.getPeriod5() + ", " + row.getPeriod6());
                    break; // Exit the loop after updating
                }
            }
        }

        // Refresh the TableView to show the updated data
        Platform.runLater(() -> scheduleTable.refresh());

        return updateSuccessful; // Return whether the update was successful
    }
}