package school.admin.service.teacher;

import school.admin.model.dto.teacherDto.AddTeacherUserDto;
import school.admin.model.dto.teacherDto.TeacherDto;
import school.admin.model.dto.teacherDto.TeacherUserDto;
import school.admin.model.dto.teacherDto.UpdateTeacherUserDto;

import java.util.List;

public interface AdminTeacherService {
    TeacherUserDto addTeacher(AddTeacherUserDto addTeacherUserDto);
    TeacherUserDto getTeacher(String id);
    TeacherUserDto updateTeacher(String id, UpdateTeacherUserDto updateTeacherUserDto);

    void deleteTeacher(String id);

    List<TeacherUserDto> getAllTeachers();
}

