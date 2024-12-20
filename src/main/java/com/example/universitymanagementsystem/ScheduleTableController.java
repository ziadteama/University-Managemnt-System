package com.example.universitymanagementsystem;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleTableController {

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
    private ScheduleDAO scheduleDAO;

    public ScheduleTableController() {
        try {
            // Initialize the database connection
            Connection dbConnection = DataBaseConnection.getConnection();
            this.scheduleDAO = new ScheduleDAO(dbConnection); // Pass the connection to DAO
        } catch (
                SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        // Initialize the table columns
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        period1Column.setCellValueFactory(new PropertyValueFactory<>("period1"));
        period2Column.setCellValueFactory(new PropertyValueFactory<>("period2"));
        period3Column.setCellValueFactory(new PropertyValueFactory<>("period3"));
        period4Column.setCellValueFactory(new PropertyValueFactory<>("period4"));
        period5Column.setCellValueFactory(new PropertyValueFactory<>("period5"));
        period6Column.setCellValueFactory(new PropertyValueFactory<>("period6"));

        // Populate the table with data
        List<CourseSchedule> schedules = scheduleDAO.getSchedulesByUserId(StudentSession.getCurrentStudent().getUserId());
                populateCells(schedules);
    }

    public void populateCells(List<CourseSchedule> schedules) {
        // Get the current items in the TableView
        ObservableList<RowData> tableData = scheduleTable.getItems();

        // Clear the existing data
        tableData.clear();

        // Create a map to store the rows
        Map<String, RowData> rows = new HashMap<>();

        // Create rows for each day of the week
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        for (String day : days) {
            String[] periods = new String[6];
            for (int i = 0; i < 6; i++) {
                periods[i] = "";
            }
            rows.put(day, new RowData(day, periods));
        }

        // Populate cells
        for (CourseSchedule schedule : schedules) {
            String day = schedule.getDayOfWeek();
            int periodIndex = schedule.getPeriod() - 1; // Convert to 0-based index
            String cellData = schedule.getCourseName() + "\n" + schedule.getClassType();

            // Get the row for the specified day
            if (!rows.containsKey(day)) {
                System.err.println("Warning: Unrecognized day '" + day + "'. Skipping entry.");
                continue; // Skip this schedule entry if the day is invalid
            }

            RowData row = rows.get(day);

            // Double-check row is not null (redundant but safe)
            if (row == null) {
                System.err.println("Error: Row for day '" + day + "' is null. Skipping entry.");
                continue;
            }

            // Set the appropriate period cell for the row
            switch (periodIndex) {
                case 0:
                    row.setPeriod1(cellData);
                    break;
                case 1:
                    row.setPeriod2(cellData);
                    break;
                case 2:
                    row.setPeriod3(cellData);
                    break;
                case 3:
                    row.setPeriod4(cellData);
                    break;
                case 4:
                    row.setPeriod5(cellData);
                    break;
                case 5:
                    row.setPeriod6(cellData);
                    break;
                default:
                    System.err.println("Warning: Invalid period index " + periodIndex + " for day " + day + ". Skipping entry.");
                    break;
            }
        }

        // Add the rows to the table in the correct order
        String[] daysInOrder = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        for (String day : daysInOrder) {
            tableData.add(rows.get(day));
        }

        // Refresh the TableView to show the populated data
        Platform.runLater(() -> scheduleTable.refresh());
    }}