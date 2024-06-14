package school.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.teacher.model.entity.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, String> {
}
