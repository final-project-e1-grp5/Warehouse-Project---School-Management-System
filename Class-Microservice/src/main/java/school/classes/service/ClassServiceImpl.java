package school.classes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.classes.model.dto.ResponseDto;
import school.classes.model.entity.ClassEntity;
import school.classes.repository.ClassRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Override
    public ResponseDto updateStudentClass(String studentId, String className) {
        List<ClassEntity> classes = classRepository.findByStudentId(studentId);
        for (ClassEntity classEntity : classes) {
            classEntity.setClassName(className);
            classRepository.save(classEntity);
        }
        return new ResponseDto(200, null, "Class name updated for student", System.currentTimeMillis(), "/updateStudentClass", null);
    }

    @Override
    public ResponseDto updateTeacherClass(String teacherId, String currentClassName, String newClassName) {
        // Check if the teacher exists in the database
        List<ClassEntity> teacher = classRepository.findByTeacherId(teacherId);
        if (teacher.isEmpty()) {
            return new ResponseDto(404, "Not Found", "Teacher not found", System.currentTimeMillis(), "/updateTeacherClass", null);
        }

        // Check if the teacher is already assigned to the new class
        Optional<ClassEntity> existingNewClass= classRepository.findByTeacherIdAndClassName(teacherId, newClassName);
        if (existingNewClass.isPresent()) {
            return new ResponseDto(409, "Conflict", "Teacher is already assigned to new class", System.currentTimeMillis(), "/updateTeacherClass", null);
        }

        // Check if the teacher is assigned to the current class
        Optional<ClassEntity> currentClass = classRepository.findByTeacherIdAndClassName(teacherId, currentClassName);
        if (currentClass.isEmpty()) {
            return new ResponseDto(404, "Not Found", "Teacher is not assigned to current class", System.currentTimeMillis(), "/updateTeacherClass", null);
        }



        // Update teacher's class name from current to new class
        currentClass.get().setClassName(newClassName);
            classRepository.save(currentClass.get());


        return new ResponseDto(200, null, "Teacher's class updated successfully", System.currentTimeMillis(), "/updateTeacherClass", null);
    }


    @Override
    public ResponseDto deleteClass(String className) {
        List<ClassEntity> existingClasses = classRepository.findByClassName(className);
        if (!existingClasses.isEmpty()) {
            classRepository.deleteAll(existingClasses);
            return new ResponseDto(200, null, "Class deleted", System.currentTimeMillis(), "/deleteClass", null);
        }
        return new ResponseDto(404, "Class not found", "No class found with the provided name", System.currentTimeMillis(), "/deleteClass", null);
    }

    @Override
    public ResponseDto getAllClasses() {
        List<ClassEntity> classes = classRepository.findAll();
        List<String> uniqueClasses = classes.stream()
                .map(ClassEntity::getClassName)
                .distinct()
                .toList();
        List<Map<String, String>> payload = uniqueClasses.stream()
                .map(className -> Map.of("className", className))
                .collect(Collectors.toList());
        return new ResponseDto(200, null, "All classes retrieved", System.currentTimeMillis(), "/getAllClasses", payload);
    }

    @Override
    public ResponseDto assignTeacherToClass(String teacherId, String className) {
        Optional<ClassEntity> existingClass = classRepository.findByTeacherIdAndClassName(teacherId, className);
        if (existingClass.isPresent()) {
            return new ResponseDto(409, "Conflict", "Teacher is already assigned to this class", System.currentTimeMillis(), "/assignTeacherToClass", null);
        }
        ClassEntity newClass = new ClassEntity();
        newClass.setClassName(className);
        newClass.setTeacherId(teacherId);
        classRepository.save(newClass);
        return new ResponseDto(200, null, "Teacher assigned to class", System.currentTimeMillis(), "/assignTeacherToClass", null);
    }

    @Override
    public ResponseDto assignStudentToClass(String studentId, String className) {
        Optional<ClassEntity> existingClass = classRepository.findByStudentIdAndClassName(studentId, className);
        if (existingClass.isPresent()) {
            return new ResponseDto(409, "Conflict", "Student is already assigned to this class", System.currentTimeMillis(), "/assignStudentToClass", null);
        }
        ClassEntity newClass = new ClassEntity();
        newClass.setClassName(className);
        newClass.setStudentId(studentId);
        classRepository.save(newClass);
        return new ResponseDto(200, null, "Student assigned to class", System.currentTimeMillis(), "/assignStudentToClass", null);
    }

    @Override
    public ResponseDto removeTeacherFromClass(String teacherId, String className) {
        Optional<ClassEntity> existingClass = classRepository.findByTeacherIdAndClassName(teacherId, className);
        if (existingClass.isPresent()) {
            classRepository.delete(existingClass.get());
            return new ResponseDto(200, null, "Teacher removed from class", System.currentTimeMillis(), "/removeTeacherFromClass", null);
        }
        return new ResponseDto(404, "Not Found", "Teacher not found in the specified class", System.currentTimeMillis(), "/removeTeacherFromClass", null);
    }

    @Override
    public ResponseDto removeStudentFromClass(String studentId) {
        List<ClassEntity> existingClasses = classRepository.findByStudentId(studentId);
        if (!existingClasses.isEmpty()) {
            for (ClassEntity classEntity : existingClasses) {
                classEntity.setStudentId(null);
                classRepository.save(classEntity);
            }
            return new ResponseDto(200, null, "Student removed from class", System.currentTimeMillis(), "/removeStudentFromClass", null);
        }
        return new ResponseDto(404, "Student not found", "No class found with the provided student ID", System.currentTimeMillis(), "/removeStudentFromClass", null);
    }

    @Override
    public ResponseDto getStudentsInClass(String className) {
        List<ClassEntity> existingClasses = classRepository.findByClassName(className);
        if (!existingClasses.isEmpty()) {
            // Filter classes that have null studentId and match the className
            List<String> students = existingClasses.stream()
                    .filter(classEntity -> classEntity.getStudentId() != null)
                    .map(ClassEntity::getStudentId)
                    .toList();

            if (students.isEmpty()) {
                return new ResponseDto(404, "Students not found", "No students found for the provided class name", System.currentTimeMillis(), "/getStudentsInClass", null);
            }

            return new ResponseDto(200, null, "Students in class retrieved", System.currentTimeMillis(), "/getStudentsInClass", List.of(Map.of("students", students.toString())));
        }

        return new ResponseDto(404, "Class not found", "No class found with the provided name", System.currentTimeMillis(), "/getStudentsInClass", null);
    }

    @Override
    public ResponseDto getTeachersInClass(String className) {
        List<ClassEntity> existingClasses = classRepository.findByClassName(className);
        if (!existingClasses.isEmpty()) {
            // Filter classes that have null teacherId and match the className
            List<String> teachers = existingClasses.stream()
                    .filter(classEntity -> classEntity.getTeacherId() != null)
                    .map(ClassEntity::getTeacherId)
                    .toList();

            if (teachers.isEmpty()) {
                return new ResponseDto(404, "Teachers not found", "No teachers found for the provided class name", System.currentTimeMillis(), "/getTeachersInClass", null);
            }

            return new ResponseDto(200, null, "Teachers in class retrieved", System.currentTimeMillis(), "/getTeachersInClass", List.of(Map.of("teachers", teachers.toString())));
        }

        return new ResponseDto(404, "Class not found", "No class found with the provided name", System.currentTimeMillis(), "/getTeachersInClass", null);
    }
}
