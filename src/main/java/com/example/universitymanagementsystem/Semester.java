package com.example.universitymanagementsystem;

public class Semester {
    private int year;
    private String period;

    public Semester(int year, String period) {
        this.year = year;
        this.period = period;
    }

    // Getters and setters for the fields
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return year + " " + period;
    }
}
