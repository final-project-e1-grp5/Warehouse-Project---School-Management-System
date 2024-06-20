package school.attendance.service;
import school.attendance.model.dto.studentAttendanceDtos.AddStudentAttendanceDto;
import school.attendance.model.dto.studentAttendanceDtos.StudentAttendanceDto;

import java.util.List;


public interface StudentAttendanceService {
    List<StudentAttendanceDto> getAllAttendances();
    StudentAttendanceDto getAttendanceById(Long id);
    StudentAttendanceDto createAttendance(AddStudentAttendanceDto attendanceDto);
    StudentAttendanceDto updateAttendance(Long id, StudentAttendanceDto attendanceDto);
    void deleteAttendance(Long id);
}