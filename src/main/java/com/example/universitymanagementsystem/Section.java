package com.example.universitymanagementsystem;

public class Section {
    private String sectionId;
    private String courseId;
    private int year;
    private int drId;  // Lecturer ID
    private int taId;  // Tutor ID
    private String period;  // 'fall', 'spring', etc.

    // Constructor, getters, and setters
    public Section(String sectionId, String courseId, int year, int drId, int taId, String period) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.year = year;
        this.drId = drId;
        this.taId = taId;
        this.period = period;
    }
}

