package school.student.service;

import school.student.model.dto.AddStudentDto;
import school.student.model.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto addStudent(AddStudentDto addStudentDto);
    StudentDto getStudent(String id);
    StudentDto updateStudent(String id, StudentDto studentDto);
    void deleteStudent(String id);
    List<StudentDto> getAllStudents();
}

