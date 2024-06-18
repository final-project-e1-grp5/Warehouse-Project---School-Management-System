package school.admin.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;
import school.admin.proxy.UserProxy;

import java.util.List;


@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserProxy userProxy;

    @Override
    public UserDto addUser(AddUserDto addUserDto){

       UserDto createdUser = userProxy.addUser(addUserDto);

       return createdUser;

    }

    @Override
    public List<UserDto> getAllUsers() {
        return userProxy.getAllUsers();
    }

    @Override
    public UserDto updateUser(String id, UpdateUserDto updateUserDto) {
        return userProxy.updateUser(id,updateUserDto);
    }

    @Override
    public UserDto getUserById(String id) {
        return userProxy.getUser(id);
    }

    @Override
    public List<UserDto> getUsersByRole(String role) {
        return userProxy.getAllUsersByRole(role);
    }

    @Override
    public void deleteUserById(String id) {
         userProxy.deleteUser(id);
    }

}
