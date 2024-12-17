package com.example.universitymanagementsystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class EnrollmentsDAO {
    private Connection connection;

    public EnrollmentsDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CanEnroll> getCanEnroll(int userId) {
        List<CanEnroll> courses = new ArrayList<>();

        String query = "SELECT " +
                "c.course_name, " +
                "c.course_id AS course_code, " +
                "c.credit_hours, " +
                "dr.name AS lecturer_name, " +
                "ta.name AS tutor_name, " +
                "s.section_id AS section_id, " +
                "GROUP_CONCAT(DISTINCT CASE " +
                "WHEN s.class_type = 'lecture' THEN CONCAT(s.day_of_week, ' ', s.period, ' at ', s.location) " +
                "ELSE NULL " +
                "END ORDER BY s.period SEPARATOR ', ') AS lecture_time, " +
                "GROUP_CONCAT(DISTINCT CASE " +
                "WHEN s.class_type = 'tutorial' THEN CONCAT(s.day_of_week, ' ', s.period, ' at ', s.location) " +
                "ELSE NULL " +
                "END ORDER BY s.period SEPARATOR ', ') AS tutorial_time, " +
                "MAX(CASE WHEN s.class_type = 'lecture' THEN s.day_of_week ELSE NULL END) AS lecture_day_of_week, " +
                "MAX(CASE WHEN s.class_type = 'tutorial' THEN s.day_of_week ELSE NULL END) AS tutorial_day_of_week " +
                "FROM schedules s " +
                "JOIN sections sec ON s.section_id = sec.section_id " +
                "JOIN courses c ON sec.course_id = c.course_id " +
                "JOIN users dr ON sec.dr_id = dr.user_id AND dr.role = 'dr' " +
                "JOIN users ta ON sec.ta_id = ta.user_id AND ta.role = 'ta' " +
                "LEFT JOIN enrollments e ON e.section_id = s.section_id AND e.user_id = ? " +
                "LEFT JOIN courseprerequisites cp ON c.course_id = cp.course_id " +
                "LEFT JOIN enrollments ep ON ep.section_id IN ( " +
                "SELECT sec_prereq.section_id " +
                "FROM sections sec_prereq " +
                "JOIN courses prereq_course ON sec_prereq.course_id = cp.prerequisite_course_id " +
                "WHERE sec_prereq.section_id = ep.section_id " +
                "AND ep.user_id = ? " +
                ") " +
                "WHERE s.major = ( " +
                "SELECT major " +
                "FROM users " +
                "WHERE user_id = ? " +
                ") " +
                "AND sec.period = 'Fall' " +
                "AND sec.year = '2023' " +
                "AND ( " +
                "cp.prerequisite_course_id IS NULL OR " +
                "(ep.grade NOT IN ('U', 'W', 'F') AND ep.grade IS NOT NULL) " +
                ") " +
                "AND ( " +
                "e.section_id IS NULL OR " +
                "e.grade IN ('W', 'F', 'U') " +
                ") " +
                "GROUP BY c.course_name, c.course_id, c.credit_hours, dr.name, ta.name, s.section_id " +
                "ORDER BY c.course_id;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CanEnroll course = new CanEnroll();

                course.setSectionId(rs.getString("section_id"));
                course.setLectureDay(rs.getString("lecture_day_of_week"));
                course.setTutorialDay(rs.getString("tutorial_day_of_week"));
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


    public boolean insertEnrollments(int userId, List<String> sectionIds, Date enrollmentDate, int semesterTaken) {
        String insertQuery = "INSERT INTO enrollments (user_id, section_id, enrollment_date,semester_taken) " +
                "VALUES (?, ?, ?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (String sectionId : sectionIds) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, sectionId);
                preparedStatement.setDate(3, enrollmentDate);
                preparedStatement.setInt(4, semesterTaken);

                // Execute the insert for the current section ID
                preparedStatement.executeUpdate();
            }
            return true; // Enrollment successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Enrollment failed
        }
    }
    public boolean isAlreadyEnrolled(int userId) {
        String query = "SELECT * FROM enrollments WHERE user_id = ? ;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Returns true if a row is found, indicating the student is already enrolled
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if an error occurs
        }
    }
    public void dropEnrollments(int userId) {
        String query = "DELETE FROM enrollments WHERE user_id = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
