package school.admin.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.gradeDto.GradeDto;
import school.admin.service.grade.AdminGradeService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/grade")
public class AdminGradeController {

    private final AdminGradeService gradeService;


    public AdminGradeController(AdminGradeService gradeService) {
        this.gradeService = gradeService;
    }


    @GetMapping("{classId}/{assessment}")
    public ResponseEntity<List<GradeDto>> getGradeByClassIdAndAssessment(@PathVariable Long classId, @PathVariable String assessment) {
        List<GradeDto> grades = gradeService.getGradeByClassIdAndAssessment(classId,assessment);
        return ResponseEntity.ok(grades);
    }
    @GetMapping("/{studentId}")
   public ResponseEntity<List<GradeDto>> findByStudentId(@PathVariable String studentId){
        return ResponseEntity.ok(gradeService.getGradeByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<GradeDto> createGrade( @RequestBody GradeDto gradeDto) {
        GradeDto createdGrade = gradeService.createGrade(gradeDto);
        return new ResponseEntity<>(createdGrade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDto> updateGrade(@PathVariable Long id, @RequestBody GradeDto gradeDto) {
        GradeDto updatedGrade = gradeService.updateGrade(id, gradeDto);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}