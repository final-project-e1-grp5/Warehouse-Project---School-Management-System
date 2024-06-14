package school.grade.service;

import school.grade.model.dto.AddGradeDto;
import school.grade.model.dto.GradeDto;

import java.util.List;

public interface GradeService {

    GradeDto createGrade(AddGradeDto addGradeDto);

    GradeDto updateGrade(int id, AddGradeDto addGradeDto);

    GradeDto readGrade(int id);

    List<GradeDto> readAllGrades();

    void deleteGrade(int id);

}