package school.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.schedule.AddScheduleDto;
import school.admin.model.dto.schedule.ScheduleDto;
import school.admin.service.schedule.AdminScheduleService;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class AdminScheduleController {

    @Autowired
    private AdminScheduleService scheduleService;

    @PostMapping("/add")
    public ResponseEntity<ScheduleDto> addSchedule(@RequestBody AddScheduleDto addScheduleDto) {
        ScheduleDto scheduleDto = scheduleService.addSchedule(addScheduleDto);
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Long id) {
        ScheduleDto scheduleDto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/byClass/{classId}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByClass(@PathVariable Long classId) {
        List<ScheduleDto> schedules = scheduleService.getSchedulesByClass(classId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByTeacher(@PathVariable String teacherId) {
        List<ScheduleDto> schedules = scheduleService.getSchedulesByTeacher(teacherId);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, scheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
