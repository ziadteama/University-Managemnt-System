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
        // Prepare data for the TableView
        ObservableList<RowData> tableData = FXCollections.observableArrayList();

        // Days of the week to iterate over
        List<String> daysOfWeek = List.of("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        // Iterate through the days and populate table rows
        for (String day : daysOfWeek) {
            String[] periods = new String[6];
            for (int i = 0; i < periods.length; i++) {
                periods[i] = "";
            }

            sectionSchedule.stream()
                    .filter(schedule -> schedule.getDayOfWeek().equalsIgnoreCase(day))
                    .forEach(schedule -> {
                        int periodIndex = schedule.getPeriod() - 1;
                        if (periodIndex >= 0 && periodIndex < 6) {
                            periods[periodIndex] = schedule.getMajor();
                        }
                    });

            tableData.add(new RowData(day, periods));
        }

        // Set the data into TableView
        Platform.runLater(() -> {
            System.out.println("Data in scheduleTable:");
            scheduleTable.setItems(tableData);
            scheduleTable.getItems().forEach(item ->
                    System.out.println(item.getDay() + ": " +
                            item.getPeriod1() + ", " + item.getPeriod2() + ", " +
                            item.getPeriod3() + ", " + item.getPeriod4() + ", " +
                            item.getPeriod5() + ", " + item.getPeriod6()));
            scheduleTable.layout();
            scheduleTable.refresh();
        });
        // Debugging: Print items in TableView

    }
}