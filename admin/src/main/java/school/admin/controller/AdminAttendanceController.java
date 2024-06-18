package school.admin.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.attendanceDto.studentAttendanceDtos.StudentAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.admin.model.dto.attendanceDto.teacherAttendanceDtos.TeacherAttendanceDto;
import school.admin.service.attendance.AdminAttendanceService;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AdminAttendanceController {

    @Autowired
    private AdminAttendanceService attendanceService;

    @GetMapping("/student")
    public ResponseEntity<List<StudentAttendanceDto>> getStudentAttendance(@RequestParam("studentId") String studentId) {
        List<StudentAttendanceDto> attendance = attendanceService.getStudentAttendance(studentId);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherAttendanceDto>> getTeacherAttendance(@RequestParam("teacherId") String teacherId) {
        List<TeacherAttendanceDto> attendance = attendanceService.getTeacherAttendance(teacherId);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/teacher")
    public ResponseEntity<TeacherAttendanceDto> takeTeacherAttendance(@Valid @RequestBody AddTeacherAttendanceDto attendanceDto) {
        TeacherAttendanceDto teacherAttendanceDto = attendanceService.takeTeacherAttendance(attendanceDto);
        return ResponseEntity.ok(teacherAttendanceDto);
    }
    @PutMapping("/teacher/{attendanceId}")
    public ResponseEntity<TeacherAttendanceDto> takeTeacherAttendance(@PathVariable Long attendanceId,@Valid @RequestBody TeacherAttendanceDto attendanceDto) {
        TeacherAttendanceDto updatedAttendanceDto = attendanceService.updateTeacherAttendance(attendanceId,attendanceDto);
        return ResponseEntity.ok(updatedAttendanceDto);
    }
}