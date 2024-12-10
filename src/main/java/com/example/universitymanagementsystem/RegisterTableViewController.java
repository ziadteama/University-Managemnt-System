package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RegisterTableViewController {

    @FXML
    private TableView<ScheduleItem> scheduleTable;
    @FXML
    private TableColumn<ScheduleItem, String> dayColumn;
    @FXML
    private TableColumn<ScheduleItem, String> period1Column;
    @FXML
    private TableColumn<ScheduleItem, String> period2Column;
    @FXML
    private TableColumn<ScheduleItem, String> period3Column;
    @FXML
    private TableColumn<ScheduleItem, String> period4Column;
    @FXML
    private TableColumn<ScheduleItem, String> period5Column;
    @FXML
    private TableColumn<ScheduleItem, String> period6Column;

    @FXML
    private void initialize() {
        // Initialize columns
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
     * @param scheduleItems the list of ScheduleItem objects
     */
    public void updateSchedule(List<ScheduleItem> scheduleItems) {
        ObservableList<ScheduleItem> observableSchedule = FXCollections.observableArrayList(scheduleItems);
        scheduleTable.setItems(observableSchedule);
    }
}
