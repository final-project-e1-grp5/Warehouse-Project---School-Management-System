package school.classes.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.classes.model.dto.AddClassDto;
import school.classes.model.dto.ClassDto;
import school.classes.model.entity.ClassEntity;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassEntity addClassDtoToEntity(AddClassDto addClassDto);

    ClassDto entityToClassDto(ClassEntity classEntity);

    ClassEntity classDtoToEntity(ClassDto classDto);

    void updateEntityFromDto(ClassDto classDto, @MappingTarget ClassEntity classEntity);
}
