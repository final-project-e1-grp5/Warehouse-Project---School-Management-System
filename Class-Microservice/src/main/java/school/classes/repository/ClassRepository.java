package school.classes.repository;

import school.classes.model.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    Optional<ClassEntity> findByStudentId(String studentId);
    Optional<ClassEntity> findByTeacherIdAndClassName(String teacherId, String className);
    Optional<ClassEntity> findByStudentIdAndClassName(String studentId, String className);
    List<ClassEntity> findByTeacherId(String teacherId);
    List<ClassEntity> findByClassName(String className);


}

