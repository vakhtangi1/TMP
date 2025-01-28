package team4.tmp.service;

import team4.tmp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    // List to store tasks in memory
    private List<Task> tasks = new ArrayList<>();

    // Create Task
    public Task createTask(Task task) {
        tasks.add(task); // Add task to the list
        return task;
    }

    // Get All Tasks
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Delete Task by ID
    public boolean deleteTask(Long taskId) {
        return tasks.removeIf(task -> task.getId().equals(taskId)); // Remove task by ID
    }

    // Update Task
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);

        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            existingTask.setStatus(updatedTask.getStatus());
            return existingTask;
        }
        return null;
    }
}
