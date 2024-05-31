package school.classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import school.classes.model.dto.ResponseDto;
import school.classes.service.ClassService;

import java.util.Map;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PutMapping("/student")
    public ResponseDto updateStudentClass(@RequestBody Map<String, String> requestBody) {
        String studentId = requestBody.get("studentId");
        String className = requestBody.get("className");
        ResponseDto response = classService.updateStudentClass(studentId, className);
        return response;
    }

    @PutMapping("/update-teacher-class")
    public ResponseDto updateTeacherClass(@RequestBody Map<String, String> requestBody) {
        String teacherId = requestBody.get("teacherId");
        String currentClassName = requestBody.get("currentClassName");
        String newClassName = requestBody.get("newClassName");
        ResponseDto response = classService.updateTeacherClass(teacherId, currentClassName, newClassName);
        return response;
    }

    @GetMapping("/get-all-classes")
    public ResponseDto getAllClasses() {
        ResponseDto response = classService.getAllClasses();
        return response;
    }
    @DeleteMapping("/remove-class/{className}")
    public ResponseDto removeClass( @PathVariable String className) {
        ResponseDto response = classService.deleteClass(className);
        return response;
    }

    @PostMapping("/assign-teacher-to-class")
    public ResponseDto assignTeacherToClass(@RequestBody Map<String, String> requestBody) {
        String teacherId = requestBody.get("teacherId");
        String className = requestBody.get("className");
        ResponseDto response = classService.assignTeacherToClass(teacherId, className);
        return response;
    }

    @PostMapping("/assign-student-to-class")
    public ResponseDto assignStudentToClass(@RequestBody Map<String, String> requestBody) {
        String studentId = requestBody.get("studentId");
        String className = requestBody.get("className");
        ResponseDto response = classService.assignStudentToClass(studentId, className);
        return response;
    }

    @DeleteMapping("/remove-teacher-from-class")
    public ResponseDto removeTeacherFromClass(@RequestBody Map<String, String> requestBody) {
        String teacherId = requestBody.get("teacherId");
        String className = requestBody.get("className");
        ResponseDto response = classService.removeTeacherFromClass(teacherId, className);
        return response;
    }

    @DeleteMapping("/remove-student-from-class")
    public ResponseDto removeStudentFromClass(@RequestBody String studentId) {
        ResponseDto response = classService.removeStudentFromClass(studentId);
        return response;
    }

    @GetMapping("/get-all-students-in-class/{className}")
    public ResponseDto getStudentsInClass(@PathVariable String className) {
        ResponseDto response = classService.getStudentsInClass(className);
        return response;
    }

    @GetMapping("/get-all-teachers-in-class/{className}")
    public ResponseDto getTeachersInClass(@PathVariable String className) {
        ResponseDto response = classService.getTeachersInClass(className);
        return response;
    }
}
