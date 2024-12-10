package com.example.universitymanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to get schedule details by section_id
    public List<CourseSchedule> getScheduleBySectionId(String sectionId) {
        List<CourseSchedule> schedules = new ArrayList<>();

        // SQL query to get schedule details based on section_id
        String query = "SELECT " +
                "    s.schedule_id, " +
                "    s.section_id, " +
                "    s.day_of_week, " +
                "    s.major, " +
                "    s.period, " +
                "    s.location, " +
                "    s.user_id, " +
                "    s.class_type, " +
                "    u.name AS lecturer_or_tutor_name, " +
                "    s.location " +
                "FROM " +
                "    schedules s " +
                "JOIN " +
                "    users u " +
                "ON " +
                "    s.user_id = u.user_id " +
                "WHERE " +
                "    s.section_id = ? " +
                "ORDER BY " +
                "    s.schedule_id;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sectionId);  // Set the section_id parameter
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                CourseSchedule schedule = new CourseSchedule(
                        rs.getString("schedule_id"),
                        rs.getString("section_id"),
                        rs.getString("day_of_week"),
                        rs.getString("major"),
                        rs.getString("period"),
                        rs.getString("location"),
                        rs.getInt("user_id"),
                        rs.getString("class_type")
                );

                schedules.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return schedules;
    }
}
