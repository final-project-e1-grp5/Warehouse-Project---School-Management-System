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


   default void updateUserFromDto(User user, UpdateUserDto updateUserDto) {
       if ( updateUserDto == null ) {
           return;
       }

       if(updateUserDto.getUsername() != null){
           user.setUsername(updateUserDto.getUsername());
       }
       if(updateUserDto.getPassword() != null){
           user.setPassword(updateUserDto.getPassword());
       }
       if(updateUserDto.getEmail() != null){
           user.setEmail(updateUserDto.getEmail());
       }
       if(updateUserDto.getEnabled() != null){
           user.setEnabled(updateUserDto.getEnabled());
       }
       if(updateUserDto.getRole() != null){
           user.setRole(updateUserDto.getRole());
       }

   }


    List<UserDto> toUserDtos(List<User> users);
}