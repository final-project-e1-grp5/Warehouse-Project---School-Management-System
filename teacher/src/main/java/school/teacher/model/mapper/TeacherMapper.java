package school.teacher.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.teacher.model.dto.TeacherDto;
import school.teacher.model.dto.UpdateTeacherDto;
import school.teacher.model.entity.Teacher;


@Mapper(componentModel = "spring")
public interface TeacherMapper {


    TeacherDto entityToTeacherDto(Teacher teacher);

    void updateTeacherFromDto(UpdateTeacherDto addTeacherDto, @MappingTarget Teacher existingTeacher);

    Teacher TeacherDtoToTeacher(TeacherDto addTeacherDto);
}
