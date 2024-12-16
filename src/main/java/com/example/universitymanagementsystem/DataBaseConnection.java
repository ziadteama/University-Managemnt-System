package com.example.universitymanagementsystem;
import java.sql.*;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/universitymanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public String getPasswordByUserId(String userId) {
        String query = "SELECT pass FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("pass");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if user ID is not found
    }

    // Validate login and return user ID
    public int getUserId(String username, String password) {
        String query = "SELECT user_id FROM users WHERE user_id = ? AND pass = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return -1; // Return -1 if user is not found or login fails
    }

    // Get the security question by User ID
    public String getSecurityQuestionByUserId(String userId) {
        String query = "SELECT SecurityQuestions FROM users WHERE user_id = ?";
        try (Connection conn = getConnection()  ;
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("SecurityQuestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if user ID does not exist
    }

    // Verify the answer to the security question
    public boolean verifySecurityAnswer(String userId, String securityAnswer) {
        String query = "SELECT SecurityAnswers FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return securityAnswer.equalsIgnoreCase(rs.getString("SecurityAnswers"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false; // Return false if the answer is incorrect
    }
    public boolean updatePasswordByUserId(String userId, String newPassword) {
        String updateQuery = "UPDATE users SET pass = ? WHERE user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}



