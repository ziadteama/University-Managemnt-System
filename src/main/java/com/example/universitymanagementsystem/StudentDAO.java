package com.example.universitymanagementsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    // Constructor to initialize the database connection
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean checkCredentials(int userId, String password) {
        String query = "SELECT * FROM users WHERE user_id = ? AND pass = ?";
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
        // Prepare the queries for both users, students, and enrollments tables
        String userQuery = "SELECT * FROM users WHERE user_id = ?";
        String studentQuery = "SELECT * FROM students WHERE student_id = ?";
        String enrollmentsQuery = "SELECT * FROM enrollments WHERE user_id = ?";

        try (PreparedStatement userStmt = connection.prepareStatement(userQuery);
             PreparedStatement studentStmt = connection.prepareStatement(studentQuery);
             PreparedStatement enrollmentsStmt = connection.prepareStatement(enrollmentsQuery)) {

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
                Date dob = userRs.getDate("dob");
                String phoneNumber = userRs.getString("phone_number");

                // Now, set the user_id for the students table query
                studentStmt.setInt(1, userId);
                ResultSet studentRs = studentStmt.executeQuery();

                if (studentRs.next()) {
                    // Retrieve student-specific details from students table
                    double gpa = studentRs.getDouble("gpa");
                    int advisorId = studentRs.getInt("advisor_id");
                    String status = studentRs.getString("status");
                    int currentSemester = studentRs.getInt("current_semester");

                    // Create the Student object
                    Student student = new Student(
                            userId, name, email, role, major, gender, dob, phoneNumber,
                            gpa, advisorId, status, currentSemester);

                    // Fetch the enrollments and set them to the student object
                    enrollmentsStmt.setInt(1, userId);
                    ResultSet enrollmentsRs = enrollmentsStmt.executeQuery();
                    List<Enrollments> enrollments = new ArrayList<>();

                    while (enrollmentsRs.next()) {
                        // Retrieve enrollment details from the enrollments table
                        String sectionId = enrollmentsRs.getString("section_id");
                        String grade = enrollmentsRs.getString("grade");
                        int semesterTaken = enrollmentsRs.getInt("semester_taken");
                        int pointsGained = enrollmentsRs.getInt("points_gained");
                        double seventhExam = enrollmentsRs.getDouble("seventh_exam");
                        double twelfthExam = enrollmentsRs.getDouble("twelfth_exam");
                        double cw = enrollmentsRs.getDouble("cw");
                        double finalExam = enrollmentsRs.getDouble("final_exam");
                        double totalMark = enrollmentsRs.getDouble("total_mark");

                        // Create the Enrollment object and add it to the list
                        Enrollments enrollment = new Enrollments(
                                userId, sectionId, grade, semesterTaken, pointsGained,
                                seventhExam, twelfthExam, cw, finalExam, totalMark
                        );
                        enrollments.add(enrollment);
                    }

                    // Set the enrollments to the student object
                    student.setEnrollments(enrollments);

                    return student; // Return the student with the enrollments set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no student is found
    }

    // ... existing methods ...

    public List<StudentMarks> getStudentMarks(int userId, String period, int year) {
        String query = "SELECT " +
                "e.grade, " +
                "e.seventh_exam, " +
                "e.twelfth_exam, " +
                "e.cw, " +
                "e.final_exam, " +
                "s.period, " +
                "s.year, " +
                "c.course_name, " +
                "c.course_id " +
                "FROM enrollments e " +
                "JOIN sections s ON e.section_id = s.section_id " +
                "JOIN courses c ON s.course_id = c.course_id " +
                "WHERE e.user_id = ? AND s.period = ? AND s.year = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, period);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();

            List<StudentMarks> studentMarks = new ArrayList<>();

            while (rs.next()) {
                // Retrieve marks details from the result set
                String grade = rs.getString("grade");
                double seventhExam = rs.getDouble("seventh_exam");
                double twelfthExam = rs.getDouble("twelfth_exam");
                double cw = rs.getDouble("cw");
                double finalExam = rs.getDouble("final_exam");
                String courseName = rs.getString("course_name");
                String courseId = rs.getString("course_id");

                // Create the StudentMarks object and add it to the list
                StudentMarks studentMark = new StudentMarks(
                        grade, seventhExam, twelfthExam, cw, finalExam,
                        period, year, courseName, courseId
                );
                studentMarks.add(studentMark);
            }

            return studentMarks; // Return the list of student marks
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no student marks are found
    }

    public List<Semester> getSemestersTaken(int userId) {
        String query = "SELECT DISTINCT " +
                "s.year, " +
                "s.period " +
                "FROM enrollments e " +
                "JOIN sections s ON e.section_id = s.section_id " +
                "WHERE e.user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Semester> semesters = new ArrayList<>();

            while (rs.next()) {
                // Retrieve semester details from the result set
                int year = rs.getInt("year");
                String period = rs.getString("period");

                // Create the Semester object and add it to the list
                Semester semester = new Semester(year, period);
                semesters.add(semester);
            }

            return semesters; // Return the list of semesters
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no semesters are found
    }

    public List<Student> getStudentsByTaId(int taId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = """
                    SELECT u.user_id, u.name
                    FROM users u
                    JOIN students s ON u.user_id = s.student_id
                    WHERE s.advisor_id = ? AND u.role = 'student'
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, taId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            String studentName = resultSet.getString("student_name");
            students.add(new Student(userId, studentName));
        }

        return students;
    }
}

