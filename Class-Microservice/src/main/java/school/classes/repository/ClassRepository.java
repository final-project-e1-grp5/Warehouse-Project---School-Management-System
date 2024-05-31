package school.classes.repository;

import school.classes.model.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findByClassName(String className);
    List<ClassEntity> findByStudentId(String studentId);
    List<ClassEntity> findByTeacherId(String teacherId);
    Optional<ClassEntity> findByTeacherIdAndClassName(String teacherId, String className);
    Optional<ClassEntity> findByStudentIdAndClassName(String studentId, String className);

}

