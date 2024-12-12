package com.example.universitymanagementsystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends User {
    private double gpa;               // gpa (decimal(3,2))
    private int advisorId;            // advisor_id (foreign key)
    private String status;            // status (enum: 'active', 'suspended', 'withdrawn')
    private int currentSemester;      // current_semester (int)
    private String yearEntry;         // year_entry (varchar(4))
    private List<Enrollments> enrollments;
    private List<Semester> semesters;

    // Constructor
    public Student(int userId, String name, String email, String role, String major, String gender,
                   Date dateOfEntry, String phoneNumber, String password, double gpa,
                   int advisorId, String status, int currentSemester, String yearEntry) {
        super(userId, name, email, role, major, gender, dateOfEntry, phoneNumber, password);
        this.gpa = gpa;
        this.advisorId = advisorId;
        this.status = status;
        this.currentSemester = currentSemester;
        this.yearEntry = yearEntry;
        this.enrollments = new ArrayList<>();
        this.semesters = new ArrayList<>();
    }

    // Getters and Setters
    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getYearEntry() {
        return yearEntry;
    }

    public void setYearEntry(String yearEntry) {
        this.yearEntry = yearEntry;
    }
    public void setEnrollments(List<Enrollments> enrollments) {
        this.enrollments = enrollments;
    }
    public List<Enrollments> getEnrollments() {
        return enrollments;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + getUserId() +  // Inherited from User class
                ", gpa=" + gpa +
                ", advisorId=" + advisorId +
                ", status='" + status + '\'' +
                ", currentSemester=" + currentSemester +
                ", yearEntry='" + yearEntry + '\'' +
                ", user=" + super.toString() + // Include User information
                '}';
    }
    public List<Semester> getSemesters() {
        return semesters;
    }
    public List<String> getSemestersString() {
        List<String> semestersString = new ArrayList<>();
        for (Semester s : semesters) {
            semestersString.add(s.getYear() + " " + s.getPeriod());
        }
        return semestersString;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
}

