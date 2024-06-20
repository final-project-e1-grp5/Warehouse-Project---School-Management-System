package school.student.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.student.model.dto.AddStudentDto;
import school.student.model.dto.StudentDto;
import school.student.model.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentDto studentDto);
    StudentDto toStudentDto(Student student);

    StudentDto entityToStudentDto(Student student);

    default void updateEntityFromDto(StudentDto studentDto, Student studentEntity) {
        if (studentDto == null) {
            return;
        }
        if(studentDto.getFirstName() != null){
            studentEntity.setFirstName(studentDto.getFirstName());
        }
        if(studentDto.getLastName() != null){
            studentEntity.setLastName(studentDto.getLastName());
        }
        if(studentDto.getAddress() != null){
            studentEntity.setAddress(studentDto.getAddress());
        }
        if(studentDto.getClassId() != null){
            studentEntity.setClassId(studentDto.getClassId());
        }
        if(studentDto.getDateOfBirth() != null){
            studentEntity.setDateOfBirth(studentDto.getDateOfBirth());
        }
        if(studentDto.getPhoneNumber() != null){
            studentEntity.setPhoneNumber(studentDto.getPhoneNumber());
        }
        if(studentDto.getParentId() != null){
            studentEntity.setParentId(studentDto.getParentId());
        }



    }

    Student addStudentDtoToEntity(AddStudentDto addStudentDto);
}
