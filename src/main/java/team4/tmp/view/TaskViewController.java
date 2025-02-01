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
    private Label taskDueDateLabel;
    @FXML
    private Label taskPriorityLabel;
    @FXML
    private Label taskStatusLabel;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private ObservableList<Task> finishedTaskList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskTitleLabel.setText(selectedTask.getTitle());
            taskDescriptionLabel.setText(selectedTask.getDescription());
            taskDueDateLabel.setText("Due Date: " + selectedTask.getDueDate());
            taskPriorityLabel.setText("Priority: " + selectedTask.getPriority());
            taskStatusLabel.setText("Status: " + selectedTask.getStatus());
        }
    }

    @FXML
    private void handleFinishedTaskSelection(MouseEvent event) {
        Task selectedTask = finishedTaskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskTitleLabel.setText(selectedTask.getTitle());
            taskDescriptionLabel.setText(selectedTask.getDescription());
            taskDueDateLabel.setText("Due Date: " + selectedTask.getDueDate());
            taskPriorityLabel.setText("Priority: " + selectedTask.getPriority());
            taskStatusLabel.setText("Status: " + selectedTask.getStatus());
        }
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

    private void clearTaskDetails() {
        taskTitleLabel.setText("");
        taskDescriptionLabel.setText("");
        taskDueDateLabel.setText("");
        taskPriorityLabel.setText("");
        taskStatusLabel.setText("");
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

        public String getPriority() {
            return priority;
        }

        public String getStatus() {
            return status;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}