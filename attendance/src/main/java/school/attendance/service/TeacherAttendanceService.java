package school.attendance.service;

import school.attendance.model.dto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.attendance.model.dto.teacherAttendanceDtos.TeacherAttendanceDto;

import java.util.List;

public interface TeacherAttendanceService {
    List<TeacherAttendanceDto> getAllAttendances();
    TeacherAttendanceDto getAttendanceById(Long id);
    TeacherAttendanceDto createAttendance(AddTeacherAttendanceDto attendanceDto);
    TeacherAttendanceDto updateAttendance(Long id, TeacherAttendanceDto attendanceDto);
    void deleteAttendance(Long id);
}