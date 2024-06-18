package school.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.attendance.model.entity.StudentAttendance;

import java.time.LocalDate;
import java.util.List;

public interface StudentAttendanceRepo extends JpaRepository<StudentAttendance, Long> {
    List<StudentAttendance> findByStudentId(String studentId);
    StudentAttendance findByStudentIdAndAttendanceDate(String studentId, LocalDate attendanceDate);
    List<StudentAttendance> findByClassId(Long classId);

}
