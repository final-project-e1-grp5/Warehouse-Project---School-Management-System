package school.admin.service.grade;


import org.springframework.stereotype.Service;
import school.admin.model.dto.gradeDto.GradeDto;
import school.admin.proxy.GradeProxy;

import java.util.List;

@Service
public class AdminGradeServiceImpl  implements AdminGradeService {

    private final GradeProxy gradeProxy;

    public AdminGradeServiceImpl(GradeProxy gradeProxy) {
        this.gradeProxy = gradeProxy;
    }

    @Override
    public List<GradeDto> getGradeByClassIdAndAssessment(Long classId, String assessment) {
        return gradeProxy.findByClassIdAndAssessment(classId, assessment);
    }

    @Override
    public GradeDto updateGrade(Long id, GradeDto gradeDto) {
        return gradeProxy.updateGrade(id,gradeDto);
    }

    @Override
    public void deleteGrade(Long id) {
        gradeProxy.deleteGrade(id);

    }

    @Override
    public List<GradeDto> getGradeByStudentId(String studentId) {

        return gradeProxy.findByStudentId(studentId);
    }

    @Override
    public GradeDto createGrade(GradeDto gradeDto) {
        return gradeProxy.addGrade(gradeDto);
    }
}
