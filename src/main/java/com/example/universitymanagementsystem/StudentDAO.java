package com.example.universitymanagementsystem;

import java.sql.*;

public class StudentDAO {
    private Connection connection;

    // Constructor to initialize the database connection
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to check if the student's credentials (user_id and password) are correct
    public boolean checkCredentials(int userId, String password) {
        String query = "SELECT * FROM students WHERE user_id = ? AND pass = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();  // If a row is returned, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a student by userId (studentId)
    public Student getStudentById(int userId) {
        // Prepare the queries for both users and students tables
        String userQuery = "SELECT * FROM users WHERE user_id = ?";
        String studentQuery = "SELECT * FROM students WHERE user_id = ?";

        try (PreparedStatement userStmt = connection.prepareStatement(userQuery);
             PreparedStatement studentStmt = connection.prepareStatement(studentQuery)) {

            // Set the user_id for the users table query
            userStmt.setInt(1, userId);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                // Retrieve user details from users table
                String name = userRs.getString("name");
                String email = userRs.getString("email");
                String role = userRs.getString("role");
                String major = userRs.getString("major");
                String gender = userRs.getString("gender");
                Date dot = userRs.getDate("dot");
                String phoneNumber = userRs.getString("phone_number");
                String password = userRs.getString("pass");

                // Now, set the user_id for the students table query
                studentStmt.setInt(1, userId);
                ResultSet studentRs = studentStmt.executeQuery();

                if (studentRs.next()) {
                    // Retrieve student-specific details from students table
                    double gpa = studentRs.getDouble("gpa");
                    int advisorId = studentRs.getInt("advisor_id");
                    String status = studentRs.getString("status");
                    int currentSemester = studentRs.getInt("current_semester");
                    String yearEntry = studentRs.getString("year_entry");

                    // Create and return the Student object with data from both tables
                    return new Student(
                            userId, name, email, role, major, gender, dot, phoneNumber, password,
                            gpa, advisorId, status, currentSemester, yearEntry
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no student is found
    }


    // Method to add a new student to the database
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (user_id, name, email, role, major, gender, dot, phone_number, pass) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getUserId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getRole());
            stmt.setString(5, student.getMajor());
            stmt.setString(6, student.getGender());
            stmt.setDate(7, new java.sql.Date(student.getDateOfTransaction().getTime()));
            stmt.setString(8, student.getPhoneNumber());
            stmt.setString(9, student.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row is inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update a student's information
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, role = ?, major = ?, gender = ?, dot = ?, phone_number = ?, pass = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getRole());
            stmt.setString(4, student.getMajor());
            stmt.setString(5, student.getGender());
            stmt.setDate(6, new java.sql.Date(student.getDateOfTransaction().getTime()));
            stmt.setString(7, student.getPhoneNumber());
            stmt.setString(8, student.getPassword());
            stmt.setInt(9, student.getUserId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row is updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a student by userId (studentId)
    public boolean deleteStudent(int userId) {
        String query = "DELETE FROM students WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row is deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

