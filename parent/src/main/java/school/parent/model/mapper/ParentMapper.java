package school.parent.model.mapper;

import org.mapstruct.*;
import school.parent.model.dto.ParentDto;
import school.parent.model.entity.Parent;


@Mapper(componentModel = "spring")
public interface ParentMapper {
    ParentDto entityToDto(Parent parent);
    Parent dtoToEntity(ParentDto parentDto);
    void updateParentFromDto(ParentDto parentDto, @MappingTarget Parent parent);

}
