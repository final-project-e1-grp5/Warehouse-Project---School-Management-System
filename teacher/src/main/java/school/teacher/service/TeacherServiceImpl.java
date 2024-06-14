package school.teacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.teacher.exceptions.ConflictException;
import school.teacher.exceptions.TeacherNotFoundException;
import school.teacher.model.dto.TeacherDto;
import school.teacher.model.dto.UpdateTeacherDto;
import school.teacher.model.entity.Teacher;
import school.teacher.model.entity.TeacherClass;
import school.teacher.model.mapper.TeacherMapper;
import school.teacher.repository.TeacherClassRepo;
import school.teacher.repository.TeacherRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherClassRepo teacherClassRepo;

    /**
     * Create a new teacher and associate them with classes.
     *
     * @param addTeacherDto the DTO containing teacher information and class IDs
     * @return the created TeacherDto
     *
     */
    @Override
    @Transactional
    public TeacherDto addTeacher(TeacherDto addTeacherDto) {
        try {
            if (teacherRepo.existsById(addTeacherDto.getUserId())) {
                throw new ConflictException("Teacher with id (" + addTeacherDto.getUserId() + ") already exists!");
            }
            // Map DTO to Teacher entity
            Teacher teacher = teacherMapper.TeacherDtoToTeacher(addTeacherDto);

            // Set the teacher classes if not null
            if (addTeacherDto.getClassIds() != null) {
                teacher.setTeacherClasses(
                        addTeacherDto.getClassIds().stream()
                                .map(classId -> {
                                    TeacherClass teacherClass = new TeacherClass();
                                    teacherClass.setClassId(classId);
                                    teacherClass.setTeacher(teacher);
                                    return teacherClass;
                                })
                                .collect(Collectors.toList())
                );
            }

            // Save the teacher entity
            Teacher createdTeacher = teacherRepo.save(teacher);

            // Map the created entity to DTO
            TeacherDto createdTeacherDto = teacherMapper.entityToTeacherDto(createdTeacher);
            createdTeacherDto.setClassIds(
                    createdTeacher.getTeacherClasses().stream()
                            .map(TeacherClass::getClassId)
                            .collect(Collectors.toList())
            );
            return createdTeacherDto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create teacher");
        }
    }

    /**
     *
     * @param userId the user ID
     * @return the TeacherDto
     */
    @Override
    public TeacherDto getTeacher(String userId) {

            // Retrieve the teacher by ID
            Optional<Teacher> teacher = teacherRepo.findById(userId);

            if (teacher.isPresent()) {
                // Map the entity to DTO
                TeacherDto teacherDto = teacherMapper.entityToTeacherDto(teacher.get());
                teacherDto.setClassIds(teacher.get().getTeacherClasses().stream()
                        .map(TeacherClass::getClassId)
                        .collect(Collectors.toList())
                );

                return teacherDto;
            }
            throw new TeacherNotFoundException("Teacher with id (" + userId + ") not found!");

    }

    /**
     * Retrieve all teachers.
     *
     * @return a list of TeacherDto
     */
    @Override
    public List<TeacherDto> getAllTeachers() {
        try {
            // Retrieve all teacher entities
            List<Teacher> teachers = teacherRepo.findAll();

            // Map the entities to DTOs
            return teachers.stream()
                    .map(teacherMapper::entityToTeacherDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve teachers", e);
        }
    }

    /**
     * Update an existing teacher's details.
     *
     * @param updateTeacherDto the DTO containing updated teacher information and class IDs
     * @return the updated TeacherDto
     */
    @Override
    @Transactional
    public TeacherDto updateTeacher(String id, UpdateTeacherDto updateTeacherDto) {

            // Retrieve the existing teacher entity
            Teacher existingTeacher = teacherRepo.findById(id)
                    .orElseThrow(() -> new TeacherNotFoundException("Teacher with id (" + id + ") not found!"));

            // Map the updated fields from DTO to existing entity
            teacherMapper.updateTeacherFromDto(updateTeacherDto, existingTeacher);

            // Update the teacher's classes
            List<TeacherClass> updatedClasses = updateTeacherDto.getClassIds().stream()
                    .map(classId -> {
                        TeacherClass teacherClass = new TeacherClass();
                        teacherClass.setClassId(classId);
                        teacherClass.setTeacher(existingTeacher);
                        return teacherClass;
                    })
                    .toList();

            // Remove old classes and set new ones
            existingTeacher.getTeacherClasses().clear();
            existingTeacher.getTeacherClasses().addAll(updatedClasses);

            // Save the updated teacher entity
            Teacher updatedTeacher = teacherRepo.save(existingTeacher);

            // Map the updated entity to DTO
            return teacherMapper.entityToTeacherDto(updatedTeacher);

    }

    /**
     * Delete a teacher by user ID.
     *
     * @param userId the user ID of the teacher to delete
     */
    @Override
    @Transactional
    public void deleteTeacher(String userId) {

            // Retrieve the teacher by ID
            Teacher teacher = teacherRepo.findById(userId)
                    .orElseThrow(() -> new TeacherNotFoundException("Teacher with id (" + userId + ") not found!"));

            // Remove  Teacher classes
            teacherClassRepo.deleteByTeacher(teacher);


            // Delete the teacher entity
            teacherRepo.delete(teacher);

    }
}
