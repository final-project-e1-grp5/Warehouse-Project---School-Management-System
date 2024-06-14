package school.admin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;

import java.util.List;

@FeignClient(name = "USER-SERVICE")

public interface UserProxy {

    @PostMapping("user/add")
     UserDto addUser(@RequestBody AddUserDto addUserDto);

    @GetMapping("/user/{id}")
   UserDto getUser(@PathVariable String id);

    @GetMapping("/user/get-all")
    List<UserDto> getAllUsers();

    @PutMapping("/user/{id}")
    UserDto updateUser(@PathVariable String id,@RequestBody UpdateUserDto updateUserDto);

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable String id);

    @GetMapping("/user/get-by-role/{role}")
    List<UserDto> getAllUsersByRole(@PathVariable String role);
}