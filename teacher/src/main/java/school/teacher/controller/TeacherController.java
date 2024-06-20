package school.teacher.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.teacher.model.dto.TeacherDto;
import school.teacher.model.dto.UpdateTeacherDto;
import school.teacher.service.TeacherService;

import java.util.List;


@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    @PostMapping("/add")
    public ResponseEntity<TeacherDto> addTeacher(@Valid @RequestBody TeacherDto addTeacherDto) {
        TeacherDto addedTeacherDto = teacherService.addTeacher(addTeacherDto);
        return ResponseEntity.ok(addedTeacherDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable String id) {
        TeacherDto teacherDto = teacherService.getTeacher(id);
        return ResponseEntity.ok(teacherDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable String id,@Valid @RequestBody UpdateTeacherDto updatedTeacherDto) {
        TeacherDto updatedDto = teacherService.updateTeacher(id, updatedTeacherDto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}