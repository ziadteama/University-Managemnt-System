package com.example.universitymanagementsystem;

import java.sql.*;
import java.util.Date;

public class UserDAO {

    private Connection connection;
    public StudentDAO studentDAO ;

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
}
