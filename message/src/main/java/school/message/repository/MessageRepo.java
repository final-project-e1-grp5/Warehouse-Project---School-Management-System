package school.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.message.model.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Integer> {
}
