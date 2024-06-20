package school.admin.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.exceptions.TeacherNotFoundException;
import school.admin.model.dto.classes.AddClassDto;
import school.admin.model.dto.classes.ClassDto;
import school.admin.model.dto.studentDto.StudentDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.model.dto.teacherDto.TeacherDto;
import school.admin.model.dto.teacherDto.TeacherUserDto;
import school.admin.model.dto.teacherDto.UpdateTeacherDto;
import school.admin.model.mapper.AdminMapper;
import school.admin.proxy.ClassProxy;
import school.admin.proxy.StudentProxy;
import school.admin.proxy.TeacherProxy;
import school.admin.service.student.AdminStudentService;
import school.admin.service.teacher.AdminTeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminClassServiceImpl implements AdminClassService {


    @Autowired
    private ClassProxy classProxy;
    @Autowired
    private TeacherProxy teacherProxy;
    @Autowired
    private StudentProxy studentProxy;
    @Autowired
    private AdminStudentService adminStudentService;
    @Autowired
    private AdminTeacherService adminTeacherService;
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public ClassDto addClass(AddClassDto addClassDto) {
        return classProxy.addClass(addClassDto);
    }

    @Override
    public ClassDto getClass(Long id) {
        return classProxy.getClassById(id);
    }

    @Override
    public ClassDto updateClass(Long id, ClassDto classDto) {
        return classProxy.updateClass(id, classDto);
    }

    @Override
    public void deleteClass(Long id) {
        classProxy.deleteClass(id);
    }

    @Override
    public void assignTeacherToClass(Long id, String teacherId) {
        // Get teacher profile
        TeacherDto teacherDto = teacherProxy.getTeacher(teacherId);
        // Get teacher class Ids
        List<Long> classIds = teacherDto.getClassIds();
        //Add the class Id to the teacher's class Ids List and save it
        classIds.add(id);
        teacherDto.setClassIds(classIds);
        UpdateTeacherDto updateTeacherDto = adminMapper.teacherDtoToUpdateTeacherDto(teacherDto);
        teacherProxy.updateTeacher(teacherId, updateTeacherDto);

        // update class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> teacherIds = classDto.getTeacherIds();
        teacherIds.add(teacherId);
        classDto.setTeacherIds(teacherIds);
        classProxy.updateClass(id, classDto);

    }

    @Override
    public void assignStudentToClass(Long id, String studentId) {
        // Get Student profile
        StudentDto studentDto = studentProxy.getStudentById(studentId);
        // Set Student class
        studentDto.setClassId(id);

        studentProxy.updateStudent(studentId, studentDto);

        // update class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> studentIds = classDto.getStudentIds();
        studentIds.add(studentId);
        classDto.setTeacherIds(studentIds);
        classProxy.updateClass(id, classDto);
    }

    @Override
    public void removeTeacherFromClass(Long id, String teacherId) {
        // Get teacher profile
        TeacherDto teacherDto = teacherProxy.getTeacher(teacherId);
        // Get teacher class Ids
        List<Long> classIds = teacherDto.getClassIds();
        // Remove the class Id from the teacher's class Ids List and save it
        classIds.remove(id);
        teacherDto.setClassIds(classIds);
        UpdateTeacherDto updateTeacherDto = adminMapper.teacherDtoToUpdateTeacherDto(teacherDto);
        teacherProxy.updateTeacher(teacherId, updateTeacherDto);

        // Update class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> teacherIds = classDto.getTeacherIds();
        teacherIds.remove(teacherId);
        classDto.setTeacherIds(teacherIds);
        classProxy.updateClass(id, classDto);
    }

    @Override
    public void removeStudentFromClass(Long id, String studentId) {
        // Get student profile
        StudentDto studentDto = studentProxy.getStudentById(studentId);
        // Clear student class
        studentDto.setClassId(null);
        studentProxy.updateStudent(studentId, studentDto);

        // Update class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> studentIds = classDto.getStudentIds();
        studentIds.remove(studentId);
        classDto.setStudentIds(studentIds);
        classProxy.updateClass(id, classDto);
    }

    @Override
    public List<StudentUserDto> getAllStudentsByClassId(Long id) {
        // Get class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> studentIds = classDto.getStudentIds();

        // Fetch all student profiles
        return studentIds.stream()
                .map(adminStudentService::getStudent)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherUserDto> getAllTeachersByClassId(Long id) {
        // Get class profile
        ClassDto classDto = classProxy.getClassById(id);
        List<String> teacherIds = classDto.getTeacherIds();

        // Fetch all teacher profiles
        return teacherIds.stream()
                .map(adminTeacherService::getTeacher)
                .collect(Collectors.toList());
    }

}


