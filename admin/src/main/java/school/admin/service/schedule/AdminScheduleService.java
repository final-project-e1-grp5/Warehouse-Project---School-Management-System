package school.admin.service.schedule;

import school.admin.model.dto.schedule.AddScheduleDto;
import school.admin.model.dto.schedule.ScheduleDto;
import java.util.List;

public interface AdminScheduleService {
    ScheduleDto addSchedule(AddScheduleDto addScheduleDto);
    ScheduleDto getSchedule(Long id);
    List<ScheduleDto> getSchedulesByClass(Long classId);
    List<ScheduleDto> getSchedulesByTeacher(String teacherId);
    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto);
    void deleteSchedule(Long id);
}
