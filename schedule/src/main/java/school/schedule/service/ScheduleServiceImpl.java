package school.schedule.service;

import org.springframework.stereotype.Service;
import school.schedule.exceptions.ScheduleNotFoundException;
import school.schedule.model.dto.ClassDto;
import school.schedule.model.dto.ScheduleClassDto;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;
import school.schedule.model.entity.Schedule;
import school.schedule.model.mapper.ScheduleMapper;
import school.schedule.proxy.ClassProxy;
import school.schedule.repository.ScheduleRepo;

import java.util.ArrayList;
import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final ScheduleMapper scheduleMapper;
    //private final ClassProxy classProxy;


    public ScheduleServiceImpl(ScheduleRepo scheduleRepo, ScheduleMapper scheduleMapper, ClassProxy classProxy) {
        this.scheduleRepo = scheduleRepo;
        this.scheduleMapper = scheduleMapper;
        //this.classProxy = classProxy;
    }


    @Override
    public ScheduleDto addSchedule(AddScheduleDto addScheduleDto) {
        Schedule schedule = scheduleMapper.addDtoToEntity(addScheduleDto);

        System.out.println(schedule);

        Schedule savedSchedule = scheduleRepo.save(schedule);

        return scheduleMapper.entityToDto(savedSchedule);
    }

    @Override
    public ScheduleDto updateSchedule(Integer id, ScheduleDto scheduleDto) {

        Schedule schedule = scheduleRepo.findById(id).orElse(null);

        if(schedule != null) {
            scheduleDto.setId(id);
            Schedule savedSchedule = scheduleRepo.save(scheduleMapper.dtoToEntity(scheduleDto));
            return scheduleMapper.entityToDto(savedSchedule);

        } else {
            throw new ScheduleNotFoundException("Schedule with id: " + id + " was not found");
        }
    }

    @Override
    public ScheduleDto getScheduleById(Integer id) {

        Schedule schedule = scheduleRepo.findById(id).orElse(null);

        if(schedule != null) {
            return scheduleMapper.entityToDto(schedule);
        } else {
            throw new ScheduleNotFoundException("Schedule with id: " + id + " was not found");
        }
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {

        List<Schedule> schedules = scheduleRepo.findAll();
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDtos.add(scheduleMapper.entityToDto(schedule)));
        return scheduleDtos;
    }


    @Override
    public void deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepo.findById(id).orElse(null);

        if(schedule != null) {
            scheduleRepo.delete(schedule);
        } else {
            throw new ScheduleNotFoundException("Schedule with id: " + id + " was not found");
        }
    }

/*
    @Override
    public ScheduleClassDto getScheduleClassById(Integer id) {

        Schedule schedule = scheduleRepo.findById(id).orElse(null);

        ScheduleDto scheduleDto;

        if(schedule != null) {
            scheduleDto = scheduleMapper.entityToDto(schedule);
        } else {
            throw new ScheduleNotFoundException("Schedule with id: " + id + " was not found");
        }

        ClassDto classDto = classProxy.getClass(id);

        return scheduleMapper.toScheduleClassDto(scheduleDto, classDto);

    }
*/




/*    @Override
    public List<ScheduleClassDto> getAllScheduleClasses() {
        List<ScheduleDto> scheduleDtos = scheduleRepo.findAll().stream().map(scheduleMapper::entityToDto).toList();

        List<ClassDto> classDtos = classProxy.getClasses();

        return null;
        //return scheduleMapper.toScheduleClassDtoList(scheduleDtos, classDtos);

    }*/


}
