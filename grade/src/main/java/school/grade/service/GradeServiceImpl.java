package school.grade.service;

import org.springframework.stereotype.Service;
import school.grade.exception.GradeNotFoundException;
import school.grade.model.dto.AddGradeDto;
import school.grade.model.dto.GradeDto;
import school.grade.model.entity.Grade;
import school.grade.model.mapper.GradeMapper;
import school.grade.repository.GradeRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepo repo;
    private final GradeMapper mapper;

    public GradeServiceImpl(GradeRepo repo, GradeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public GradeDto createGrade(AddGradeDto addGradeDto) {

        Grade grade = mapper.addDtoToEntity(addGradeDto);

        Grade savedGrade = repo.save(grade);

        return mapper.entityToDto(savedGrade);
    }

    @Override
    public GradeDto updateGrade(int id, AddGradeDto addGradeDto) {
        Grade grade = repo.findById(id).orElse(null);

        if (grade == null) {
            throw new GradeNotFoundException("Grade with id: " + id + " was not found!!!");
        } else {
            grade.setGrade(addGradeDto.getGrade());
            grade.setStudentId(addGradeDto.getStudentId());
            grade.setClassId(addGradeDto.getClassId());

            Grade savedGrade = repo.save(grade);

            return mapper.entityToDto(savedGrade);
        }
    }

    @Override
    public GradeDto readGrade(int id) {

        Grade grade = repo.findById(id).orElse(null);

        if(grade != null) {
            return mapper.entityToDto(grade);
        } else {
            throw new GradeNotFoundException("Grade with id: " + id + " was not found!!!");
        }
    }

    @Override
    public List<GradeDto> readAllGrades() {

        List<Grade> grades = repo.findAll();
        List<GradeDto> gradeDtos = new ArrayList<>();

        grades.forEach(g -> gradeDtos.add(mapper.entityToDto(g)));

        return gradeDtos;
    }

    @Override
    public void deleteGrade(int id) {
        Grade grade = repo.findById(id).orElse(null);

        if(grade != null) {
            repo.delete(grade);
        } else {
            throw new GradeNotFoundException("Grade with id: " + id + " was not found!!!");
        }
    }
}
