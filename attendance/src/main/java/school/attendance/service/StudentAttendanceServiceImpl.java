package school.attendance.service;

import school.attendance.exceptions.ResourceNotFoundException;
import school.attendance.model.dto.studentAttendanceDtos.AddStudentAttendanceDto;
import school.attendance.model.dto.studentAttendanceDtos.StudentAttendanceDto;
import school.attendance.model.entity.StudentAttendance;
import school.attendance.model.mapper.StudentAttendanceMapper;
import school.attendance.repository.StudentAttendanceRepo;
import school.attendance.service.StudentAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

    @Autowired
    private StudentAttendanceRepo studentAttendanceRepo;

    @Autowired
    private  StudentAttendanceMapper mapper;



    @Override
    public List<StudentAttendanceDto> getAllAttendances() {
        return studentAttendanceRepo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentAttendanceDto getAttendanceById(Long id) {
        return studentAttendanceRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id : " + id));
    }

    @Override
    @Transactional
    public StudentAttendanceDto createAttendance(AddStudentAttendanceDto attendanceDto) {
        StudentAttendance attendance = mapper.toEntity(attendanceDto);
        return mapper.toDto(studentAttendanceRepo.save(attendance));
    }

    @Override
    @Transactional
    public StudentAttendanceDto updateAttendance(Long id, StudentAttendanceDto attendanceDto) {
        StudentAttendance attendance = studentAttendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id : " + id));

        mapper.updateEntityFromDto(attendance,attendanceDto);

        return mapper.toDto(studentAttendanceRepo.save(attendance));
    }

    @Override
    @Transactional
    public void deleteAttendance(Long id) {
        StudentAttendance attendance = studentAttendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + id));
        studentAttendanceRepo.delete(attendance);
    }
}
