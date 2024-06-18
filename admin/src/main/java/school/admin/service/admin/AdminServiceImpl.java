package school.admin.service.admin;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.model.dto.adminDto.*;
import school.admin.model.dto.userDto.AddUserDto;
import school.admin.model.dto.userDto.UpdateUserDto;
import school.admin.model.dto.userDto.UserDto;
import school.admin.model.entity.Admin;
import school.admin.model.mapper.AdminMapper;
import school.admin.proxy.UserProxy;
import school.admin.repository.AdminRepo;
import school.admin.service.user.AdminUserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserProxy userProxy;

    @Transactional
    @Override
    public AdminUserDto addAdmin(AddAdminUserDto addAdminUserDto) {

            // Convert AddAdminUserDto to AddUserDto and create the user
            AddUserDto addUserDto = adminMapper.addAdminUserDtotoAddUserDto(addAdminUserDto);
            UserDto createdUserDto = adminUserService.addUser(addUserDto);

            // Convert AddAdminUserDto to Admin entity and set the user ID
            Admin admin = adminMapper.addAdminUserDtotoAdmin(addAdminUserDto);
            admin.setUserId(createdUserDto.getUserId());

            // Save the admin entity
            Admin createdAdmin = adminRepo.save(admin);

            // Convert Admin entity to AdminDto and combine with UserDto to create AdminUserDto
            AdminDto adminDto = adminMapper.entityToAdminDto(createdAdmin);
            AdminUserDto adminUserDto = adminMapper.toAdminUserDto(adminDto, createdUserDto);

            return adminUserDto;

    }
    @Override
    public AdminUserDto getAdmin(String id) {
        // Retrieve Admin entity from repository
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Convert Admin entity to AdminDto
        AdminDto adminDto = adminMapper.entityToAdminDto(admin);

        // Retrieve UserDto from userProxy
        UserDto userDto = userProxy.getUser(id);
        if( userDto !=null) {

        // Combine AdminDto and UserDto to create AdminUserDto
        return adminMapper.toAdminUserDto(adminDto, userDto);
    }
        throw new RuntimeException("User not found");
}
    @Override
    public List<AdminUserDto> getAllAdmins() {

        List<Admin> admins = adminRepo.findAll();
        List<String> userIds = admins.stream()
                .map(Admin::getUserId)
                .toList();
        List<UserDto> userDtos = userIds.stream()
                .map(userProxy::getUser)
                .toList();

        List<AdminDto> adminDtos = adminMapper.toAdminDtoList(admins);

        List<AdminUserDto> adminUserDtos = adminMapper.toAdminUserDtoList(adminDtos,userDtos);


        return adminUserDtos;
    }


    @Transactional
    @Override
    public AdminUserDto updateAdmin(String id,UpdateAdminUserDto updateAdminUserDto) {
        // Step 1: Update User Profile
        UserDto updatedUserDto = updateUserProfile(id,updateAdminUserDto);

        // Step 2: Update Admin Profile
        AdminUserDto adminUserDto = updateAdminProfile(id,updateAdminUserDto, updatedUserDto);

        return adminUserDto;
    }

    @Transactional
    @Override
    public void deleteAdmin(String id) {
        adminRepo.deleteById(id);
        userProxy.deleteUser(id);
    }

    /**
     * Updates the User profile based on the provided AddAdminUserDto.
     * Returns the updated UserDto from the user microservice.
     */
    private UserDto updateUserProfile(String id, UpdateAdminUserDto updateAdminUserDto) {

        UpdateUserDto updateUserDto = adminMapper.updateAdminUserDtoToUpdateUserDto(updateAdminUserDto);
        // Check if the User profile exists
        UserDto existingUserDto = userProxy.getUser(id);

        if (existingUserDto != null) {
            // Update the existing User profile
            return userProxy.updateUser(id,updateUserDto);
        } else {
            throw new RuntimeException("User profile not found");
        }
    }

    /**
     * Updates the Admin profile based on the provided UpdateAdminUserDto and updated UserDto.
     * Returns the updated AdminUserDto.
     */
    private AdminUserDto updateAdminProfile(String id,UpdateAdminUserDto updateAdminUserDto, UserDto updatedUserDto) {
        // Find the Admin profile by ID
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();

            // Convert UpdateAdminUserDto to AdminDto
            AdminDto adminDto = adminMapper.updateAdminUserDtoToAdminDto(updateAdminUserDto);

            // Update existing Admin entity with new values
            adminMapper.updateAdminFromDto(adminDto, admin);

            // Save the updated Admin entity
            admin = adminRepo.save(admin);

            // Map Admin and User DTOs to AdminUserDto for response
            return adminMapper.toAdminUserDto(adminDto, updatedUserDto);
        } else {
            throw new RuntimeException("Admin not found");
        }

    }
}