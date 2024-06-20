package school.teacher.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.teacher.model.entity.Teacher;
import school.teacher.model.entity.TeacherClass;

public interface TeacherClassRepo extends JpaRepository<TeacherClass, Long> {

    @EntityGraph(attributePaths = "teacherClasses")
    @Query("SELECT t FROM Teacher t WHERE t.userId = :userId")
    Teacher findByIdWithClasses(@Param("userId") String userId);

    void deleteByTeacher(Teacher existingTeacher);
}
