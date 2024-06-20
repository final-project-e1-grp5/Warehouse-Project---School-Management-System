package school.user.service;

import school.user.model.dto.AddUserDto;
import school.user.model.dto.UpdateUserDto;
import school.user.model.dto.UserDto;
import school.user.model.entity.User;

import java.util.List;

public interface UserService {

    UserDto addUser(AddUserDto addUserDto);

    UserDto getUser(String id);

    List<UserDto> getAllUsers();

    UserDto updateUser(String id, UpdateUserDto updateUserDto);

    void deleteUser(String id);

    List<UserDto> getUsersByRole(String role);

    User authenticateUser(String email, String password);
}
