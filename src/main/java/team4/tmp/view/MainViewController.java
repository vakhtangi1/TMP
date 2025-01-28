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
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dueDateField;
    @FXML
    private TextField priorityField;
    @FXML
    private TextField statusField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.", AlertType.ERROR);
        } else {
            // Handle login logic here (verify credentials)
            System.out.println("Logged in with " + username);
        }
    }

    @FXML
    private void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            // Proceed with registration (e.g., save user to database)
            System.out.println("User registered: " + username);
        } else {
            showAlert("Registration Error", "Passwords do not match.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleCreateTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String dueDate = dueDateField.getText();
        String priority = priorityField.getText();
        String status = statusField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            showAlert("Task Creation Error", "Please fill out all required fields.", AlertType.ERROR);
        } else {
            // Handle task creation logic here (e.g., save task to database)
            System.out.println("Task created: " + title);
        }
    }

    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
