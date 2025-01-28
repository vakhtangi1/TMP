package team4.tmp.repository;

import team4.tmp.model.Task;
import team4.tmp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, Task.Status status);

    List<Task> findByUserAndPriority(User user, Task.Priority priority);

    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);
}
