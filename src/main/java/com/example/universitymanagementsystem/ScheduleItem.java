package com.example.universitymanagementsystem;

public class ScheduleItem {
    private String day;
    private String period1;
    private String period2;
    private String period3;
    private String period4;
    private String period5;
    private String period6;
    private String classType;
    private String teacherName;

    // Constructor
    public ScheduleItem(String day, String period1, String period2, String period3, String period4, String period5, String period6) {
        this.day = day;
        this.period1 = period1;
        this.period2 = period2;
        this.period3 = period3;
        this.period4 = period4;
        this.period5 = period5;
        this.period6 = period6;
    }

    // Getters
    public String getDay() {
        return day;
    }

    public String getPeriod1() {
        return period1;
    }

    public String getPeriod2() {
        return period2;
    }

    public String getPeriod3() {
        return period3;
    }

    public String getPeriod4() {
        return period4;
    }

    public String getPeriod5() {
        return period5;
    }

    public String getPeriod6() {
        return period6;
    }

    public String getClassType() {
        return classType;
    }

    public String getTeacherName() {
        return teacherName;
    }

    // Setters
    public void setDay(String day) {
        this.day = day;
    }

    public void setPeriod1(String period1) {
        this.period1 = period1;
    }

    public void setPeriod2(String period2) {
        this.period2 = period2;
    }

    public void setPeriod3(String period3) {
        this.period3 = period3;
    }

    public void setPeriod4(String period4) {
        this.period4 = period4;
    }

    public void setPeriod5(String period5) {
        this.period5 = period5;
    }

    public void setPeriod6(String period6) {
        this.period6 = period6;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setTeacherName(String teacherName) {
        if ("Lecture".equalsIgnoreCase(this.classType)) {
            this.teacherName = "Lecturer: " + teacherName;
        } else {
            this.teacherName = "Tutor: " + teacherName;
        }
    }
}
