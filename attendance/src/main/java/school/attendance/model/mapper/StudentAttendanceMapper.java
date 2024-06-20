package school.attendance.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.attendance.model.dto.studentAttendanceDtos.AddStudentAttendanceDto;
import school.attendance.model.dto.studentAttendanceDtos.StudentAttendanceDto;
import school.attendance.model.entity.StudentAttendance;

@Mapper(componentModel = "spring")
public interface StudentAttendanceMapper {
    StudentAttendanceDto toDto(StudentAttendance studentAttendance);

    StudentAttendance toEntity(AddStudentAttendanceDto attendanceDto);

    void updateEntityFromDto(@MappingTarget StudentAttendance attendance, StudentAttendanceDto attendanceDto);
}
