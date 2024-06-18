package school.admin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.studentDto.AddStudentDto;
import school.admin.model.dto.studentDto.StudentDto;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE", path = "/student")

public interface StudentProxy {
    @PostMapping("student/add")
    StudentDto addStudent(@RequestBody AddStudentDto student);

    @GetMapping("/student/{id}")
    StudentDto getStudentById(@PathVariable String id);

    @PutMapping("/student/{id}")
    StudentDto updateStudent(@PathVariable String id, @RequestBody StudentDto studentDto);

    @DeleteMapping("/student/{id}")
    void deleteStudent(@PathVariable String id);

    @GetMapping("student/all")
    List<StudentDto> getAllStudents();
}