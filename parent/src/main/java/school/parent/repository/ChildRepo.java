package school.parent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.parent.model.entity.Child;
import school.parent.model.entity.Parent;

public interface ChildRepo  extends JpaRepository<Child,String> {
    void deleteByParent(Parent parent);
}
