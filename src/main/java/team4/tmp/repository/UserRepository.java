package team4.tmp.repository;

import team4.tmp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // This returns a User object (not Optional)
}