package school.schedule.service;

import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<ScheduleDto> findSchedulesByClassId(Long classId);

    List<ScheduleDto> findSchedulesByTeacherId(String teacherId);

    ScheduleDto addSchedule(AddScheduleDto scheduleDto);

    ScheduleDto updateSchedule(Long scheduleId,AddScheduleDto updateScheduleDto);
    void deleteSchedule (Long scheduleId);

}


