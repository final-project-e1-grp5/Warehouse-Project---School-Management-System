package school.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.adminDto.AddAdminUserDto;
import school.admin.model.dto.adminDto.AdminUserDto;
import school.admin.model.dto.adminDto.UpdateAdminUserDto;
import school.admin.service.admin.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<AdminUserDto> addAdmin(@RequestBody AddAdminUserDto admin) {
        AdminUserDto adminUserDto = adminService.addAdmin(admin);
        return ResponseEntity.ok(adminUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUserDto> getAdmin(@PathVariable String id) {
        AdminUserDto adminUserDto = adminService.getAdmin(id);
        if (adminUserDto != null) {
            return ResponseEntity.ok(adminUserDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUserDto> updateAdmin(@PathVariable String id,@RequestBody UpdateAdminUserDto updateAdminUserDto) {
        try {
            AdminUserDto updatedAdmin = adminService.updateAdmin( id, updateAdminUserDto);
            return ResponseEntity.ok(updatedAdmin);
            // Handles both User and Admin not found scenarios
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminUserDto>> getAllAdmins() {
        List<AdminUserDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }
}
