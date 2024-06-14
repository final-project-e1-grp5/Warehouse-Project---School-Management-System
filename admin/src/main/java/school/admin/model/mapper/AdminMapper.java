package school.admin.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import school.admin.model.dto.adminDto.*;
import school.admin.model.dto.studentDto.AddStudentDto;
import school.admin.model.dto.studentDto.AddStudentUserDto;
import school.admin.model.dto.studentDto.StudentDto;
import school.admin.model.dto.studentDto.StudentUserDto;
import school.admin.model.dto.teacherDto.*;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;
import school.admin.model.entity.Admin;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(source = "userDto.userId", target = "userId")
    TeacherUserDto toTeacherUserDto(TeacherDto teacherDto, UserDto userDto);

    AdminDto entityToAdminDto(Admin admin);
    @Mapping(source = "userDto.userId", target = "userId")
    AdminUserDto toAdminUserDto(AdminDto adminDto, UserDto userDto);
    List<AdminDto> toAdminDtoList(List<Admin> adminList);

    void updateAdminFromDto(AdminDto adminDto,@MappingTarget Admin admin);

    default List<AdminUserDto> toAdminUserDtoList(List<AdminDto> adminDtos, List<UserDto> userDtos) {
        if (adminDtos == null || userDtos == null || adminDtos.size() != userDtos.size()) {
            throw new IllegalArgumentException("Lists must be non-null and of the same size");
        }

        int size = adminDtos.size();
        List<AdminUserDto> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            AdminDto adminDto = adminDtos.get(i);
            UserDto userDto = userDtos.get(i);
            result.add(toAdminUserDto(adminDto, userDto));
        }
        return result;
    }


    UserDto studentUserDtotoUserDto(StudentUserDto student);

    StudentDto studentUserDtoStudentDto(StudentUserDto student);

    StudentUserDto toStudentUserDto(StudentDto studentDto, UserDto userDto);

    UserDto addAdminUserDtotoUserDto(AddAdminUserDto addAdminUserDto);

    Admin addAdminUserDtotoAdmin(AddAdminUserDto addAdminUserDto);

    AdminDto updateAdminUserDtoToAdminDto(UpdateAdminUserDto updateAdminUserDto);

    UserDto updateAdminUserDtoToUserDto(UpdateAdminUserDto updateAdminUserDto);

    TeacherDto addTeacherUserDtoToTeacherDto(AddTeacherUserDto addTeacherUserDto);


    default List<TeacherUserDto> toTeacherUserDtoList(List<TeacherDto> teacherDtos, List<UserDto> userDtos) {
        if (teacherDtos == null || userDtos == null || teacherDtos.size() != userDtos.size()) {
            throw new IllegalArgumentException("Lists must be non-null and of the same size");
        }

        int size = teacherDtos.size();
        List<TeacherUserDto> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TeacherDto teacherDto = teacherDtos.get(i);
            UserDto userDto = userDtos.get(i);
            result.add(toTeacherUserDto(teacherDto, userDto));
        }
        return result;
    }


    TeacherDto updateTeacherUserDtoToTeacherDto(UpdateTeacherUserDto updateTeacherUserDto);

    StudentDto studentUserDtoToStudentDto(StudentUserDto studentUserDto);

    UserDto updateTeacherUserDtoToUserDto(UpdateTeacherUserDto updateTeacherUserDto);

    UserDto studentUserDtoToUserDto(StudentUserDto updateStudentUserDto);

    AddUserDto studentUserDtoToAddUserDto(StudentUserDto student);

    AddUserDto addTeacherUserDtoToAddUserDto(AddTeacherUserDto addTeacherUserDto);

    AddUserDto addAdminUserDtotoAddUserDto(AddAdminUserDto addAdminUserDto);

    UpdateTeacherDto updateTeacherUserDtoToUpdateTeacherDto(UpdateTeacherUserDto updateTeacherUserDto);

    UpdateTeacherDto teacherDtoToUpdateTeacherDto(TeacherDto teacherDto);

    UpdateUserDto updateAdminUserDtoToUpdateUserDto(UpdateAdminUserDto updateAdminUserDto);

    UpdateUserDto studentUserDtoToUpdateUserDto(StudentUserDto updateStudentUserDto);

    UpdateUserDto updateTeacherUserDtoToUpdateUserDto(UpdateTeacherUserDto updateTeacherUserDto);

    AddUserDto addStudentUserDtoToAddUserDto(AddStudentUserDto student);

    StudentDto addStudentUserDtoToStudentDto(AddStudentUserDto student);

    AddStudentDto addStudentUserDtoToAddStudentDto(AddStudentUserDto student);

    default List<StudentUserDto> toStudentUserDtoList(List<StudentDto> studentDtos, List<UserDto> userDtos) {
        if (studentDtos == null || userDtos == null || studentDtos.size() != userDtos.size()) {
            throw new IllegalArgumentException("Lists must be non-null and of the same size");
        }

        int size = studentDtos.size();
        List<StudentUserDto> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StudentDto studentDto = studentDtos.get(i);
            UserDto userDto = userDtos.get(i);
            result.add(toStudentUserDto(studentDto, userDto));
        }
        return result;
    }
}