package school.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.user.model.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByUserId(String id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findAllByRole(String role);

    Optional<User> findByEmailAndPassword(String email, String password);
}
