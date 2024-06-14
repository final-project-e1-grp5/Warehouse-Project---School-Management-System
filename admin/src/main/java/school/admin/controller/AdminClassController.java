package school.admin.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.classes.AddClassDto;
import school.admin.model.dto.classes.ClassDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.model.dto.teacherDto.TeacherUserDto;
import school.admin.service.classes.AdminClassService;

import java.util.List;

@RestController
@RequestMapping("/class")
public class AdminClassController {

    @Autowired
    private AdminClassService classService ;

    @PostMapping("/add")
    public ResponseEntity<ClassDto> addClass(@Valid @RequestBody AddClassDto addClassDto) {
        ClassDto classDto = classService.addClass(addClassDto);
        return ResponseEntity.ok(classDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> getClass(@PathVariable Long id) {
        ClassDto classDto = classService.getClass(id);
        return ResponseEntity.ok(classDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable Long id,@Valid @RequestBody ClassDto classDto) {
        ClassDto updatedClass = classService.updateClass(id, classDto);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("assignTeacher/{id}")
    public ResponseEntity<Void> assignTeacherToClass(@PathVariable Long id, @RequestBody String teacherId) {
        classService.assignTeacherToClass(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assignStudent/{id}")
    public ResponseEntity<Void> assignStudentToClass(@PathVariable Long id, @RequestBody String studentId) {
        classService.assignStudentToClass(id, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeTeacher/{id}")
    public ResponseEntity<Void> removeTeacherFromClass(@PathVariable Long id, @RequestBody String teacherId) {
        classService.removeTeacherFromClass(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeStudent/{id}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long id, @RequestBody String studentId) {
        classService.removeStudentFromClass(id, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<StudentUserDto>> getAllStudentsByClassId(@PathVariable Long id) {
        List<StudentUserDto> students = classService.getAllStudentsByClassId(id);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<List<TeacherUserDto>> getAllTeachersByClassId(@PathVariable Long id) {
        List<TeacherUserDto> teachers = classService.getAllTeachersByClassId(id);
        return ResponseEntity.ok(teachers);
    }
}
