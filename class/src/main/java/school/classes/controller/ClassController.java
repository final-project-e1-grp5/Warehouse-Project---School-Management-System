package school.classes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.classes.model.dto.AddClassDto;
import school.classes.model.dto.ClassDto;
import school.classes.service.ClassService;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;
    @PostMapping("/add")
    public ResponseEntity<ClassDto> addClass(@RequestBody AddClassDto addClassDto) {
        ClassDto classDto = classService.addClass(addClassDto);
        return ResponseEntity.ok(classDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable("id") Long id) {
        ClassDto classDto = classService.getClass(id);
        return ResponseEntity.ok(classDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable("id") Long id, @RequestBody ClassDto classDto) {
        ClassDto updatedClassDto = classService.updateClass(id, classDto);
        return ResponseEntity.ok(updatedClassDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable("id") Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}