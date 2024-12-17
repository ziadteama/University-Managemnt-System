package com.example.universitymanagementsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    private Connection connection;
    public StudentDAO studentDAO;

    public UserDAO(Connection connection) {
        this.connection = connection;
        this.studentDAO = new StudentDAO(connection);
    }

    public User checkCredentials(int userId, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ? AND pass = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            String role = rs.getString("role");
            String major = rs.getString("major");
            String gender = rs.getString("gender");
            String email = rs.getString("email");
            Date dob = rs.getDate("dob");
            String phoneNumber = rs.getString("phone_number");

            // Instantiate the appropriate user subclass based on role
            switch (role) {
                case "student" -> {
                    return studentDAO.getStudentById(userId);
                }
                case "ta" -> {
                    return new Tutor(userId, name, role, major, email, gender, dob, phoneNumber);
                }
                case "dr" -> {
                    return new Doctor(userId, name, role, major, email, gender, dob, phoneNumber);
                }
            }
        }

        return null; // No user found with matching credentials
    }

    public List<Course> getCoursesForTeacher(int userid) throws SQLException {
        String role = UserSession.getInstance().getLoggedInUser().getRole();
        List<Course> courses = new ArrayList<>();
        String query = null;

        if (role.equals("dr")) {
            query = "SELECT sections.course_id, sections.section_id, users.name AS doctor_name, courses.course_name " +
                    "FROM sections " +
                    "JOIN users ON sections.dr_id = users.user_id " +
                    "JOIN courses ON sections.course_id = courses.course_id " +
                    "WHERE sections.dr_id = ? AND sections.year = '2023' AND sections.period = 'Fall'";
        } else {
            query = "SELECT sections.course_id, sections.section_id, users.name AS doctor_name, courses.course_name " +
                    "FROM sections " +
                    "JOIN users ON sections.ta_id = users.user_id " +
                    "JOIN courses ON sections.course_id = courses.course_id " +
                    "WHERE sections.ta_id = ? AND sections.year = '2023' AND sections.period = 'Fall'";
        }


        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, userid);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String courseName = resultSet.getString("course_name");
            String courseId = resultSet.getString("course_id");
            String sectionId = resultSet.getString("section_id");
            String doctorName = resultSet.getString("doctor_name");

            courses.add(new Course(courseName, courseId, sectionId, doctorName));
        }


        return courses;
    }
}
