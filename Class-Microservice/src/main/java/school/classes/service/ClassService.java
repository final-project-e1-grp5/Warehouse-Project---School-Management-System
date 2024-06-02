package school.classes.service;

import school.classes.model.dto.ResponseDto;
import school.classes.model.entity.ClassEntity;

import java.util.Optional;


public interface ClassService {

    ResponseDto updateStudentClass(String studentId, String className);

    ResponseDto updateTeacherClass(String teacherId, String currentClassName, String newClassName);

    ResponseDto deleteClass(String className);

    ResponseDto getAllClasses();

    ResponseDto assignTeacherToClass(String teacherId, String className);

    ResponseDto assignStudentToClass(String studentId, String className);

    ResponseDto removeStudentFromClass(String studentId);

    ResponseDto removeTeacherFromClass(String teacherId, String className);

    ResponseDto getStudentsInClass(String className);

    ResponseDto getTeachersInClass(String className);

    ResponseDto getAllClassesForTeacher(String teacherId);

    ResponseDto deleteAllClassesForTeacher(String teacherId);




}
