package school.classes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.classes.model.dto.AddClassDto;
import school.classes.model.dto.ClassDto;
import school.classes.model.entity.ClassEntity;
import school.classes.model.mapper.ClassMapper;
import school.classes.repository.ClassRepo;

import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepo classRepo;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public ClassDto addClass(AddClassDto addClassDto) {
        ClassEntity classEntity = classMapper.addClassDtoToEntity(addClassDto);
        classRepo.save(classEntity);
        return classMapper.entityToClassDto(classEntity);
    }

    @Override
    public ClassDto getClass(Long id) {
        Optional<ClassEntity> classEntity = classRepo.findById(id);
        if (classEntity.isPresent()) {
            return classMapper.entityToClassDto(classEntity.get());
        }
        throw new RuntimeException("Class with id(" + id + ") not found!");
    }

    @Override
    public ClassDto updateClass(Long id, ClassDto classDto) {
        Optional<ClassEntity> classEntityOpt = classRepo.findById(id);
        if (classEntityOpt.isPresent()) {
            ClassEntity classEntity = classEntityOpt.get();
            classMapper.updateEntityFromDto(classDto, classEntity);
            classRepo.save(classEntity);
            return classMapper.entityToClassDto(classEntity);
        }
        throw new RuntimeException("Class with id(" + id + ") not found!");
    }

    @Override
    public void deleteClass(Long id) {
        Optional<ClassEntity> classEntity = classRepo.findById(id);
        if (classEntity.isPresent()) {
            classRepo.delete(classEntity.get());
        } else {
            throw new RuntimeException("Class with id(" + id + ") not found!");
        }
    }
}