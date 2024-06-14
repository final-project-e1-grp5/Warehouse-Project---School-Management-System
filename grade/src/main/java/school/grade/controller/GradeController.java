package school.grade.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.grade.model.dto.AddGradeDto;
import school.grade.model.dto.GradeDto;
import school.grade.service.GradeService;

import java.util.List;


@RestController
@Validated
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{id}")
    public GradeDto getGrade(@PathVariable("id") @Min(1) Integer id) {
        return gradeService.readGrade(id);
    }

    @GetMapping("")
    public List<GradeDto> getAllGrades() {
        return gradeService.readAllGrades();
    }

    @PostMapping("")
    public GradeDto addGrade(@Valid @RequestBody AddGradeDto addGradeDto) {
        return gradeService.createGrade(addGradeDto);
    }

    @PutMapping("/{id}")
    public GradeDto updateGrade(@PathVariable("id") @Min(1) Integer id, @Valid @RequestBody AddGradeDto addGradeDto) {
        return gradeService.updateGrade(id, addGradeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGrade(@PathVariable("id") @Min(1) Integer id) {
        gradeService.deleteGrade(id);
    }

}
