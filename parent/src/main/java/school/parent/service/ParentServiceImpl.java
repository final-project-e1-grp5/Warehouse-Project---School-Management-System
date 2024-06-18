package school.parent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.parent.exceptions.ConflictException;
import school.parent.exceptions.ParentNotFoundException;
import school.parent.model.dto.ParentDto;
import school.parent.model.entity.Parent;
import school.parent.model.entity.Child;
import school.parent.model.mapper.ParentMapper;
import school.parent.repository.ChildRepo;
import school.parent.repository.ParentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private ChildRepo childRepo;

    @Override
    @Transactional
    public ParentDto addParent(ParentDto addParentDto) {
        if (parentRepo.existsById(addParentDto.getId())) {
            throw new ConflictException("Parent with id (" + addParentDto.getId() + ") already exists!");
        }

        // Map DTO to Parent entity
        Parent parent = parentMapper.dtoToEntity(addParentDto);

        // Initialize children list if not initialized
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }

        // Set the parent children if not null
        if (addParentDto.getChildrenIds() != null) {
            parent.getChildren().addAll(
                    addParentDto.getChildrenIds().stream()
                            .map(childId -> {
                                Child child = new Child();
                                child.setChildId(childId);
                                child.setParent(parent);
                                return child;
                            })
                            .toList()
            );
        }

        // Save the parent entity
        Parent createdParent = parentRepo.save(parent);

        // Map the created entity to DTO
        ParentDto createdParentDto = parentMapper.entityToDto(createdParent);
        createdParentDto.setChildrenIds(
                createdParent.getChildren().stream()
                        .map(Child::getChildId)
                        .collect(Collectors.toList())
        );
        return createdParentDto;
    }


    @Override
    public ParentDto getParent(String userId) {
        Optional<Parent> parent = parentRepo.findById(userId);
        if (parent.isPresent()) {
            ParentDto parentDto = parentMapper.entityToDto(parent.get());
            parentDto.setChildrenIds(
                    parent.get().getChildren().stream()
                            .map(Child::getChildId)
                            .collect(Collectors.toList())
            );
            return parentDto;
        }
        throw new ParentNotFoundException("Parent with id (" + userId + ") not found!");
    }

    @Override
    public List<ParentDto> getAllParents() {
        List<Parent> parents = parentRepo.findAll();
        return parents.stream()
                .map(parentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParentDto updateParent(String id, ParentDto updateParentDto) {
        Parent existingParent = parentRepo.findById(id)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id (" + id + ") not found!"));

        // Map the updated fields from DTO to existing entity
        parentMapper.updateParentFromDto(updateParentDto, existingParent);

        // Update the parent's children
        List<Child> updatedChildren = updateParentDto.getChildrenIds().stream()
                .map(childId -> {
                    Child child = new Child();
                    child.setChildId(childId);
                    child.setParent(existingParent);
                    return child;
                })
                .toList();

        existingParent.getChildren().clear();
        existingParent.getChildren().addAll(updatedChildren);

        // Save the updated parent entity
        Parent updatedParent = parentRepo.save(existingParent);

        // Map the updated entity to DTO
        return parentMapper.entityToDto(updatedParent);
    }

    @Override
    @Transactional
    public void deleteParent(String userId) {
        Parent parent = parentRepo.findById(userId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id (" + userId + ") not found!"));

        // Remove parent children
        childRepo.deleteByParent(parent);

        // Delete the parent entity
        parentRepo.delete(parent);
    }
}
