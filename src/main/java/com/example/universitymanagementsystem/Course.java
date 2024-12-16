package com.example.universitymanagementsystem;

public class Course {
    private String courseName;
    private String courseId;
    private String sectionId;
    private String doctorName;

    public Course(String courseName, String courseId, String sectionId, String doctorName) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.doctorName = doctorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
