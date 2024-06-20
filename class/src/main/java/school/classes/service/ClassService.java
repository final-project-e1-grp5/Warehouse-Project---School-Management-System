package school.classes.service;

import school.classes.model.dto.AddClassDto;
import school.classes.model.dto.ClassDto;

public interface ClassService {

    ClassDto addClass(AddClassDto addClassDto);
    ClassDto getClass(Long id);
    ClassDto updateClass(Long id, ClassDto classDto);
    void deleteClass(Long id);
}
