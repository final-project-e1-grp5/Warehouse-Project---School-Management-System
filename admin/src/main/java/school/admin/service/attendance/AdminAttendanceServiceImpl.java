package school.admin.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.model.dto.attendanceDto.studentAttendanceDtos.StudentAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.TeacherAttendanceDto;
import school.admin.proxy.AttendanceProxy;

import java.util.List;

@Service
public class AdminAttendanceServiceImpl implements AdminAttendanceService {


    @Autowired
    private AttendanceProxy attendanceProxy;

    @Override
    public List<StudentAttendanceDto> getStudentAttendance(String studentId) {
        return attendanceProxy.getStudentAttendance(studentId);
    }

    @Override
    public List<TeacherAttendanceDto> getTeacherAttendance(String teacherId) {
        return attendanceProxy.getTeacherAttendance(teacherId);
    }

    @Override
    public TeacherAttendanceDto takeTeacherAttendance(AddTeacherAttendanceDto attendanceDto) {
       return attendanceProxy.takeTeacherAttendance(attendanceDto);
    }

    @Override
    public TeacherAttendanceDto updateTeacherAttendance(Long attendanceId,TeacherAttendanceDto attendanceDto) {
        return attendanceProxy.updateAttendance(attendanceId,attendanceDto);
    }


}
