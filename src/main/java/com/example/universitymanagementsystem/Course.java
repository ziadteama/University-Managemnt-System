package com.example.universitymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private int creditHours;
    private List<Prerequisite> prerequisites;
    private List<Section> sections;

    // Constructor, getters, and setters

    public Course(String courseId, String courseName, int creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.prerequisites = new ArrayList<>();
        this.sections = new ArrayList<>();
    }

    // Add methods to add prerequisites and sections
}
