package school.admin.service.student;

import org.springframework.http.ResponseEntity;
import school.admin.model.dto.studentDto.AddStudentUserDto;
import school.admin.model.dto.studentDto.StudentUserDto;

import java.util.List;

public interface AdminStudentService {
    StudentUserDto addStudent(AddStudentUserDto student);
    StudentUserDto getStudent(String id);
    StudentUserDto updateStudent(String id,StudentUserDto studentUserDto);
    void deleteStudent(String id);

    List<StudentUserDto> getAll();
}
