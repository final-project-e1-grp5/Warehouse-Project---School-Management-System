package school.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.schedule.model.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByClassId(Long classId);

    List<Schedule> findByTeacherId(String teacherId);
}
