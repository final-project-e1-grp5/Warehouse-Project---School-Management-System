package school.admin.proxy;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.attendanceDto.studentAttendanceDtos.StudentAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.TeacherAttendanceDto;

import java.util.List;

@FeignClient(name = "ATTENDANCE-SERVICE")
public interface AttendanceProxy {

    @GetMapping("/attendance/student")
    List<StudentAttendanceDto> getStudentAttendance(@RequestParam("studentId") String studentId);

    @GetMapping("/attendance/teacher")
    List<TeacherAttendanceDto> getTeacherAttendance(@RequestParam("teacherId") String teacherId);

    @PostMapping("/attendance/teacher")
    TeacherAttendanceDto takeTeacherAttendance(@RequestBody AddTeacherAttendanceDto attendanceDto);

    @PutMapping("/attendance/teacher/{attendanceId}")
    TeacherAttendanceDto updateAttendance(@PathVariable Long attendanceId, @Valid @RequestBody TeacherAttendanceDto teacherAttendanceDto);
}
