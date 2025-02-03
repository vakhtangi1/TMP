package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TaskViewController {
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
    private ListView<Task> taskListView;
    @FXML
    private ListView<Task> finishedTaskListView;
    @FXML
    private Label taskTitleLabel;
    @FXML
    private TextArea taskDescriptionLabel;
    @FXML
    private TextField taskDueDateField;
    @FXML
    private TextField taskPriorityField;
    @FXML
    private TextField taskStatusField;
    @FXML
    private ImageView userImageView; // User image view (added)
    @FXML
    private Label usernameLabel;  // Label to show the username (added)

    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedTaskList = FXCollections.observableArrayList();

    private Task selectedTask;

    @FXML
    private void initialize() {
        taskListView.setItems(taskList);
        finishedTaskListView.setItems(finishedTaskList);

        // Custom cell factory for the task list
        taskListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> param) {
                return new ListCell<Task>() {
                    private CheckBox checkBox = new CheckBox();
                    private ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/delete.png")));

                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            checkBox.setText((getIndex() + 1) + ". " + item.getTitle());
                            checkBox.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
                            checkBox.setSelected(item.isCompleted());
                            checkBox.setOnAction(event -> handleTaskCompletion(item));
                            deleteImageView.setFitHeight(16);
                            deleteImageView.setFitWidth(16);
                            deleteImageView.setOnMouseClicked(event -> handleTaskDeletion(item));
                            HBox hBox = new HBox(checkBox);
                            HBox.setHgrow(hBox, Priority.ALWAYS);
                            hBox.setSpacing(10);
                            HBox container = new HBox(hBox, deleteImageView);
                            container.setSpacing(10);
                            setGraphic(container);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Custom cell factory for the finished task list
        finishedTaskListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> param) {
                return new ListCell<Task>() {
                    private CheckBox checkBox = new CheckBox();
                    private ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/delete.png")));

                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            checkBox.setText((getIndex() + 1) + ". " + item.getTitle());
                            checkBox.setStyle("-fx-text-fill: grey; -fx-font-size: 14px;");
                            checkBox.setSelected(item.isCompleted());
                            checkBox.setOnAction(event -> handleTaskUncompletion(item));
                            deleteImageView.setFitHeight(16);
                            deleteImageView.setFitWidth(16);
                            deleteImageView.setOnMouseClicked(event -> handleTaskDeletion(item));
                            HBox hBox = new HBox(checkBox);
                            HBox.setHgrow(hBox, Priority.ALWAYS);
                            hBox.setSpacing(10);
                            HBox container = new HBox(hBox, deleteImageView);
                            container.setSpacing(10);
                            setGraphic(container);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Event handlers for task selection
        taskListView.setOnMouseClicked(this::handleTaskSelection);
        finishedTaskListView.setOnMouseClicked(this::handleFinishedTaskSelection);

        // Event handler for user image click
        userImageView.setOnMouseClicked(this::handleUserImageClick);
    }

    // Method to set the username dynamically after login or registration
    public void setUsername(String username) {
        usernameLabel.setText(username); // Set username text in label
    }

    @FXML
    private void handleAddTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String dueDate = dueDateField.getText();
        String priority = priorityField.getText();
        String status = statusField.getText();

        if (!title.isEmpty()) {
            Task newTask = new Task(title, description, dueDate, priority, status, false);
            taskList.add(newTask);
            clearInputFields();
        }
    }

    private void clearInputFields() {
        titleField.clear();
        descriptionField.clear();
        dueDateField.clear();
        priorityField.clear();
        statusField.clear();
    }

    @FXML
    private void handleTaskSelection(MouseEvent event) {
        selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            displayTaskDetails(selectedTask);
        }
    }

    @FXML
    private void handleFinishedTaskSelection(MouseEvent event) {
        selectedTask = finishedTaskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            displayTaskDetails(selectedTask);
        }
    }

    private void displayTaskDetails(Task task) {
        taskTitleLabel.setText(task.getTitle());
        taskDescriptionLabel.setText(task.getDescription());
        taskDueDateField.setText(task.getDueDate());
        taskPriorityField.setText(task.getPriority());
        taskStatusField.setText(task.getStatus());
    }

    @FXML
    private void handleSaveChanges() {
        if (selectedTask != null) {
            selectedTask.setDueDate(taskDueDateField.getText());
            selectedTask.setPriority(taskPriorityField.getText());
            selectedTask.setStatus(taskStatusField.getText());

            // Refresh the ListViews to reflect changes
            taskListView.refresh();
            finishedTaskListView.refresh();

            clearTaskDetails();
        }
    }

    private void clearTaskDetails() {
        taskTitleLabel.setText("");
        taskDescriptionLabel.setText("");
        taskDueDateField.clear();
        taskPriorityField.clear();
        taskStatusField.clear();
    }

    private void handleTaskCompletion(Task task) {
        if (taskList.contains(task)) { // Check if the task exists in the list
            task.setCompleted(true);
            taskList.remove(task); // Remove the task from the active task list
            finishedTaskList.add(task); // Add the task to the finished task list

            // Refresh the ListViews to reflect changes
            taskListView.refresh();
            finishedTaskListView.refresh();

            clearTaskDetails(); // Clear the task details pane
        }
    }

    private void handleTaskUncompletion(Task task) {
        if (finishedTaskList.contains(task)) { // Check if the task exists in the list
            task.setCompleted(false);
            finishedTaskList.remove(task); // Remove the task from the finished task list
            taskList.add(task); // Add the task back to the active task list

            // Refresh the ListViews to reflect changes
            taskListView.refresh();
            finishedTaskListView.refresh();

            clearTaskDetails(); // Clear the task details pane
        }
    }

    private void handleTaskDeletion(Task task) {
        if (taskList.contains(task)) { // Check if the task exists in the active task list
            taskList.remove(task);
        } else if (finishedTaskList.contains(task)) { // Check if the task exists in the finished task list
            finishedTaskList.remove(task);
        }

        // Refresh the ListViews to reflect changes
        taskListView.refresh();
        finishedTaskListView.refresh();

        clearTaskDetails(); // Clear the task details pane
    }

    @FXML
    private void filterTasksByPriority() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter by Priority");
        dialog.setHeaderText("Enter Priority (1-5):");
        dialog.setContentText("Priority:");

        dialog.showAndWait().ifPresent(priority -> {
            ObservableList<Task> filteredTasks = taskList.filtered(task -> task.getPriority().equals(priority));
            taskListView.setItems(filteredTasks);
        });
    }

    @FXML
    private void filterTasksByDate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter by Date");
        dialog.setHeaderText("Enter Date (YYYY-MM-DD):");
        dialog.setContentText("Date:");

        dialog.showAndWait().ifPresent(date -> {
            ObservableList<Task> filteredTasks = taskList.filtered(task -> task.getDueDate().equals(date));
            taskListView.setItems(filteredTasks);
        });
    }

    @FXML
    private void searchTasksByName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Name");
        dialog.setHeaderText("Enter Task Name:");
        dialog.setContentText("Name:");

        dialog.showAndWait().ifPresent(name -> {
            ObservableList<Task> filteredTasks = taskList.filtered(task -> task.getTitle().toLowerCase().contains(name.toLowerCase()));
            taskListView.setItems(filteredTasks);
        });
    }

    // Event handler for user image click
    private void handleUserImageClick(MouseEvent event) {
        // Display user email and app version in a dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Information");
        alert.setHeaderText("User Email: user@example.com");
        alert.setContentText("Application Version: 9.50.00");
        alert.showAndWait();
    }

    // Inner class representing a Task
    public static class Task {
        private String title;
        private String description;
        private String dueDate;
        private String priority;
        private String status;
        private boolean completed;

        public Task(String title, String description, String dueDate, String priority, String status, boolean completed) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.status = status;
            this.completed = completed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}