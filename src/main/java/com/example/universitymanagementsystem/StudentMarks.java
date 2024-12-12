package com.example.universitymanagementsystem;

public class StudentMarks {
    private String grade;
    private double seventhExam;
    private double twelfthExam;
    private double cw;
    private double finalExam;
    private String period;
    private int year;
    private String courseName;
    private String courseId;

    public StudentMarks(String grade, double seventhExam, double twelfthExam, double cw, double finalExam,
                        String period, int year, String courseName, String courseId) {
        this.grade = grade;
        this.seventhExam = seventhExam;
        this.twelfthExam = twelfthExam;
        this.cw = cw;
        this.finalExam = finalExam;
        this.period = period;
        this.year = year;
        this.courseName = courseName;
        this.courseId = courseId;
    }

    // Getters and setters for the fields
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getSeventhExam() {
        return seventhExam;
    }

    public void setSeventhExam(double seventhExam) {
        this.seventhExam = seventhExam;
    }

    public double getTwelfthExam() {
        return twelfthExam;
    }

    public void setTwelfthExam(double twelfthExam) {
        this.twelfthExam = twelfthExam;
    }

    public double getCw() {
        return cw;
    }

    public void setCw(double cw) {
        this.cw = cw;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}