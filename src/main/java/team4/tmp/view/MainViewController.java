package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    @FXML
    private void handleLogin() {
        navigateToTaskView();
    }

    @FXML
    private void handleRegister() {
        navigateToTaskView();
    }

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

    private void navigateToTaskView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TaskView.fxml"));
            BorderPane taskView = loader.load(); // Change VBox to BorderPane
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(taskView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}