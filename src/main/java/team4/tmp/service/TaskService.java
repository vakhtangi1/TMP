package team4.tmp.service;

import team4.tmp.repository.TaskRepository;
import team4.tmp.model.Task;
import team4.tmp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getTasksByStatus(User user, Task.Status status) {
        return taskRepository.findByUserAndStatus(user, status);
    }

    public List<Task> getTasksByPriority(User user, Task.Priority priority) {
        return taskRepository.findByUserAndPriority(user, priority);
    }

    public List<Task> getTasksByDueDate(User user, LocalDate dueDate) {
        return taskRepository.findByUserAndDueDate(user, dueDate);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task editTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUser(task.getUser());
        return taskRepository.save(existingTask);
    }

    public Task markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(Task.Status.COMPLETED);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}