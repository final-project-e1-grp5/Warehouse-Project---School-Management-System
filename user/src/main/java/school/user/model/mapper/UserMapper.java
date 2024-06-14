package school.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.user.model.dto.*;
import school.user.model.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
    UserDto addUserDtotoUserDto(AddUserDto userDto);
    User addUserDtotoUser(AddUserDto userDto);


    void updateUserFromDto(@MappingTarget User user, UpdateUserDto updateUserDto);

    List<UserDto> toUserDtos(List<User> users);
}