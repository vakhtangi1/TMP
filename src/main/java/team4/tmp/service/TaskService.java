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

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}