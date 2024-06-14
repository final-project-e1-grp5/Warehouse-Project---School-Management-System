package school.schedule.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.schedule.model.dto.AddScheduleDto;
//import school.schedule.model.dto.ScheduleClassDto;
import school.schedule.model.dto.ScheduleDto;
//import school.schedule.model.entity.Schedule;
import school.schedule.service.ScheduleService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{id}")
    public ScheduleDto getSchedule(@PathVariable @Min(1) Integer id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("")
    public List<ScheduleDto> getAllScheduleDtos() {

        return scheduleService.getAllSchedules();
    }


    @PostMapping("")
    public ScheduleDto addSchedule(@Valid @RequestBody AddScheduleDto addScheduleDto) {
        return scheduleService.addSchedule(addScheduleDto);
    }

    @PutMapping("/{id}")
    public ScheduleDto updateSchedule(@PathVariable @Min(1) Integer id, @Valid @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.updateSchedule(id, scheduleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable @Min(1) Integer id) {
        scheduleService.deleteSchedule(id);
    }



    /*@GetMapping("/{id}")
    public ScheduleClassDto getSchedule(@PathVariable Integer id) {
        return scheduleService.getScheduleClassById(id);
    }*/


/*    @GetMapping("")
    public List<ScheduleClassDto> getAllSchedules() {
        return scheduleService.getAllScheduleClasses();
    }*/
}
