package com.example.universitymanagementsystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentsDAO {
    private Connection connection;

    public EnrollmentsDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CanEnroll> getCanEnroll(int userId) {
        List<CanEnroll> courses = new ArrayList<>();

        String query = """
                    SELECT
                        c.course_name,
                        c.course_id AS course_code,
                        c.credit_hours,
                        dr.name AS lecturer_name,
                        ta.name AS tutor_name,
                        GROUP_CONCAT(DISTINCT CASE WHEN s.class_type = 'lecture' THEN CONCAT(s.day_of_week, ' ', s.period, ' at ', s.location) END) AS lecture_time,
                        GROUP_CONCAT(DISTINCT CASE WHEN s.class_type = 'tutorial' THEN CONCAT(s.day_of_week, ' ', s.period, ' at ', s.location) END) AS tutorial_time
                    FROM 
                        schedules s
                    JOIN 
                        sections sec ON s.section_id = sec.section_id
                    JOIN 
                        courses c ON sec.course_id = c.course_id
                    JOIN 
                        users dr ON sec.dr_id = dr.user_id AND dr.role = 'dr'
                    JOIN 
                        users ta ON sec.ta_id = ta.user_id AND ta.role = 'ta'
                    WHERE 
                        s.major = (SELECT major FROM users WHERE user_id = ?) -- Student's major
                        AND sec.period = 'Fall' -- Filter by semester
                        AND sec.year = 2022 -- Filter by year
                    GROUP BY 
                        c.course_name, c.course_id, c.credit_hours, dr.name, ta.name
                    ORDER BY 
                        c.course_id;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CanEnroll course = new CanEnroll();
                course.setCourseName(rs.getString("course_name"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCreditHours(rs.getInt("credit_hours"));
                course.setLecturerName(rs.getString("lecturer_name"));
                course.setTutorName(rs.getString("tutor_name"));
                course.setLectureTime(rs.getString("lecture_time")); // Merged lecture times
                course.setTutorialTime(rs.getString("tutorial_time")); // Merged tutorial times

                courses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Database error while fetching courses: " + e.getMessage());
        }

        return courses;
    }
}
