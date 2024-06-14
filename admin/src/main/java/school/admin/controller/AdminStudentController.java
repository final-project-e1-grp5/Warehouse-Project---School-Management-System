package school.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.studentDto.AddStudentUserDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.service.student.AdminStudentService;

import java.util.List;


@RestController
@RequestMapping("/student")
public class AdminStudentController {
    @Autowired
    private AdminStudentService adminStudentService;


    @PostMapping("/add")
    public ResponseEntity<StudentUserDto> addStudent(@RequestBody AddStudentUserDto addStudentUserDto) {
       StudentUserDto createdStudentUserDto= adminStudentService.addStudent(addStudentUserDto);
        return ResponseEntity.ok(createdStudentUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentUserDto> getStudent(@PathVariable String id) {
        StudentUserDto studentUserDto = adminStudentService.getStudent(id);
        return ResponseEntity.ok(studentUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentUserDto> updateStudent(@PathVariable String id,@RequestBody StudentUserDto studentUserDto) {
        StudentUserDto updatedStudent = adminStudentService.updateStudent( id,studentUserDto);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        adminStudentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<StudentUserDto>> getAllStudent() {
        List<StudentUserDto> studentUserDtos = adminStudentService.getAll();
        return ResponseEntity.ok(studentUserDtos);
    }
}