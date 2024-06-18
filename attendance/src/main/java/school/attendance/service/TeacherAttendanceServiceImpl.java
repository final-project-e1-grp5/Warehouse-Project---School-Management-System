package school.attendance.service;

import school.attendance.exceptions.ResourceNotFoundException;
import school.attendance.model.dto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.attendance.model.dto.teacherAttendanceDtos.TeacherAttendanceDto;
import school.attendance.model.entity.TeacherAttendance;
import school.attendance.model.mapper.TeacherAttendanceMapper;
import school.attendance.repository.TeacherAttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TeacherAttendanceServiceImpl implements TeacherAttendanceService {

    @Autowired
    private TeacherAttendanceRepo teacherAttendanceRepo;

    @Autowired
    private TeacherAttendanceMapper mapper ;

    @Override
    public List<TeacherAttendanceDto> getAllAttendances() {
       List<TeacherAttendance> attendances = teacherAttendanceRepo.findAll();

        return mapper.entitiestoDtos(attendances);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherAttendanceDto getAttendanceById(Long id) {
        return teacherAttendanceRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id: " + id));
    }

    @Override
    @Transactional
    public TeacherAttendanceDto createAttendance(AddTeacherAttendanceDto attendanceDto) {
        TeacherAttendance attendance = mapper.toEntity(attendanceDto);
        return mapper.toDto(teacherAttendanceRepo.save(attendance));
    }

    @Override
    @Transactional
    public TeacherAttendanceDto updateAttendance(Long id, TeacherAttendanceDto attendanceDto) {
        TeacherAttendance attendance = teacherAttendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id: " + id));

        mapper.updateEntityFromDto(attendance,attendanceDto);


        return mapper.toDto(attendance);
    }

    @Override
    @Transactional
    public void deleteAttendance(Long id) {
        TeacherAttendance attendance = teacherAttendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id:" + id));
        teacherAttendanceRepo.delete(attendance);
    }
}
