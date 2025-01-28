package team4.tmp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import team4.tmp.model.Task;
import team4.tmp.service.TaskService;

public class TaskController {

    @FXML
    private TableView<Task> taskTable;

    private TaskService taskService = new TaskService();

    // Handle delete task operation
    @FXML
    private void handleDeleteTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            boolean success = taskService.deleteTask(selectedTask.getId());
            if (success) {
                taskTable.getItems().remove(selectedTask); // Remove from table
                showAlert("Task Deleted", "The task has been successfully deleted!", AlertType.INFORMATION);
            } else {
                showAlert("Error", "Task deletion failed", AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a task to delete.", AlertType.WARNING);
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
