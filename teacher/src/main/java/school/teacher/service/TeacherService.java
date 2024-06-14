package school.teacher.service;
import org.springframework.transaction.annotation.Transactional;
import school.teacher.model.dto.TeacherDto;
import school.teacher.model.dto.UpdateTeacherDto;

import java.util.List;

public interface TeacherService  {

    TeacherDto addTeacher(TeacherDto addTeacherDto);
    TeacherDto getTeacher(String teacherId);
    List<TeacherDto> getAllTeachers();
    TeacherDto updateTeacher(String teacherId, UpdateTeacherDto addTeacherDto);
    void deleteTeacher(String userId);


}
