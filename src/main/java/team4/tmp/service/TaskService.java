package team4.tmp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.tmp.model.Task;
import team4.tmp.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Delete a task by ID
    public boolean deleteTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return true;
        }
        return false;
    }

    // Update an existing task
    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setPriority(updatedTask.getPriority());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        }
        return null;
    }

    // Custom method to find tasks by status
    public List<Task> findTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    // Custom method to find tasks by priority and due date
    public List<Task> findTasksByPriorityAndDueDateBefore(String priority, String dueDate) {
        return taskRepository.findTasksByPriorityAndDueDateBefore(priority, dueDate);
    }

    // Custom method to update task status
    public boolean updateTaskStatus(Long taskId, String status) {
        int updatedRows = taskRepository.updateTaskStatus(status, taskId);
        return updatedRows > 0;
    }
}