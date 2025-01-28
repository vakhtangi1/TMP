package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import team4.tmp.model.Task;  // Updated import for Task model
import java.time.LocalDate;  // For handling date input

public class MainViewController {

    // Login Section
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    // Register Section
    @FXML
    private TextField registerUsernameField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    // Task Creation Section
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

    // Login button handler
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Simulate a login check
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Information");
        alert.setHeaderText(null);
        alert.setContentText("Login successful for user: " + username);
        alert.showAndWait();
    }

    // Register button handler
    @FXML
    public void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();
        } else {
            // Simulate a successful registration
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registration Information");
            alert.setHeaderText(null);
            alert.setContentText("Registration successful for user: " + username);
            alert.showAndWait();
        }
    }

    // Create Task button handler
    @FXML
    public void handleCreateTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String dueDate = dueDateField.getText();
        String priority = priorityField.getText();
        String status = statusField.getText();

        try {
            // Validate and parse the due date
            if (dueDate == null || dueDate.trim().isEmpty()) {
                throw new IllegalArgumentException("Due date cannot be empty");
            }

            // Converting String to LocalDate (added validation)
            LocalDate dueDateParsed = LocalDate.parse(dueDate);  // This assumes the format is YYYY-MM-DD

            Task task = new Task();  // Create a Task object
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDateParsed);  // Set the due date
            task.setPriority(priority);
            task.setStatus(status);

            // Simulate creating a task
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Task Created");
            alert.setHeaderText(null);
            alert.setContentText("Task created successfully!\nTitle: " + title);
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle empty due date error
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Due date cannot be empty.");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle invalid date format or other errors
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid date format. Please use YYYY-MM-DD.");
            alert.showAndWait();
        }
    }
}
