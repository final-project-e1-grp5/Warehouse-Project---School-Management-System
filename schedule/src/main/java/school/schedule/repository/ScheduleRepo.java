package school.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.schedule.model.entity.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
}
