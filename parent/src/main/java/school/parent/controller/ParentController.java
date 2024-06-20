package school.parent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.parent.model.dto.ParentDto;
import school.parent.service.ParentService;

import java.util.List;

@RestController
@RequestMapping("/parents")
@Validated
public class ParentController {

    @Autowired
    private ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentDto> addParent(@RequestBody ParentDto parentDto) {
        ParentDto createdParent = parentService.addParent(parentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParent);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ParentDto> getParent(@PathVariable String userId) {
        ParentDto parent = parentService.getParent(userId);
        return ResponseEntity.ok(parent);
    }

    @GetMapping
    public ResponseEntity<List<ParentDto>> getAllParents() {
        List<ParentDto> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ParentDto> updateParent(@PathVariable String userId, @RequestBody ParentDto parentDto) {
        ParentDto updatedParent = parentService.updateParent(userId, parentDto);
        return ResponseEntity.ok(updatedParent);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteParent(@PathVariable String userId) {
        parentService.deleteParent(userId);
        return ResponseEntity.noContent().build();
    }
}
