package team4.tmp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;  // Updated to match MainView.fxml layout
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the main view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));

        // Load the layout from FXML
        VBox root = loader.load();  // Changed to VBox

        // Set the stage title
        primaryStage.setTitle("Task Management Portal");

        // Create the scene with the loaded layout
        Scene scene = new Scene(root, 800, 600);

        // Set the scene on the primary stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
