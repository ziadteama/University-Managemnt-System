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
        ObservableList<RowData> sampleData = FXCollections.observableArrayList(
                new RowData("Saturday", new String[]{"", "", "", "", "", ""}),
                new RowData("Sunday", new String[]{"", "", "", "", "", ""}),
                new RowData("Monday", new String[]{"", "", "", "", "", ""}),
                new RowData("Tuesday", new String[]{"", "", "", "", "", ""}),
                new RowData("Wednesday", new String[]{"", "", "", "", "", ""}),
                new RowData("Thursday", new String[]{"", "", "", "", "", ""}),
                new RowData("Friday", new String[]{"", "", "", "", "", ""})
        );
        scheduleTable.setItems(sampleData);
        scheduleTable.refresh();
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

    /**
     * Updates the TableView with the provided schedule data.
     *
     * @param sectionSchedule the list of ScheduleItem objects
     */
    public void updateSchedule(List<CourseSchedule> sectionSchedule) {
        // Get existing items from the table
        ObservableList<RowData> tableData = scheduleTable.getItems();

        // Iterate through the schedule and update only necessary fields
        for (CourseSchedule schedule : sectionSchedule) {
            String day = schedule.getDayOfWeek();
            int periodIndex = schedule.getPeriod() - 1; // Get the period index (0-based)
            String major = schedule.getMajor();

            // Find the matching RowData for the day
            for (RowData row : tableData) {
                if (row.getDay().equalsIgnoreCase(day) && periodIndex >= 0 && periodIndex < 6) {
                    // Update the appropriate period cell for the row
                    switch (periodIndex) {
                        case 0 -> row.setPeriod1(major);
                        case 1 -> row.setPeriod2(major);
                        case 2 -> row.setPeriod3(major);
                        case 3 -> row.setPeriod4(major);
                        case 4 -> row.setPeriod5(major);
                        case 5 -> row.setPeriod6(major);
                    }
                    break; // Break out of loop once the matching day is found
                }
            }
        }
        Platform.runLater(() -> {
            scheduleTable.refresh();
        });

        // No need to refresh or reset anything; updates are automatically reflected
        Platform.runLater(() -> {
            System.out.println("Updated cells in scheduleTable:");
            tableData.forEach(item -> System.out.println(
                    item.getDay() + ": " +
                            item.getPeriod1() + ", " + item.getPeriod2() + ", " +
                            item.getPeriod3() + ", " + item.getPeriod4() + ", " +
                            item.getPeriod5() + ", " + item.getPeriod6()));
        });
    }
}