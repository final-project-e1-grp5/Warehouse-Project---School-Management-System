package school.grade.model.mapper;


import org.mapstruct.Mapper;
import school.grade.model.dto.AddGradeDto;
import school.grade.model.dto.GradeDto;
import school.grade.model.entity.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    Grade addDtoToEntity(AddGradeDto dto);

    GradeDto entityToDto(Grade grade);
}
