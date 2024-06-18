package school.admin.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.gradeDto.GradeDto;

import java.util.List;

@FeignClient(name = "GRADE-SERVICE",path = "/grade/grade")
public interface GradeProxy {

    @GetMapping("/{classId}/{assessment}")
    List<GradeDto> findByClassIdAndAssessment(@PathVariable Long classId, @PathVariable String assessment);

    @GetMapping("/{studentId}")
    List<GradeDto> findByStudentId(@PathVariable String studentId);

    @PostMapping()
    GradeDto addGrade(@RequestBody GradeDto gradeDto);

    @PutMapping("/{id}")
    GradeDto updateGrade(@PathVariable Long id, @RequestBody GradeDto gradeDto);

    @DeleteMapping("/{id}")
     void deleteGrade(@PathVariable Long id);
}
