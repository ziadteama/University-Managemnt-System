package com.example.universitymanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    // Map enum strings to integers
    private static final Map<String, Integer> PERIOD_MAPPING = Map.of(
            "first", 1,
            "second", 2,
            "third", 3,
            "fourth", 4,
            "fifth", 5,
            "sixth", 6
    );

    /**
     * Converts a period enum string to its corresponding integer value.
     * @param period The enum string value of the period (e.g., "first")
     * @return The corresponding integer value (e.g., 1 for "first")
     * @throws IllegalArgumentException if the period is invalid
     */
    private int periodStringToInt(String period) {
        if (PERIOD_MAPPING.containsKey(period.toLowerCase())) {
            return PERIOD_MAPPING.get(period.toLowerCase());
        } else {
            throw new IllegalArgumentException("Invalid period value from database: " + period);
        }
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
                try {
                    CourseSchedule schedule = new CourseSchedule(
                            rs.getString("schedule_id"),
                            rs.getString("section_id"),
                            rs.getString("day_of_week"),
                            rs.getString("major"),
                            periodStringToInt(rs.getString("period")), // Convert period to integer
                            rs.getString("location"),
                            rs.getInt("user_id"),
                            rs.getString("class_type")
                    );

                    schedules.add(schedule);
                } catch (IllegalArgumentException e) {
                    System.err.println("Failed to map period for schedule_id "
                            + rs.getString("schedule_id") + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return schedules;
    }
}
