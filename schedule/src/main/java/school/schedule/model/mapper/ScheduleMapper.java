package school.schedule.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;
import school.schedule.model.entity.Schedule;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleDto toDto(Schedule schedule);

    Schedule toEntity(ScheduleDto dto);
    Schedule addScheduleDtoToEntity(AddScheduleDto addScheduleDto);

    List<ScheduleDto> toDtoList(List<Schedule> schedules);

    void updateEntityFromAddStudentDto(@MappingTarget Schedule schedule, AddScheduleDto updateScheduleDto);
}
