package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.Region;
import team4.tmp.model.Task;

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
    private ImageView userImageView;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedTaskList = FXCollections.observableArrayList();

    private Task selectedTask;
    private String authenticatedUserId;

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    public void initialize() {
        // Set up the taskListView to display task titles
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            private final CheckBox checkBox = new CheckBox();
            private final Button deleteButton = new Button();
            private final HBox hbox = new HBox(10);
            private final Label taskNumberLabel = new Label();
            private final Label taskTitleLabel = new Label();
            private final Pane spacer = new Pane();

            {
                // Set up the delete button
                Image deleteImage = new Image(getClass().getResourceAsStream("/images/delete.png"));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(10);
                deleteImageView.setFitWidth(10);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setOnAction(event -> {
                    Task task = getItem();
                    if (task != null) {
                        deleteTaskFromDatabase(task);
                        taskList.remove(task);
                    }
                });

                // Set up the checkbox
                checkBox.setOnAction(event -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setCompleted(checkBox.isSelected());
                        if (task.isCompleted()) {
                            taskList.remove(task);
                            finishedTaskList.add(task);
                        } else {
                            finishedTaskList.remove(task);
                            taskList.add(task);
                        }
                    }
                });

                // Set the spacer to grow and push the delete button to the right
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // Add elements to the HBox (delete button on the far right)
                hbox.getChildren().addAll(taskNumberLabel, checkBox, taskTitleLabel, spacer, deleteButton);
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    taskNumberLabel.setText((taskListView.getItems().indexOf(task) + 1) + ". ");
                    taskTitleLabel.setText(task.getTitle());
                    checkBox.setSelected(task.isCompleted());
                    setGraphic(hbox);
                }
            }
        });

        // Set up the finishedTaskListView
        finishedTaskListView.setCellFactory(param -> new ListCell<Task>() {
            private final CheckBox checkBox = new CheckBox();
            private final Button deleteButton = new Button();
            private final HBox hbox = new HBox(10);
            private final Label taskNumberLabel = new Label();
            private final Label taskTitleLabel = new Label();
            private final Pane spacer = new Pane();

            {
                // Set up the delete button
                Image deleteImage = new Image(getClass().getResourceAsStream("/images/delete.png"));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(10);
                deleteImageView.setFitWidth(10);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setOnAction(event -> {
                    Task task = getItem();
                    if (task != null) {
                        deleteTaskFromDatabase(task);
                        finishedTaskList.remove(task);
                    }
                });

                // Set up the checkbox
                checkBox.setOnAction(event -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setCompleted(checkBox.isSelected());
                        if (!task.isCompleted()) {
                            finishedTaskList.remove(task);
                            taskList.add(task);
                        } else {
                            taskList.remove(task);
                            finishedTaskList.add(task);
                        }
                    }
                });

                // Set the spacer to grow and push the delete button to the right
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // Add elements to the HBox (delete button on the far right)
                hbox.getChildren().addAll(taskNumberLabel, checkBox, taskTitleLabel, spacer, deleteButton);
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    taskNumberLabel.setText((finishedTaskListView.getItems().indexOf(task) + 1) + ". ");
                    taskTitleLabel.setText(task.getTitle());
                    checkBox.setSelected(task.isCompleted());
                    setGraphic(hbox);
                }
            }
        });
    }

    public void setAuthenticatedUserId(String userId) {
        this.authenticatedUserId = userId;
        fetchTasks();
    }

    @FXML
    private void handleAddTask() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String dueDate = dueDateField.getText().trim();
        String priority = priorityField.getText().trim();
        String status = statusField.getText().trim();

        if (title.isEmpty()) {
            showError("Title is required.");
            return;
        }

        try {
            String requestBody = String.format(
                    "{\"title\": \"%s\", \"description\": \"%s\", \"dueDate\": \"%s\", \"priority\": \"%s\", \"status\": \"%s\", \"user\": {\"id\": %s}}",
                    title, description, dueDate, priority, status, authenticatedUserId
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/task"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                fetchTasks();
                clearInputFields();
            } else {
                showError("Failed to create task.");
            }
        } catch (Exception e) {
            showError("An error occurred while creating the task.");
            e.printStackTrace();
        }
    }

    private void fetchTasks() {
        try {
            String url = BASE_URL + "/task?userId=" + authenticatedUserId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Task[] tasks = objectMapper.readValue(response.body(), Task[].class);
                taskList.setAll(tasks);
                taskListView.setItems(taskList);
                finishedTaskListView.setItems(finishedTaskList);
            } else {
                showError("Failed to fetch tasks.");
            }
        } catch (Exception e) {
            showError("An error occurred while fetching tasks.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTaskSelection(MouseEvent event) {
        selectedTask = taskListView.getSelectionModel().getSelectedItem();
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

            // Refresh the ListViews
            taskListView.refresh();
            finishedTaskListView.refresh();

            clearTaskDetails();
        }
    }

    @FXML
    private void filterTasksByPriority() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter by Priority");
        dialog.setHeaderText("Enter Priority (e.g., High, Medium, Low):");
        dialog.setContentText("Priority:");

        dialog.showAndWait().ifPresent(priority -> {
            ObservableList<Task> filteredTasks = taskList.filtered(task -> task.getPriority().equalsIgnoreCase(priority));
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
            ObservableList<Task> filteredTasks = taskList.filtered(task -> task.getTitle().toLowerCase() .contains(name.toLowerCase()));
            taskListView.setItems(filteredTasks);
        });
    }

    private void deleteTaskFromDatabase(Task task) {
        try {
            String url = BASE_URL + "/task/" + task.getId();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                showError("Failed to delete task.");
            }
        } catch (Exception e) {
            showError("An error occurred while deleting the task.");
            e.printStackTrace();
        }
    }

    private void clearTaskDetails() {
        taskTitleLabel.setText("");
        taskDescriptionLabel.setText("");
        taskDueDateField.clear();
        taskPriorityField.clear();
        taskStatusField.clear();
    }

    private void clearInputFields() {
        titleField.clear();
        descriptionField.clear();
        dueDateField.clear();
        priorityField.clear();
        statusField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}