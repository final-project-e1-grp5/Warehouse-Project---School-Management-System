package school.admin.service.teacher;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.exceptions.TeacherNotFoundException;
import school.admin.model.dto.teacherDto.*;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;
import school.admin.model.mapper.AdminMapper;
import school.admin.proxy.TeacherProxy;
import school.admin.proxy.UserProxy;
import school.admin.service.user.AdminUserService;

import java.util.List;


@Service
public class AdminTeacherServiceImpl implements AdminTeacherService {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private TeacherProxy teacherProxy;

    @Autowired
    AdminUserService adminUserServiceImpl;

    @Autowired
    private UserProxy userProxy;

    @Transactional
    @Override
    public TeacherUserDto addTeacher(AddTeacherUserDto addTeacherUserDto) {
        // Convert AddTeacherUserDto to UserDto and create the user
        AddUserDto addUserDto = adminMapper.addTeacherUserDtoToAddUserDto(addTeacherUserDto);
        UserDto createdUserDto = adminUserService.addUser(addUserDto);

        // Convert AddTeacherUserDto to TeacherDto and set the user ID
        TeacherDto teacherDto = adminMapper.addTeacherUserDtoToTeacherDto(addTeacherUserDto);
        teacherDto.setUserId(createdUserDto.getUserId());

        // Add teacher using TeacherProxy
        TeacherDto createdTeacherDto = teacherProxy.addTeacher(teacherDto);

        // Convert TeacherDto and UserDto to TeacherUserDto
        return adminMapper.toTeacherUserDto(createdTeacherDto, createdUserDto);
    }

    @Override
    public TeacherUserDto getTeacher(String id) {

        TeacherDto teacherDto = teacherProxy.getTeacher(id);
        if (teacherDto != null) {
            UserDto userDto = adminUserService.getUserById(id);
            return adminMapper.toTeacherUserDto(teacherDto, userDto);
        } else {
            throw new TeacherNotFoundException("Teacher with id(" + id + ") not found!");
        }
    }

    @Transactional
    @Override
    public TeacherUserDto updateTeacher(String id,UpdateTeacherUserDto updateTeacherUserDto) {

        // Convert UpdateTeacherUserDto to TeacherDto
        UpdateTeacherDto updateTeacherDto = adminMapper.updateTeacherUserDtoToUpdateTeacherDto(updateTeacherUserDto);
        // Convert UpdateTeacherUserDto to UserDto
        UpdateUserDto updateUserDto = adminMapper.updateTeacherUserDtoToUpdateUserDto(updateTeacherUserDto);


        // get TeacherUser profile
        TeacherUserDto existTeacher= getTeacher(id);

        if (existTeacher != null) {
            // Update teacher using TeacherProxy if the teacher exist
            TeacherDto updatedTeacherDto = teacherProxy.updateTeacher(id, updateTeacherDto);

            // Update user using UserProxy if the teacher exist
            UserDto updatedUserDto = userProxy.updateUser(id, updateUserDto);

            // Convert updated TeacherDto and updated UserDto to TeacherUserDto;
            return adminMapper.toTeacherUserDto(updatedTeacherDto, updatedUserDto);
        } throw new TeacherNotFoundException("Teacher with id(" + id + ") not found!");

    }

    @Transactional
    @Override
    public void deleteTeacher(String id) {
        TeacherUserDto teacherUserDto = getTeacher(id);
        if (teacherUserDto != null) {
            adminUserService.deleteUserById(id);
            teacherProxy.deleteTeacher(id);
        }else{ throw new TeacherNotFoundException("Teacher with id(" + id + ") not found!");
        }
    }

    @Override
    public List<TeacherUserDto> getAllTeachers() {

        List<TeacherDto> teacherDtos = teacherProxy.getAllTeachers();
        List<UserDto> userDtos = adminUserService.getAllUsers();
        return adminMapper.toTeacherUserDtoList(teacherDtos, userDtos);
    }
}
