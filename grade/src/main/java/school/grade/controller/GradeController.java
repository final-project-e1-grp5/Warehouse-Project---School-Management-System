package school.grade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.grade.model.dto.GradeDto;
import school.grade.service.GradeService;


import java.util.List;

@Validated
@RestController
@RequestMapping("/grade")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{classId}/{assessment}")
    public ResponseEntity<List<GradeDto>> getGradesByClassAndAssessment(
            @PathVariable Long classId,
            @PathVariable String assessment
    ) {
        List<GradeDto> grades = gradeService.getGradeByClassIdAndAssessment(classId, assessment);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<GradeDto>> getGradesByStudentId(@PathVariable String studentId) {
        List<GradeDto> grades = gradeService.getGradeByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @PostMapping()
    public ResponseEntity<GradeDto> addGrade(@RequestBody GradeDto gradeDto) {
        GradeDto createdGrade = gradeService.createGrade(gradeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDto> updateGrade(
            @PathVariable Long id,
            @RequestBody GradeDto gradeDto
    ) {
        GradeDto updatedGrade = gradeService.updateGrade(id, gradeDto);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
