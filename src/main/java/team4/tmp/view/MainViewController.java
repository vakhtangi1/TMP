package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField registerUsernameField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Add your login logic here
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter both username and password.");
        } else {
            // Perform login
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        }
    }

    @FXML
    private void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Add your registration logic here
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Registration Error", "Please fill in all fields.");
        } else if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Registration Error", "Passwords do not match.");
        } else {
            // Perform registration
            showAlert(AlertType.INFORMATION, "Registration Successful", "User " + username + " registered successfully!");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}