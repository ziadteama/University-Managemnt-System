package com.example.universitymanagementsystem;

public class Schedule {
    private String scheduleId;
    private String sectionId;
    private String dayOfWeek; // e.g., 'Monday', 'Tuesday'
    private String major;
    private String period; // e.g., 'first', 'second', etc.
    private String location;
    private int userId;

    // Constructor, getters, and setters
    public Schedule(String scheduleId, String sectionId, String dayOfWeek, String major, String period, String location, int userId) {
        this.scheduleId = scheduleId;
        this.sectionId = sectionId;
        this.dayOfWeek = dayOfWeek;
        this.major = major;
        this.period = period;
        this.location = location;
        this.userId = userId;
    }

}

