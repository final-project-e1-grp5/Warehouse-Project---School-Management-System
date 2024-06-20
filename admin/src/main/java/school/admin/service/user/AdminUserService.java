package school.admin.service.user;

import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;

import java.util.List;

public interface AdminUserService {
    UserDto addUser(AddUserDto userDto);
    List<UserDto> getAllUsers();
    UserDto updateUser(String id, UpdateUserDto updateUserDto);
    UserDto getUserById(String id);
    List<UserDto> getUsersByRole(String role);
    void deleteUserById(String id);
}
