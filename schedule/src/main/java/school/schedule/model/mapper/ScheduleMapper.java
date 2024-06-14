package school.schedule.model.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ClassDto;
import school.schedule.model.dto.ScheduleClassDto;
import school.schedule.model.dto.ScheduleDto;
import school.schedule.model.entity.Schedule;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule dtoToEntity(ScheduleDto scheduleDTO);

    ScheduleDto entityToDto(Schedule schedule);

    Schedule addDtoToEntity(AddScheduleDto addScheduleDTO);

    @Mapping(source = "scheduleDto.id", target = "id")
    ScheduleClassDto toScheduleClassDto(ScheduleDto scheduleDto, ClassDto classDto);

    default List<ScheduleClassDto> toScheduleClassDtoList(List<ScheduleDto> scheduleDtos, List<ClassDto> classDtos) {
        List<ScheduleClassDto> scheduleClassDtos = new ArrayList<>();
        for (ScheduleDto scheduleDto : scheduleDtos) {
            for (ClassDto classDto : classDtos) {
                scheduleClassDtos.add(toScheduleClassDto(scheduleDto, classDto));
            }
        }

        return scheduleClassDtos;
    }
}