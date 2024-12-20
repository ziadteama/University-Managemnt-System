package com.example.universitymanagementsystem;

public class Enrollments {
    private int userId;           // Student ID
    private String sectionId;     // Section ID (foreign key)
    private String grade;         // Grade the student received (A+, A, A-, etc.)
    private int semesterTaken;    // The semester in which the course was taken
    private int pointsGained;     // Points gained from the course
    private double seventhExam;   // Score from the seventh exam
    private double twelfthExam;   // Score from the twelfth exam
    private double cw;            // Continuous work score (e.g., assignments)
    private double finalExam;     // Final exam score
    private double totalMark;     // Total mark (computed from the scores)

    // Constructor
    public Enrollments(int userId, String sectionId, String grade, int semesterTaken, int pointsGained,
                       double seventhExam, double twelfthExam, double cw, double finalExam, double totalMark) {
        this.userId = userId;
        this.sectionId = sectionId;
        this.grade = grade;
        this.semesterTaken = semesterTaken;
        this.pointsGained = pointsGained;
        this.seventhExam = seventhExam;
        this.twelfthExam = twelfthExam;
        this.cw = cw;
        this.finalExam = finalExam;
        this.totalMark = totalMark;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSemesterTaken() {
        return semesterTaken;
    }

    public void setSemesterTaken(int semesterTaken) {
        this.semesterTaken = semesterTaken;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(int pointsGained) {
        this.pointsGained = pointsGained;
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

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }


}
