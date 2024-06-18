package school.admin.service.grade;

import school.admin.model.dto.gradeDto.GradeDto;

import java.util.List;

public interface AdminGradeService {
    List<GradeDto> getGradeByClassIdAndAssessment(Long classId, String assessment);

    GradeDto updateGrade(Long id, GradeDto gradeDto);

    void deleteGrade(Long id);

    List<GradeDto> getGradeByStudentId(String studentId);

    GradeDto createGrade(GradeDto gradeDto);
}
