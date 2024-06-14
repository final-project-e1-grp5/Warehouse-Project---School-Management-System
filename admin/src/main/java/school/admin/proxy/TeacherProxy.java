package school.admin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.teacherDto.TeacherDto;
import school.admin.model.dto.teacherDto.UpdateTeacherDto;

import java.util.List;

@FeignClient(name = "TEACHER-SERVICE")
public interface TeacherProxy {


    @PostMapping("teacher/add")
     TeacherDto addTeacher(@RequestBody TeacherDto teacher);

    @GetMapping("/teacher/{id}")
    TeacherDto getTeacher(@PathVariable String id);

    @GetMapping("/teacher/get-all")
    List<TeacherDto> getAllTeachers();

    @PutMapping("/teacher/{id}")
    TeacherDto updateTeacher(@PathVariable String id,@RequestBody UpdateTeacherDto UpdateTeacherDto);

    @DeleteMapping("/teacher/{id}")
    void deleteTeacher(@PathVariable String id);
}
