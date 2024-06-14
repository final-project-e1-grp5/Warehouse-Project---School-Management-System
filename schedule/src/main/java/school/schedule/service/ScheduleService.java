package school.schedule.service;

import school.schedule.model.dto.ScheduleClassDto;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {

    ScheduleDto addSchedule(AddScheduleDto scheduleDto);

    ScheduleDto updateSchedule(Integer id, ScheduleDto scheduleDto);

    ScheduleDto getScheduleById(Integer id);

    List<ScheduleDto> getAllSchedules();

    void deleteSchedule(Integer id);

    //ScheduleClassDto getScheduleClassById(Integer id);

    //List<ScheduleClassDto> getAllScheduleClasses();
}


