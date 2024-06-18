package school.grade.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.grade.exceptions.GradeNotFoundException;
import school.grade.model.dto.GradeDto;
import school.grade.model.entity.Grade;
import school.grade.model.mapper.GradeMapper;
import school.grade.repository.GradeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepo gradeRepo;
    private final GradeMapper gradeMapper;

    public GradeServiceImpl(GradeRepo gradeRepo, GradeMapper gradeMapper) {
        this.gradeRepo = gradeRepo;
        this.gradeMapper = gradeMapper;
    }

    @Override
    public List<GradeDto> getGradeByClassIdAndAssessment(Long classId, String assessment) {
        List<Grade> grades = gradeRepo.findByClassIdAndAssessment(classId, assessment);
        return gradeMapper.entityToDto(grades);
    }

    @Transactional
    @Override
    public GradeDto updateGrade(Long id, GradeDto gradeDto) {
        Optional<Grade> grade = gradeRepo.findById(id);
        if (grade.isPresent()) {
            Grade existingGrade = grade.get();
           gradeMapper.updateEntityFromDto(existingGrade,gradeDto);
            gradeRepo.save(existingGrade);
            return gradeMapper.entityToDto(existingGrade);
        }
        throw new GradeNotFoundException("Grade not found with id: " + id);
    }

    @Transactional
    @Override
    public void deleteGrade(Long id) {
       Optional<Grade> grade = gradeRepo.findById(id);
       if(grade.isPresent()){
           gradeRepo.delete(grade.get());
        } else {
            throw new GradeNotFoundException("Grade not found with id: " + id);
        }
    }

    @Override
    public List<GradeDto> getGradeByStudentId(String studentId) {
        List<Grade> grades = gradeRepo.findByStudentId(studentId);
        return gradeMapper.entityToDto(grades);
    }

    @Transactional
    @Override
    public GradeDto createGrade(GradeDto gradeDto) {
        Grade grade = gradeMapper.dtoToEntity(gradeDto);
        gradeRepo.save(grade);
        return gradeMapper.entityToDto(grade);
    }
}
