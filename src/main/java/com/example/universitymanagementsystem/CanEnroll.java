package com.example.universitymanagementsystem;

public class CanEnroll {
    private String courseName;
    private String courseCode;
    private int creditHours;
    private String lecturerName;
    private String tutorName;
    private String lectureTime; // Includes location
    private String tutorialTime; // Includes location

    // Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getTutorialTime() {
        return tutorialTime;
    }

    public void setTutorialTime(String tutorialTime) {
        this.tutorialTime = tutorialTime;
    }
}
