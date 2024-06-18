package school.schedule.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.schedule.exceptions.ScheduleNotFoundException;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;
import school.schedule.model.entity.Schedule;
import school.schedule.model.mapper.ScheduleMapper;
import school.schedule.repository.ScheduleRepository;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleDto> findSchedulesByClassId(Long classId) {
        List<Schedule> schedules = scheduleRepository.findByClassId(classId);
        return scheduleMapper.toDtoList(schedules);
    }

    @Override
    public List<ScheduleDto> findSchedulesByTeacherId(String teacherId) {
        List<Schedule> schedules = scheduleRepository.findByTeacherId(teacherId);
        return scheduleMapper.toDtoList(schedules);
    }

    @Transactional
    @Override
    public ScheduleDto addSchedule(AddScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.addScheduleDtoToEntity(scheduleDto);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleDto updateSchedule(Long scheduleId, AddScheduleDto updateScheduleDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with ID: " + scheduleId));
        scheduleMapper.updateEntityFromAddStudentDto(schedule, updateScheduleDto);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            throw new ScheduleNotFoundException("Schedule not found with ID: " + scheduleId);
        }
    }




}

