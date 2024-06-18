package school.classes.repository;

import school.classes.model.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<ClassEntity, Long> {
}
