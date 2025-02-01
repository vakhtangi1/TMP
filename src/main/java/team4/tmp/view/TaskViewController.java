package team4.tmp.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

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
    private TextField taskDueDateField; // Editable due date field
    @FXML
    private TextField taskPriorityField; // Editable priority field
    @FXML
    private TextField taskStatusField; // Editable status field
    @FXML
    private ImageView logoImageView; // ImageView for logushka.png

    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedTaskList = FXCollections.observableArrayList();

    private Task selectedTask; // Track the currently selected task

    @FXML
    private void initialize() {
        // Load the logushka.png image
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logushka.png"));
        logoImageView.setImage(logoImage);

        taskListView.setItems(taskList);
        finishedTaskListView.setItems(finishedTaskList);

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
                            checkBox.setStyle("-fx-text-fill: green; -fx-font-size: 20px;");
                            checkBox.setSelected(item.isCompleted());
                            checkBox.setOnAction(event -> handleTaskCompletion(item));
                            deleteImageView.setFitHeight(20);
                            deleteImageView.setFitWidth(20);
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
                            checkBox.setStyle("-fx-text-fill: grey; -fx-font-size: 20px;");
                            checkBox.setSelected(item.isCompleted());
                            checkBox.setOnAction(event -> handleTaskUncompletion(item));
                            deleteImageView.setFitHeight(20);
                            deleteImageView.setFitWidth(20);
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

        taskListView.setOnMouseClicked(this::handleTaskSelection);
        finishedTaskListView.setOnMouseClicked(this::handleFinishedTaskSelection);
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
            titleField.clear();
            descriptionField.clear();
            dueDateField.clear();
            priorityField.clear();
            statusField.clear();
        }
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
        taskDueDateField.setText(task.getDueDate()); // Populate editable field
        taskPriorityField.setText(task.getPriority()); // Populate editable field
        taskStatusField.setText(task.getStatus()); // Populate editable field
    }

    @FXML
    private void handleSaveChanges() {
        if (selectedTask != null) {
            // Update the selected task with new values
            selectedTask.setDueDate(taskDueDateField.getText());
            selectedTask.setPriority(taskPriorityField.getText());
            selectedTask.setStatus(taskStatusField.getText());

            // Refresh the ListView to reflect changes
            taskListView.refresh();
            finishedTaskListView.refresh();

            // Clear the editable fields
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
        task.setCompleted(true);
        taskList.remove(task);
        finishedTaskList.add(task);
        clearTaskDetails();
    }

    private void handleTaskUncompletion(Task task) {
        task.setCompleted(false);
        finishedTaskList.remove(task);
        taskList.add(task);
        clearTaskDetails();
    }

    private void handleTaskDeletion(Task task) {
        taskList.remove(task);
        finishedTaskList.remove(task);
        clearTaskDetails();
    }

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

        public String getDescription() {
            return description;
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