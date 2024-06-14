package school.student.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.student.exceptions.StudentNotFoundException;
import school.student.model.dto.AddStudentDto;
import school.student.model.dto.StudentDto;
import school.student.model.entity.Student;
import school.student.model.mapper.StudentMapper;
import school.student.repository.StudentRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    @Override
    public StudentDto addStudent(AddStudentDto addStudentDto) {
        Student student = studentMapper.addStudentDtotoEntity(addStudentDto);
        studentRepo.save(student);
        StudentDto studentDto = studentMapper.toStudentDto(student);
        return studentDto;
    }

    @Override
    public StudentDto getStudent(String id) {
       Optional<Student> student = studentRepo.findById(id);
       if (student.isPresent()) {
           return studentMapper.toStudentDto(student.get());
       }
        throw new StudentNotFoundException("Student not found!");
    }


    @Override
    public StudentDto updateStudent(String id, StudentDto studentDto) {
        Optional<Student> studentEntityOpt = studentRepo.findById(id);
        if (studentEntityOpt.isPresent()) {
            Student studentEntity = studentEntityOpt.get();
            studentMapper.updateEntityFromDto(studentDto, studentEntity);
            studentRepo.save(studentEntity);
            return studentMapper.entityToStudentDto(studentEntity);
        }
        throw new RuntimeException("Student with id(" + id + ") not found!");
    }

    @Override
    public void deleteStudent(String id) {
        Optional<Student> studentEntity = studentRepo.findById(id);
        if (studentEntity.isPresent()) {
            studentRepo.delete(studentEntity.get());
        } else {
            throw new RuntimeException("Student with id(" + id + ") not found!");
        }
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(studentMapper::entityToStudentDto)
                .collect(Collectors.toList());
    }

}
