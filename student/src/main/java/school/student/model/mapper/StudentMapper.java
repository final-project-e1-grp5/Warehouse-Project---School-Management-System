package school.student.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.student.model.dto.AddStudentDto;
import school.student.model.dto.StudentDto;
import school.student.model.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentDto studentDto);
    Student addStudentDtotoEntity(AddStudentDto addStudentdto);
    StudentDto toStudentDto(Student student);

    StudentDto entityToStudentDto(Student student);

    void updateEntityFromDto(StudentDto studentDto,@MappingTarget Student studentEntity);
}
