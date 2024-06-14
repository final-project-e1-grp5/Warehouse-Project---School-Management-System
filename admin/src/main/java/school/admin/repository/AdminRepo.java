package school.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.admin.model.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {
}
