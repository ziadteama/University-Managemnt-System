package com.example.universitymanagementsystem;


public class CanEnroll {
    private String sectionId;
    private String courseName;
    private String courseCode;
    private int creditHours;
    private String lecturerName;
    private String tutorName;
    private String lectureDay;
    private String tutorialDay;
    private String lectureTime;
    private String tutorialTime;

    public String getLectureDay(){
        return this.lectureDay;
    }
    public void setLectureDay(String lectureDay){
        this.lectureDay = lectureDay;
    }
    public String getTutorialDay(){
        return this.tutorialDay;
    }
    public void setTutorialDay(String tutorialDay){

        this.tutorialDay = tutorialDay;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

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
    public String getLectureTime(){
        return this.lectureTime;
    }
    public void setLectureTime(String lectureTime){
        this.lectureTime = lectureTime;
    }
    public String getTutorialTime(){
        return this.tutorialTime;
    }
    public void setTutorialTime(String tutorialTime){
        this.tutorialTime = tutorialTime;
    }

    // Period details management

}
