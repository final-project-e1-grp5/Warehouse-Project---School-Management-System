package school.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.grade.model.entity.Grade;

public interface GradeRepo extends JpaRepository<Grade, Integer> {
}
