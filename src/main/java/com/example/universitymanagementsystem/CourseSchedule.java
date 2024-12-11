package com.example.universitymanagementsystem;

import java.util.Map;

public class CourseSchedule {
    private String scheduleId; // schedule_id (PK)
    private String sectionId;  // section_id
    private String dayOfWeek;  // day_of_week
    private String major;      // major
    private String courseName;
    // Change period to integer
    private int period;        // Updated type for period

    private String location;   // location
    private int userId;        // user_id (foreign key referencing the student or instructor)
    private String classType;  // class_type (lecture or tutorial)

    // Static mapping for period names to integers
    private static final Map<String, Integer> PERIOD_MAPPING = Map.of(
            "first", 1,
            "second", 2,
            "third", 3,
            "fourth", 4,
            "fifth", 5,
            "sixth", 6
    );

    // Constructor to initialize all fields
    public CourseSchedule(String scheduleId,String courseName, String sectionId, String dayOfWeek, String major,
                          int period, String location, int userId, String classType) {
        this.scheduleId = scheduleId;
        this.sectionId = sectionId;
        this.dayOfWeek = dayOfWeek;
        this.major = major;
        this.period = period; // Accept numeric period directly
        this.location = location;
        this.userId = userId;
        this.classType = classType;
        this.courseName = courseName;
    }

    // Method to convert string period to integer
    public void setPeriodFromString(String periodName) {
        if (PERIOD_MAPPING.containsKey(periodName.toLowerCase())) {
            this.period = PERIOD_MAPPING.get(periodName.toLowerCase());
        } else {
            throw new IllegalArgumentException("Invalid period name: " + periodName);
        }
    }

    // Method to get period as a string (optional)
    public String getPeriodAsString() {
        return PERIOD_MAPPING.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == this.period)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("Invalid");
    }

    // Getters and setters

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getCourseName() {
        return courseName;
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

    // Updated getter and setter for period
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        if (period >= 1 && period <= 6) {
            this.period = period;
        } else {
            throw new IllegalArgumentException("Period must be between 1 and 6: " + period);
        }
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
                ", period=" + period +
                ", location='" + location + '\'' +
                ", userId=" + userId +
                ", classType='" + classType + '\'' +
                '}';
    }
}