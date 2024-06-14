package school.attendance.controller;

import school.attendance.exceptions.ResourceNotFoundException;
import school.attendance.model.dto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.attendance.model.dto.teacherAttendanceDtos.TeacherAttendanceDto;
import school.attendance.model.entity.TeacherAttendance;
import school.attendance.service.TeacherAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attendance/teacher")
public class TeacherAttendanceController {

    @Autowired
    private TeacherAttendanceService teacherAttendanceService;

    @GetMapping("/")
    public List<TeacherAttendanceDto> getAllAttendances() {
        return teacherAttendanceService.getAllAttendances();
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<TeacherAttendanceDto> getAttendanceById(@PathVariable Long attendanceId) {
        TeacherAttendanceDto attendance = teacherAttendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok().body(attendance);
    }

    @PostMapping
    public TeacherAttendanceDto createAttendance(@Valid @RequestBody AddTeacherAttendanceDto attendance) {
        return teacherAttendanceService.createAttendance(attendance);
    }

    @PutMapping("/{attendanceId}")
    public ResponseEntity<TeacherAttendanceDto> updateAttendance(@PathVariable Long attendanceId,
                                                              @Valid @RequestBody TeacherAttendanceDto teacherAttendanceDto) {
        TeacherAttendanceDto updatedAttendance = teacherAttendanceService.updateAttendance(attendanceId, teacherAttendanceDto);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        teacherAttendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}
