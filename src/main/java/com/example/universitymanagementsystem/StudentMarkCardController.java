package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentMarkCardController {
    @FXML
    private Label courseIdLabel;

    @FXML
    private Label courseNameLabel;

    @FXML
    private Label seventhWeekMarkLabel;

    @FXML
    private Label twelfthWeekMarkLabel;

    @FXML
    private Label courseWorkMarkLabel;

    @FXML
    private Label finalMarkLabel;

    @FXML
    private Label gradeLabel;

    /**
     * Populates the card with data for a specific course and its marks.
     *
     * @param studentMarks The data for the student's marks in a course.
     */
    public void setData(StudentMarks studentMarks) {
        courseIdLabel.setText(studentMarks.getCourseId());
        courseNameLabel.setText(studentMarks.getCourseName());
        seventhWeekMarkLabel.setText(String.format("%.2f", studentMarks.getSeventhExam()));
        twelfthWeekMarkLabel.setText(String.format("%.2f", studentMarks.getTwelfthExam()));
        courseWorkMarkLabel.setText(String.format("%.2f", studentMarks.getCw()));
        finalMarkLabel.setText(String.format("%.2f", studentMarks.getFinalExam()));
        gradeLabel.setText(studentMarks.getGrade());
    }
}
