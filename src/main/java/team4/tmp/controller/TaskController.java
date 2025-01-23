package team4.tmp.controller;

import team4.tmp.service.TaskService;
import team4.tmp.service.UserService;
import team4.tmp.model.Task;
import team4.tmp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(taskService.getTasksByUser(user.get()));
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(
            @PathVariable Long userId, @PathVariable Task.Status status) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(taskService.getTasksByStatus(user.get(), status));
    }

    @GetMapping("/user/{userId}/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(
            @PathVariable Long userId, @PathVariable Task.Priority priority) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(taskService.getTasksByPriority(user.get(), priority));
    }

    @GetMapping("/user/{userId}/due-date/{dueDate}")
    public ResponseEntity<List<Task>> getTasksByDueDate(
            @PathVariable Long userId, @PathVariable String dueDate) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(taskService.getTasksByDueDate(user.get(), LocalDate.parse(dueDate)));
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}