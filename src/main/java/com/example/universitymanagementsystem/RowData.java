package com.example.universitymanagementsystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RowData {
    private final StringProperty day;
    private final StringProperty period1;
    private final StringProperty period2;
    private final StringProperty period3;
    private final StringProperty period4;
    private final StringProperty period5;
    private final StringProperty period6;

    public RowData(String day, String[] periods) {
        this.day = new SimpleStringProperty(day);
        this.period1 = new SimpleStringProperty(periods[0]);
        this.period2 = new SimpleStringProperty(periods[1]);
        this.period3 = new SimpleStringProperty(periods[2]);
        this.period4 = new SimpleStringProperty(periods[3]);
        this.period5 = new SimpleStringProperty(periods[4]);
        this.period6 = new SimpleStringProperty(periods[5]);
    }

    public String getDay() {
        return day.get();
    }

    public StringProperty dayProperty() {
        return day;
    }

    public String getPeriod1() {
        return period1.get();
    }

    public void setPeriod1(String value) {
        this.period1.set(value);
    }

    public StringProperty period1Property() {
        return period1;
    }

    public String getPeriod2() {
        return period2.get();
    }

    public void setPeriod2(String value) {
        this.period2.set(value);
    }

    public StringProperty period2Property() {
        return period2;
    }

    public String getPeriod3() {
        return period3.get();
    }

    public void setPeriod3(String value) {
        this.period3.set(value);
    }

    public StringProperty period3Property() {
        return period3;
    }

    public String getPeriod4() {
        return period4.get();
    }

    public void setPeriod4(String value) {
        this.period4.set(value);
    }

    public StringProperty period4Property() {
        return period4;
    }

    public String getPeriod5() {
        return period5.get();
    }

    public void setPeriod5(String value) {
        this.period5.set(value);
    }

    public StringProperty period5Property() {
        return period5;
    }

    public String getPeriod6() {
        return period6.get();
    }

    public void setPeriod6(String value) {
        this.period6.set(value);
    }

    public StringProperty period6Property() {
        return period6;
    }
}