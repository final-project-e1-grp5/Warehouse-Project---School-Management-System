package school.admin.service.attendance;

import school.admin.model.dto.attendanceDto.studentAttendanceDtos.StudentAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.TeacherAttendanceDto;

import java.util.List;

public interface AdminAttendanceService {


    List<StudentAttendanceDto> getStudentAttendance(String studentId);
    List<TeacherAttendanceDto> getTeacherAttendance(String teacherId);
    TeacherAttendanceDto takeTeacherAttendance(AddTeacherAttendanceDto attendanceDto);
    TeacherAttendanceDto updateTeacherAttendance(Long attendanceId,TeacherAttendanceDto attendanceDto);



}
