package school.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.grade.model.dto.GradeDto;
import school.grade.model.entity.Grade;

import java.util.List;

public interface GradeRepo extends JpaRepository<Grade, Long> {
    List<Grade> findByClassIdAndAssessment(Long classId, String assessment);

    List<Grade> findByStudentId(String studentId);
}
