package school.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.attendance.model.entity.TeacherAttendance;
import java.time.LocalDate;
import java.util.List;

public interface TeacherAttendanceRepo extends JpaRepository<TeacherAttendance, Long> {

    List<TeacherAttendance> findByTeacherId(String teacherId);
    TeacherAttendance findByTeacherIdAndAttendanceDate(String teacherId, LocalDate date);

}
