package school.grade.model.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import school.grade.model.dto.GradeDto;
import school.grade.model.entity.Grade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    GradeDto entityToDto(Grade grade);

    Grade dtoToEntity(GradeDto gradeDto);

    List<GradeDto> entityToDto(List<Grade> grades);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Grade existingGrade, GradeDto gradeDto);
}
