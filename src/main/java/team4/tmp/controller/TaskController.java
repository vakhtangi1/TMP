package team4.tmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team4.tmp.model.Task;
import team4.tmp.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
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
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Update a task
    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        return taskService.updateTask(taskId, updatedTask);
    }

    // Delete a task
    @DeleteMapping("/{taskId}")
    public boolean deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }

    // Update task status
    @PatchMapping("/{taskId}/status/{status}")
    public boolean updateTaskStatus(@PathVariable Long taskId, @PathVariable String status) {
        return taskService.updateTaskStatus(taskId, status);
    }
}