package school.parent.service;

import school.parent.model.dto.ParentDto;

import java.util.List;

public interface ParentService {
     ParentDto addParent(ParentDto addParentDto);
     ParentDto getParent(String userId);
     List<ParentDto> getAllParents();
     ParentDto updateParent(String id, ParentDto updateParentDto);
     void deleteParent(String userId);
}
