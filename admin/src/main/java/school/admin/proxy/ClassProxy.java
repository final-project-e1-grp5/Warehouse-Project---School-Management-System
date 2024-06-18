package school.admin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import school.admin.model.dto.classes.AddClassDto;
import school.admin.model.dto.classes.ClassDto;

@FeignClient(name = "CLASS-SERVICE",path = "/class")
public interface ClassProxy {

    @PostMapping("/classes/add")
    ClassDto addClass(@RequestBody AddClassDto addClassDto);

    @GetMapping("/classes/{id}")
    ClassDto getClassById(@PathVariable("id") Long id);

    @PutMapping("/classes/{id}")
    ClassDto updateClass(@PathVariable("id") Long id, @RequestBody ClassDto classDto);

    @DeleteMapping("/classes/{id}")
    void deleteClass(@PathVariable("id") Long id);
}

