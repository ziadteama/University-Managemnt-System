package com.example.universitymanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    private DataBaseConnection databaseConnection = new DataBaseConnection();

    public List<Course> getCoursesForDoctor(int doctorId) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT sections.course_id, sections.section_id, users.name AS doctor_name, courses.course_name " +
                "FROM sections " +
                "JOIN users ON sections.dr_id = users.user_id " +
                "JOIN courses ON sections.course_id = courses.course_id " +
                "WHERE sections.dr_id = ? AND sections.year = '2023' AND sections.period = 'Fall'";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String courseId = resultSet.getString("course_id");
                String sectionId = resultSet.getString("section_id");
                String doctorName = resultSet.getString("doctor_name");

                courses.add(new Course(courseName, courseId, sectionId, doctorName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }

}
