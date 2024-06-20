package school.grade.service;

import school.grade.model.dto.GradeDto;

import java.util.List;

public interface GradeService {
    List<GradeDto> getGradeByClassIdAndAssessment(Long classId, String assessment);

    GradeDto updateGrade(Long id, GradeDto gradeDto);

    void deleteGrade(Long id);

    List<GradeDto> getGradeByStudentId(String studentId);

    GradeDto createGrade(GradeDto gradeDto);
}
