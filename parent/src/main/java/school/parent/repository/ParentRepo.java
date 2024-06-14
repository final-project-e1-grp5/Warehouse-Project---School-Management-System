package school.parent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.parent.model.entity.Parent;

public interface ParentRepo extends JpaRepository<Parent, Long> {
}
