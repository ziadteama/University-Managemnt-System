package com.example.universitymanagementsystem;

import java.util.Arrays;
import java.util.List;

public class RowData {
    private String day;
    private String period1;
    private String period2;
    private String period3;
    private String period4;
    private String period5;
    private String period6;

    // Constructor
    public RowData(String day, String[] periods) {
        this.day = day;
        this.period1 = periods[0];
        this.period2 = periods[1];
        this.period3 = periods[2];
        this.period4 = periods[3];
        this.period5 = periods[4];
        this.period6 = periods[5];
    }

    // Getters for JavaFX PropertyValueFactory
    public String getDay() {
        return day;
    }

    public String getPeriod1() {
        return period1;
    }

    public String getPeriod2() {
        return period2;
    }

    public String getPeriod3() {
        return period3;
    }

    public String getPeriod4() {
        return period4;
    }

    public String getPeriod5() {
        return period5;
    }

    public String getPeriod6() {
        return period6;
    }
    public List<String> getPeriods() {
        return Arrays.asList(period1, period2, period3, period4, period5, period6);
    }
}