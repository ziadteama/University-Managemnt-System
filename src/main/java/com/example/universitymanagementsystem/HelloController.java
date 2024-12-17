package com.example.universitymanagementsystem;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.SQLException;


public class HelloController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button submitPasswordButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField forgotPasswordUserIdField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button forgotPasswordButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label securityQuestionLabel;

    @FXML
    private TextField securityAnswerField;

    @FXML
    private Button verifyButton;

    @FXML
    private Label forgotPasswordResultLabel;

    private final DataBaseConnection databaseConnection = new DataBaseConnection();

    private UserDAO userDAO;

    public HelloController() {
        try {
            // Initialize the database connection
            Connection dbConnection = DataBaseConnection.getConnection();
            this.userDAO = new UserDAO(dbConnection); // Pass the connection to DAO
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            try {
                handleLogin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleLogin() throws SQLException {
        int userId = Integer.parseInt(usernameField.getText().trim());
        String password = passwordField.getText().trim();

        if (isValidInput(userId, password)) {
            User loggedInUser = userDAO.checkCredentials(userId, password);

            if (loggedInUser != null) {
                // Set the logged-in user in the session
                UserSession.getInstance().setLoggedInUser(loggedInUser);

                messageLabel.setText("Login successful!");

                // Redirect based on the role
                switch (loggedInUser.getRole().toLowerCase()) {
                    case "student":
                        showStudentDashboard();
                        break;
                    case "dr":
                        showDoctorDashboard();
                        break;
                    case "ta":
                       showDoctorDashboard();
                        break;
                    default:
                        messageLabel.setText("Unknown role.");
                }
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        } else {
            messageLabel.setText("Please fill in both fields.");
        }
    }


    private boolean isValidInput(int userId, String password) {
        return userId != 0 && !password.isEmpty();
    }

    private void showStudentDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("studentmenu.fxml"));
            Parent root = loader.load();

            // Pass the current student to the StudentMenuController
            StudentMenuController studentMenuController = loader.getController();

            // The controller can now access the student from the session
            studentMenuController.initialize(); // This will be automatically called

            // Redirect to the student dashboard/menu
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene dashboardScene = new Scene(root);
            stage.setScene(dashboardScene);
            stage.setTitle("Welcome to the Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDoctorDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorCourseDisplay.fxml"));
            Parent root = loader.load();

            DoctorCourseDisplayController controller = loader.getController();
            if (controller != null) {
                controller.initialize();
            } else {
                throw new IllegalStateException("Controller could not be retrieved. Check FXML configuration.");
            }

            // Create a new Scene based on the loaded FXML
            Scene scene = new Scene(root);

            // Create the Stage
            Stage stage = new Stage();
            stage.setTitle("Doctor Course Display");

            // Set the scene for the stage
            stage.setScene(scene);

            // Wait for the scene to be rendered, then get the preferred width
            stage.show();

            // Get the preferred width of the content and set it as the width for the Stage
            double contentWidth = scene.getRoot().prefWidth(-1);  // -1 means "use the preferred width of the root node"
            stage.setWidth(contentWidth);  // Set stage width based on the content's preferred width
            stage.setHeight(470);  // Set fixed height

            // Set minimum width (optional, you can adjust this as needed)
            stage.setMinWidth(600);

            // Close the login stage
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error loading the next screen. Please try again.");
        }
    }




    @FXML
    private void handleForgotPassword() {
        // Show the User ID input field and Next button
        forgotPasswordUserIdField.setVisible(true);
        nextButton.setVisible(true);
        forgotPasswordResultLabel.setVisible(false); // Hide result initially

        // Optionally clear the fields
        forgotPasswordUserIdField.clear();
        forgotPasswordResultLabel.setText("");
    }

    @FXML
    private void handleNextButton() {
        String userId = forgotPasswordUserIdField.getText().trim();

        if (!userId.isEmpty()) {
            String securityQuestion = databaseConnection.getSecurityQuestionByUserId(userId);

            if (securityQuestion != null) {
                securityQuestionLabel.setText(securityQuestion);
                securityQuestionLabel.setVisible(true);
                securityAnswerField.setVisible(true);
                verifyButton.setVisible(true);
                nextButton.setVisible(false); // Hide Next button
            } else {
                forgotPasswordResultLabel.setText("No security question found for this User ID.");
                forgotPasswordResultLabel.setVisible(true);
            }
        } else {
            forgotPasswordResultLabel.setText("Please enter a valid User ID.");
            forgotPasswordResultLabel.setVisible(true);
        }
    }

    @FXML
    private void handleVerifyPassword() {
        String userId = forgotPasswordUserIdField.getText().trim();
        String securityAnswer = securityAnswerField.getText().trim();

        if (!userId.isEmpty() && !securityAnswer.isEmpty()) {
            boolean isAnswerCorrect = databaseConnection.verifySecurityAnswer(userId, securityAnswer);

            if (isAnswerCorrect) {
                // Show fields to change the password
                newPasswordField.setVisible(true);
                confirmPasswordField.setVisible(true);
                submitPasswordButton.setVisible(true);

                forgotPasswordResultLabel.setText("Security answer correct! Enter your new password.");
                forgotPasswordResultLabel.setVisible(true);

                // Add an event handler for the Submit button
                submitPasswordButton.setOnAction(event -> handleChangePassword(userId));
            } else {
                forgotPasswordResultLabel.setText("Incorrect answer. Please try again.");
                forgotPasswordResultLabel.setVisible(true);
            }
        } else {
            forgotPasswordResultLabel.setText("Please fill in all fields.");
            forgotPasswordResultLabel.setVisible(true);
        }
    }
    private void handleChangePassword(String userId) {
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                boolean updateSuccess = databaseConnection.updatePasswordByUserId(userId, newPassword);
                if (updateSuccess) {
                    forgotPasswordResultLabel.setText("Password updated successfully! Please log in with your new password.");
                    forgotPasswordResultLabel.setVisible(true);

                    // Optionally hide the password change fields
                    newPasswordField.setVisible(false);
                    confirmPasswordField.setVisible(false);
                    submitPasswordButton.setVisible(false);
                } else {
                    forgotPasswordResultLabel.setText("Error updating password. Please try again.");
                    forgotPasswordResultLabel.setVisible(true);
                }
            } else {
                forgotPasswordResultLabel.setText("Passwords do not match. Please try again.");
                forgotPasswordResultLabel.setVisible(true);
            }
        } else {
            forgotPasswordResultLabel.setText("Both fields are required.");
            forgotPasswordResultLabel.setVisible(true);
        }
    }

}
