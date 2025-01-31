package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TaskViewController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dueDateField;

    @FXML
    private void handleAddTask() {
        // Handle adding the task (e.g., save to database)
        System.out.println("Task Added: " + titleField.getText());
    }
}