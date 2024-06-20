package school.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.student.model.entity.Student;

public interface StudentRepo extends JpaRepository<Student, String> {
}
