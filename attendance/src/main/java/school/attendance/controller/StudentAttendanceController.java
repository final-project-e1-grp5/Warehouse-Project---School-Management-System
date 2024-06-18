package school.attendance.controller;

import school.attendance.exceptions.ResourceNotFoundException;
import school.attendance.model.dto.studentAttendanceDtos.AddStudentAttendanceDto;
import school.attendance.model.dto.studentAttendanceDtos.StudentAttendanceDto;
import school.attendance.model.entity.StudentAttendance;
import school.attendance.service.StudentAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attendance/student")
public class StudentAttendanceController {

    @Autowired
    private StudentAttendanceService studentAttendanceService;


    @GetMapping("/")
    public List<StudentAttendanceDto> getAllAttendances() {
        return studentAttendanceService.getAllAttendances();
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<StudentAttendanceDto> getAttendanceById(@PathVariable Long attendanceId) {
        StudentAttendanceDto attendance = studentAttendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok().body(attendance);
    }

    @PostMapping
    public StudentAttendanceDto createAttendance(@Valid @RequestBody AddStudentAttendanceDto attendance) {
        return studentAttendanceService.createAttendance(attendance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentAttendanceDto> updateAttendance(@PathVariable(value = "id") Long attendanceId,
                                                              @Valid @RequestBody StudentAttendanceDto studentAttendanceDto) {
        StudentAttendanceDto updatedAttendance = studentAttendanceService.updateAttendance(attendanceId, studentAttendanceDto);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable(value = "id") Long attendanceId) {
        studentAttendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}
