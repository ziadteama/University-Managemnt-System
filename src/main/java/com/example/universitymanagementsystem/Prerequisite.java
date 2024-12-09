package com.example.universitymanagementsystem;

public class Prerequisite {
    private String courseId;
    private String prerequisiteCourseId;

    // Constructor, getters, and setters
    public Prerequisite(String courseId, String prerequisiteCourseId) {
        this.courseId = courseId;
        this.prerequisiteCourseId = prerequisiteCourseId;
    }
}

