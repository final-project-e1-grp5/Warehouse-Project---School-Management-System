package school.admin.service.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.exceptions.StudentNotFoundException;
import school.admin.model.dto.studentDto.AddStudentDto;
import school.admin.model.dto.studentDto.AddStudentUserDto;
import school.admin.model.dto.studentDto.StudentDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.model.dto.teacherDto.TeacherDto;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;
import school.admin.model.mapper.AdminMapper;
import school.admin.proxy.StudentProxy;
import school.admin.proxy.UserProxy;
import school.admin.service.user.AdminUserService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminStudentServiceImpl implements AdminStudentService {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StudentProxy studentProxy;
    @Autowired private UserProxy userProxy;


    @Transactional
    @Override
    public StudentUserDto addStudent(AddStudentUserDto student){

// Convert AddStudentUserDto to AddUserDto and create the user
        AddUserDto addUserDto = adminMapper.addStudentUserDtoToAddUserDto(student);
        UserDto createdUserDto = adminUserService.addUser(addUserDto);

// Convert AddStudentUserDto to AddStudentDto and set the user ID
        AddStudentDto addStudentDto = adminMapper.addStudentUserDtoToAddStudentDto(student);
        addStudentDto.setUserId(createdUserDto.getUserId());

// Add student using StudentProxy
        StudentDto createdStudentDto = studentProxy.addStudent(addStudentDto);

// Convert StudentDto and UserDto to StudentUserDto
        return adminMapper.toStudentUserDto(createdStudentDto, createdUserDto);

    }

    @Override
    public StudentUserDto getStudent(String id) {
        StudentDto studentDto = studentProxy.getStudentById(id);
        if (studentDto != null) {
            UserDto userDto = adminUserService.getUserById(id);
            return adminMapper.toStudentUserDto(studentDto, userDto);
        } else {
            throw new StudentNotFoundException("Student with id(" + id + ")not found");
        }
    }

    @Transactional
    public StudentUserDto updateStudent(String id, StudentUserDto updateStudentUserDto) {
        // Convert UpdateStudentUserDto to StudentDto
        StudentDto updateStudentDto = adminMapper.studentUserDtoToStudentDto(updateStudentUserDto);

        // Convert UpdateStudentUserDto to UpdateUserDto
        UpdateUserDto updateUserDto = adminMapper.studentUserDtoToUpdateUserDto(updateStudentUserDto);

        // get StudentUser profile
        StudentUserDto existStudent = getStudent(id);

        if (existStudent != null) {
            // Update student using StudentProxy if the student exists
            StudentDto updatedStudentDto = studentProxy.updateStudent(id, updateStudentDto);

            // Update user using UserProxy if the student exists
            UserDto updatedUserDto = userProxy.updateUser(id, updateUserDto);

            // Convert updated StudentDto and updated UserDto to StudentUserDto;
            return adminMapper.toStudentUserDto(updatedStudentDto, updatedUserDto);
        }
        throw new RuntimeException("Student not found");
    }

    @Transactional
    @Override
    public void deleteStudent(String id) {
        StudentDto studentDto = studentProxy.getStudentById(id);
        if (studentDto != null) {
        studentProxy.deleteStudent(id);
        userProxy.deleteUser(id);
        } throw new RuntimeException("Student not found!");
    }

    @Override
    public List<StudentUserDto> getAll() {

        List<UserDto> userDtos = adminUserService.getUsersByRole("student");
        // Extract userIds from userDtos
        List<String> userIds = userDtos.stream()
                .map(UserDto::getUserId)
                .toList();

        // Fetch studentDtos by userIds using studentProxy
        List<StudentDto> studentDtos = userIds.stream()
                .map(studentProxy::getStudentById)
                .collect(Collectors.toList());


        return adminMapper.toStudentUserDtoList(studentDtos, userDtos);
    }
}