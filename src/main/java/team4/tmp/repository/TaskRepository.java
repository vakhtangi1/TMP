package team4.tmp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team4.tmp.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by status
    List<Task> findByStatus(String status);

    // Find tasks by priority
    @Query("SELECT t FROM Task t WHERE t.priority = ?1")
    List<Task> findTasksByPriority(String priority);

    // Find tasks by due date
    @Query("SELECT t FROM Task t WHERE t.dueDate = ?1")
    List<Task> findTasksByDueDate(LocalDate dueDate);

    // Custom query to update a task's status by taskId
    @Modifying
    @Query("UPDATE Task t SET t.status = ?1 WHERE t.id = ?2")
    int updateTaskStatus(String status, Long taskId);
}