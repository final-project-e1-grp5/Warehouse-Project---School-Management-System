package school.teacher.model.mapper;

import org.mapstruct.Mapper;
import school.teacher.model.dto.TeacherDto;
import school.teacher.model.dto.UpdateTeacherDto;
import school.teacher.model.entity.Teacher;
import school.teacher.model.entity.TeacherClass;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    default TeacherDto entityToTeacherDto(Teacher teacher){
        TeacherDto teacherDto = new TeacherDto();
       teacherDto.setAddress(teacher.getAddress());
       teacherDto.setSubject(teacher.getSubject());
       teacherDto.setUserId(teacher.getUserId());
       teacherDto.setFirstName(teacher.getFirstName());
       teacherDto.setLastName(teacher.getLastName());
       teacherDto.setPhoneNumber(teacher.getPhoneNumber());
       teacherDto.setDateOfBirth(teacher.getDateOfBirth());
       teacherDto.setClassIds(teacher.getTeacherClasses().stream().map(TeacherClass::getClassId).collect(Collectors.toList()));

        return teacherDto;
    }


    default void updateTeacherFromDto(UpdateTeacherDto updateTeacherDto, Teacher existingTeacher){

        if (updateTeacherDto == null) {
            return;
        }
        existingTeacher.setFirstName(updateTeacherDto.getFirstName());
        existingTeacher.setLastName(updateTeacherDto.getLastName());
        existingTeacher.setDateOfBirth(updateTeacherDto.getDateOfBirth());
        existingTeacher.setSubject(updateTeacherDto.getSubject());
        existingTeacher.setPhoneNumber(updateTeacherDto.getPhoneNumber());
        existingTeacher.setAddress(updateTeacherDto.getAddress());
    }


    default List<Long> mapTeacherClassesToClassIds(List<TeacherClass> teacherClasses) {
        if (teacherClasses == null) {
            return null;
        }
        return teacherClasses.stream()
                .map(TeacherClass::getClassId)
                .collect(Collectors.toList());
    }

    default List<TeacherClass> mapClassIdsToTeacherClasses(List<Long> classIds, Teacher teacher) {
        if (classIds == null) {
            return null;
        }
        return classIds.stream()
                .map(classId -> {
                    TeacherClass teacherClass = new TeacherClass();
                    teacherClass.setClassId(classId);
                    teacherClass.setTeacher(teacher);
                    return teacherClass;
                })
                .collect(Collectors.toList());
    }

    Teacher TeacherDtoToTeacher(TeacherDto addTeacherDto);
}
