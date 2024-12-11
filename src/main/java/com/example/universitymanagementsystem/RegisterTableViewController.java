package com.example.universitymanagementsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        ObservableList<RowData> scheduleList = ScheduleData.getInstance().getScheduleList();
        scheduleTable.setItems(scheduleList);
       // scheduleTable.refresh();
        System.out.println("Initializing TableView...");
        System.out.println("scheduleTable: " + (scheduleTable != null ? "Loaded" : "Null"));
        System.out.println("dayColumn: " + (dayColumn != null ? "Loaded" : "Null"));

        // Ensure correct column bindings
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        period1Column.setCellValueFactory(new PropertyValueFactory<>("period1"));
        period2Column.setCellValueFactory(new PropertyValueFactory<>("period2"));
        period3Column.setCellValueFactory(new PropertyValueFactory<>("period3"));
        period4Column.setCellValueFactory(new PropertyValueFactory<>("period4"));
        period5Column.setCellValueFactory(new PropertyValueFactory<>("period5"));
        period6Column.setCellValueFactory(new PropertyValueFactory<>("period6"));
    }


    public void updateCells(List<CourseSchedule> schedules) {
        // Get the current items in the TableView
        ObservableList<RowData> tableData = scheduleTable.getItems();

        // Print the incoming schedules for debugging
        System.out.println("Updating schedules:");
        for (CourseSchedule schedule : schedules) {
            System.out.println("Day: " + schedule.getDayOfWeek() + ", Period: " + schedule.getPeriod() + ", Major: " + schedule.getMajor());
        }

        // Iterate through each CourseSchedule object in the list
        for (CourseSchedule schedule : schedules) {
            String day = schedule.getDayOfWeek();
            int periodIndex = schedule.getPeriod() - 1; // Convert to 0-based index
            String major = schedule.getMajor();

            // Find the row for the specified day
            for (RowData row : tableData) {
                // Print the current row data for debugging
                System.out.println("Current Row: " + row.getDay() + ", Periods: " +
                        row.getPeriod1() + ", " + row.getPeriod2() + ", " +
                        row.getPeriod3() + ", " + row.getPeriod4() + ", " +
                        row.getPeriod5() + ", " + row.getPeriod6());

                if (row.getDay().equalsIgnoreCase(day)) {
                    // Update the appropriate period cell for the row
                    if (periodIndex >= 0 && periodIndex < 6) {
                        switch (periodIndex) {
                            case 0 -> row.setPeriod1(major);
                            case 1 -> row.setPeriod2(major);
                            case 2 -> row.setPeriod3(major);
                            case 3 -> row.setPeriod4(major);
                            case 4 -> row.setPeriod5(major);
                            case 5 -> row.setPeriod6(major);
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
    }}