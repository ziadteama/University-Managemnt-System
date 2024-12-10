package com.example.universitymanagementsystem;

public class CourseSchedule {
    private String scheduleId; // schedule_id (PK)
    private String sectionId;  // section_id
    private String dayOfWeek;  // day_of_week
    private String major;      // major
    private String period;     // period
    private String location;   // location
    private int userId;        // user_id (foreign key referencing the student or instructor)
    private String classType;  // class_type (lecture or tutorial)

    // Constructor to initialize all fields
    public CourseSchedule(String scheduleId, String sectionId, String dayOfWeek, String major,
                          String period, String location, int userId, String classType) {
        this.scheduleId = scheduleId;
        this.sectionId = sectionId;
        this.dayOfWeek = dayOfWeek;
        this.major = major;
        this.period = period;
        this.location = location;
        this.userId = userId;
        this.classType = classType;
    }

    // Getters and setters for all fields

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    // Optional: Override toString() for better logging/representation
    @Override
    public String toString() {
        return "CourseSchedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", major='" + major + '\'' +
                ", period='" + period + '\'' +
                ", location='" + location + '\'' +
                ", userId=" + userId +
                ", classType='" + classType + '\'' +
                '}';
    }
}
