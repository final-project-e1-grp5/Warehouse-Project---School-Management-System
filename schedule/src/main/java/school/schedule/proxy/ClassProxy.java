package school.schedule.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import school.schedule.model.dto.ClassDto;

import java.util.List;

@FeignClient(name = "CLASS-SERVICE")
public interface ClassProxy {

    @GetMapping("/class/{id}")
    ClassDto getClass(@PathVariable Integer id);

    @GetMapping("/class")
    List<ClassDto> getClasses();
}
