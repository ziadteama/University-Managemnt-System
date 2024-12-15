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



public class HelloController {

    @FXML
    private TextField usernameField;

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

    private int loggedInUserId;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (isValidInput(username, password)) {
            loggedInUserId = databaseConnection.getUserId(username, password);
            if (loggedInUserId != -1) {
                messageLabel.setText("Login successful!");
                // Call this method to open the "Doctor Course Display" screen
                showDoctorCourseDisplay(loggedInUserId);
            } else {
                messageLabel.setText("Invalid username or password.");
                loggedInUserId = -1;
            }
        } else {
            messageLabel.setText("Please fill in both fields.");
        }
    }

    private boolean isValidInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void showDoctorCourseDisplay(int doctorId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorCourseDisplay.fxml"));
            Parent root = loader.load();

            DoctorCourseDisplayController controller = loader.getController();
            if (controller != null) {
                controller.initialize(doctorId);
            } else {
                throw new IllegalStateException("Controller could not be retrieved. Check FXML configuration.");
            }

            Stage stage = new Stage();
            stage.setTitle("Doctor Course Display");
            stage.setScene(new Scene(root, 1250, 400));
            // Disable the maximize button
            stage.setResizable(false);
            stage.show();

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
                String password = databaseConnection.getPasswordByUserId(userId);
                if (password != null) {
                    forgotPasswordResultLabel.setText("Your password is: " + password);
                    forgotPasswordResultLabel.setVisible(true);
                } else {
                    forgotPasswordResultLabel.setText("Password could not be retrieved.");
                    forgotPasswordResultLabel.setVisible(true);
                }
            } else {
                forgotPasswordResultLabel.setText("Incorrect answer. Please try again.");
                forgotPasswordResultLabel.setVisible(true);
            }
        } else {
            forgotPasswordResultLabel.setText("Please fill in all fields.");
            forgotPasswordResultLabel.setVisible(true);
        }
    }
}
