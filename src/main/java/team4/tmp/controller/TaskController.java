package team4.tmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team4.tmp.model.Task;
import team4.tmp.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get tasks by status
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskService.findTasksByStatus(status);
    }

    // Create a task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Update a task
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(taskId, updatedTask);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        if (isDeleted) {
            return ResponseEntity.ok("Task deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
    }

    // Update task status
    @PatchMapping("/{taskId}/status/{status}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long taskId, @PathVariable String status) {
        boolean isUpdated = taskService.updateTaskStatus(taskId, status);
        if (isUpdated) {
            return ResponseEntity.ok("Task status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
    }

    // Get tasks by priority
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        return taskService.findTasksByPriority(priority);
    }

    // Get tasks by due date
    @GetMapping("/dueDate/{dueDate}")
    public List<Task> getTasksByDueDate(@PathVariable String dueDate) {
        LocalDate localDate = LocalDate.parse(dueDate);
        return taskService.findTasksByDueDate(localDate);
    }

    // Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<String> markTaskAsCompleted(@PathVariable Long taskId) {
        boolean isUpdated = taskService.updateTaskStatus(taskId, "Completed");
        if (isUpdated) {
            return ResponseEntity.ok("Task marked as completed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
    }
}