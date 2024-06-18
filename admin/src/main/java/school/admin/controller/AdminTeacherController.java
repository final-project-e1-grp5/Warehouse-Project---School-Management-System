package school.admin.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.teacherDto.AddTeacherUserDto;
import school.admin.model.dto.teacherDto.TeacherUserDto;
import school.admin.model.dto.teacherDto.UpdateTeacherUserDto;
import school.admin.service.teacher.AdminTeacherService;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class AdminTeacherController {

    @Autowired
    private AdminTeacherService adminTeacherService;

    @PostMapping("/add")
    public ResponseEntity<TeacherUserDto> addTeacher( @Valid @RequestBody AddTeacherUserDto teacherDto) {
        TeacherUserDto createdTeacher = adminTeacherService.addTeacher(teacherDto);
        return ResponseEntity.ok(createdTeacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherUserDto> getTeacher(@PathVariable String id) {
        TeacherUserDto teacher = adminTeacherService.getTeacher(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherUserDto> updateTeacher( @PathVariable String id, @RequestBody UpdateTeacherUserDto updateTeacherUserDto) {
            TeacherUserDto updatedTeacher = adminTeacherService.updateTeacher(id,updateTeacherUserDto);
            return ResponseEntity.ok(updatedTeacher);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        adminTeacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherUserDto>> getAllTeachers() {
        List<TeacherUserDto> teachers = adminTeacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }
}
