package com.example.universitymanagementsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseService {

    private DataBaseConnection databaseConnection = new DataBaseConnection();

    public void displayCoursesMatchedWithDrId(int drId) {

        // SQL query with join to fetch needed course information and doctor name
        String query = "SELECT sections.year, sections.period, sections.course_id, sections.section_id, users.name AS doctor_name " +
                "FROM sections " +
                "JOIN users ON sections.dr_id = users.user_id " +
                "WHERE sections.dr_id = ? AND sections.year = '2023' AND sections.period = 'Fall'";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, drId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Fetch and store doctor's name
            String doctorName = null;
            if (resultSet.next()) {
                doctorName = resultSet.getString("doctor_name");
                System.out.println("Courses for Doctor " + doctorName + " in Fall 2023:");

                // Print details for the first record
                String year = resultSet.getString("year");
                String period = resultSet.getString("period");
                String courseId = resultSet.getString("course_id");
                String sectionId = resultSet.getString("section_id");

                System.out.println("- Year: " + year + ", Period: " + period + ", Course ID: " + courseId + ", Section ID: " + sectionId);
            }

            // Iterate through and print the rest of the courses
            while (resultSet.next()) {
                String year = resultSet.getString("year");
                String period = resultSet.getString("period");
                String courseId = resultSet.getString("course_id");
                String sectionId = resultSet.getString("section_id");

                System.out.println("- Year: " + year + ", Period: " + period + ", Course ID: " + courseId + ", Section ID: " + sectionId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}