package school.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.schedule.model.dto.AddScheduleDto;
import school.schedule.model.dto.ScheduleDto;
import school.schedule.service.ScheduleService;

import java.util.List;

@RestController
@Validated@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/class/{classId}")
    public ResponseEntity<List<ScheduleDto>> findSchedulesForClass(@PathVariable Long classId) {
        List<ScheduleDto> schedules = scheduleService.findSchedulesByClassId(classId);

        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ScheduleDto>> findSchedulesForTeacher(@PathVariable String teacherId) {
        List<ScheduleDto> schedules = scheduleService.findSchedulesByTeacherId(teacherId);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping()
    public ResponseEntity<ScheduleDto> addSchedule(@RequestBody AddScheduleDto scheduleDto) {
        ScheduleDto createdSchedule = scheduleService.addSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }


    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody AddScheduleDto scheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

}