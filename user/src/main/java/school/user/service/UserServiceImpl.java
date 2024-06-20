package school.user.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.user.exceptions.ConflictException;
import school.user.exceptions.UserNotFoundException;
import school.user.model.dto.AddUserDto;
import school.user.model.dto.UpdateUserDto;
import school.user.model.dto.UserDto;
import school.user.model.entity.User;
import school.user.model.mapper.UserMapper;
import school.user.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDto addUser(AddUserDto addUserDto) {
        if (userRepo.existsByUsername(addUserDto.getUsername())) {
            throw new ConflictException("Username already exists!");
        }
        if (userRepo.existsByEmail(addUserDto.getEmail())) {
            throw new ConflictException("Email already exists!");
        }

        User user = userMapper.addUserDtotoUser(addUserDto);
        User createdUser = userRepo.save(user);
        return userMapper.toUserDto(createdUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(userMapper.toUserDto(user));
        }
        return usersDto;
    }

    @Transactional
    @Override
    public UserDto updateUser(String id, UpdateUserDto updateUserDto) {
        User user = userRepo.findByUserId(id);
        if (user != null) {
            userMapper.updateUserFromDto(user, updateUserDto);
            userRepo.save(user);
            return userMapper.toUserDto(user);
        }
        throw new UserNotFoundException("User with id(" + id + ") not found!");
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        User user = userRepo.findByUserId(id);
        if (user != null) {
            userRepo.delete(user);
        } else {
            throw new UserNotFoundException("User with id(" + id + ") not found!");
        }
    }


    @Override
    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userRepo.findAllByRole(role);
        return userMapper.toUserDtos(users);
    }

    @Override
    public User authenticateUser(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password).orElse(null);
    }

    @Override
    public UserDto getUser(String id) {
        User user = userRepo.findByUserId(id);
        if (user != null) {
            return userMapper.toUserDto(user);
        }
        throw new RuntimeException("User with id(" + id + ") not found!");
    }

}
