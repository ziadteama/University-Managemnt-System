package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleData {
    private static ScheduleData instance;
    private ObservableList<RowData> scheduleList;

    private ScheduleData() {
        scheduleList = FXCollections.observableArrayList();
        initializeSchedule();
    }

    public static ScheduleData getInstance() {
        if (instance == null) {
            instance = new ScheduleData();
        }
        return instance;
    }

    private void initializeSchedule() {
        scheduleList.add(new RowData("Saturday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Sunday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Monday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Tuesday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Wednesday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Thursday", new String[]{"", "", "", "", "", ""}));
        scheduleList.add(new RowData("Friday", new String[]{"", "", "", "", "", ""}));
    }

    public ObservableList<RowData> getScheduleList() {
        return scheduleList;
    }
}