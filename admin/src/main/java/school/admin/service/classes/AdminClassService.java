package school.admin.service.classes;

import school.admin.model.dto.classes.AddClassDto;
import school.admin.model.dto.classes.ClassDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.model.dto.teacherDto.TeacherUserDto;

import java.util.List;

public interface AdminClassService {

    ClassDto addClass(AddClassDto addClassDto);
    ClassDto getClass(Long id);
    ClassDto updateClass(Long id, ClassDto classDto);
    void deleteClass(Long id);
    void assignTeacherToClass(Long id, String teacherId);
    void assignStudentToClass(Long id, String studentId);
    void removeTeacherFromClass(Long id, String teacherId);
    void removeStudentFromClass(Long id, String studentId);
    List<StudentUserDto> getAllStudentsByClassId(Long id);
    List<TeacherUserDto> getAllTeachersByClassId(Long id);
}
