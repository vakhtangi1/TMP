package team4.tmp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team4.tmp.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by status
    List<Task> findByStatus(String status);

    // Custom query to find tasks by priority and due date
    @Query("SELECT t FROM Task t WHERE t.priority = ?1 AND t.dueDate < ?2")
    List<Task> findTasksByPriorityAndDueDateBefore(String priority, String dueDate);

    // Custom query to update a task's status by taskId
    @Query("UPDATE Task t SET t.status = ?1 WHERE t.id = ?2")
    int updateTaskStatus(String status, Long taskId);
}
