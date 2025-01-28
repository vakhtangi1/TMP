package team4.tmp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;

    @ManyToOne
    private User user;

    // Enum for Task Priority
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // Enum for Task Status
    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }

    // Default constructor
    public Task() {}

    // Constructor with parameters
    public Task(String title, String description, String dueDate, String priority, String status) {
        this.title = title;
        this.description = description;
        this.dueDate = LocalDate.parse(dueDate);  // Convert string to LocalDate
        this.priority = Priority.valueOf(priority.toUpperCase());  // Convert string to Priority enum
        this.status = Status.valueOf(status.toUpperCase());  // Convert string to Status enum
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
