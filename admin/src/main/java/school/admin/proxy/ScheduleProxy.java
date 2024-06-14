package school.admin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.schedule.AddScheduleDto;
import school.admin.model.dto.schedule.ScheduleDto;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "SCHEDULE-SERVICE")
public interface ScheduleProxy {

    @PostMapping("/schedule/add")
    ScheduleDto addSchedule(@RequestBody AddScheduleDto addScheduleDto);

    @GetMapping("/schedule/{id}")
    ScheduleDto getSchedule(@PathVariable Long id);

    @GetMapping("/schedule/byClass/{classId}")
    List<ScheduleDto> getSchedulesByClass(@PathVariable Long classId);

    @GetMapping("/schedule/byTeacher/{teacherId}")
    List<ScheduleDto> getSchedulesByTeacher(@PathVariable String teacherId);

    @PutMapping("/schedule/{id}")
    ScheduleDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto);

    @DeleteMapping("/schedule/{id}")
    void deleteSchedule(@PathVariable Long id);
}
