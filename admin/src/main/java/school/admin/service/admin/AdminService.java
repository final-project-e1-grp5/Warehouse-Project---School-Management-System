package school.admin.service.admin;

import school.admin.model.dto.adminDto.AddAdminUserDto;
import school.admin.model.dto.adminDto.AdminUserDto;
import school.admin.model.dto.adminDto.UpdateAdminUserDto;

import java.util.List;

public interface AdminService {

    AdminUserDto addAdmin(AddAdminUserDto addAdminUserDto);

    AdminUserDto getAdmin(String id);

    List<AdminUserDto> getAllAdmins();

    AdminUserDto updateAdmin(String id,UpdateAdminUserDto adminDto);

    void deleteAdmin(String id);

}
