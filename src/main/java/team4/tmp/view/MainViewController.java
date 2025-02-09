package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import team4.tmp.model.User;

public class MainViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label formTitle;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label toggleText;
    @FXML
    private VBox loginRegisterSection;
    @FXML
    private Button submitButton;
    @FXML
    private Button toggleButton;

    private String authenticatedUserId; // Store the authenticated user's ID

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Initialize passwordEncoder

    // Handle Login Request
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password are required.");
            return;
        }

        try {
            // Encode the username to handle spaces and special characters
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
            String url = BASE_URL + "/user/username/" + encodedUsername;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the response to get the user details
                String responseBody = response.body();
                User user = objectMapper.readValue(responseBody, User.class);

                // Validate password
                if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                    // Passwords match, proceed to authenticate the user
                    authenticatedUserId = String.valueOf(user.getId()); // Converts Long to String
                    navigateToTaskView();
                } else {
                    // Passwords don't match, show error
                    showError("Invalid username or password.");
                }
            } else {
                showError("Invalid username or password.");
            }
        } catch (Exception e) {
            showError("An error occurred while logging in.");
            e.printStackTrace();
        }
    }

    // Handle Register Request
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        try {
            String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                // Parse the response to get the user ID
                String responseBody = response.body();
                authenticatedUserId = objectMapper.readTree(responseBody).get("id").asText();
                navigateToTaskView();
            } else {
                showError("Registration failed. Please try again.");
            }
        } catch (Exception e) {
            showError("An error occurred while registering.");
            e.printStackTrace();
        }
    }

    // Switch to Login Form
    @FXML
    private void showLogin() {
        formTitle.setText("Login");
        usernameLabel.setText("Username:");
        passwordLabel.setText("Password:");
        confirmPasswordLabel.setVisible(false);
        confirmPasswordLabel.setManaged(false);
        confirmPasswordField.setVisible(false);
        confirmPasswordField.setManaged(false);
        submitButton.setText("Login");
        submitButton.setOnAction(event -> handleLogin());
        toggleText.setText("Don't have an account?");
        toggleButton.setText("Register");
        toggleButton.setOnAction(event -> showRegister());
    }

    // Switch to Register Form
    @FXML
    private void showRegister() {
        formTitle.setText("Register");
        usernameLabel.setText("Username:");
        passwordLabel.setText("Password:");
        confirmPasswordLabel.setVisible(true);
        confirmPasswordLabel.setManaged(true);
        confirmPasswordField.setVisible(true);
        confirmPasswordField.setManaged(true);
        submitButton.setText("Register");
        submitButton.setOnAction(event -> handleRegister());
        toggleText.setText("Do you have an account?");
        toggleButton.setText("Login");
        toggleButton.setOnAction(event -> showLogin());
    }

    // Navigate to Task View
    private void navigateToTaskView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TaskView.fxml"));
            BorderPane taskView = loader.load();
            TaskViewController taskViewController = loader.getController();
            taskViewController.setAuthenticatedUserId(authenticatedUserId); // Pass the user ID to TaskViewController
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(taskView));
        } catch (Exception e) {
            showError("An error occurred while navigating to the task view.");
            e.printStackTrace();
        }
    }

    // Show Error Dialog
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}