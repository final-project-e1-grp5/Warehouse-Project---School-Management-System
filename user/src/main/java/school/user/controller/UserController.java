package school.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.user.model.dto.AddUserDto;
import school.user.model.dto.UpdateUserDto;
import school.user.model.dto.UserDto;
import school.user.model.dto.UserLoginDto;
import school.user.model.entity.User;
import school.user.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDto loginRequest) {
        try {
            User user =
                    userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            if (user != null) {
                return ResponseEntity.ok().body(Map.of("message", "Login successful", "user", user));
            } else {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Internal server error", "error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody AddUserDto addUserDto) {
        UserDto addedUserDto = userService.addUser(addUserDto);
        return ResponseEntity.ok(addedUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUserDto = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<List<UserDto>> getAllUsersByRole(@PathVariable String role) {
        List<UserDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
}
