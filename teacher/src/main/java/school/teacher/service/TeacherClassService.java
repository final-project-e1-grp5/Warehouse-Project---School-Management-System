package school.teacher.service;

import org.springframework.data.jpa.repository.JpaRepository;
import school.teacher.model.entity.TeacherClass;


public interface TeacherClassService extends JpaRepository<TeacherClass, Long> {

}
