package school.attendance.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import school.attendance.model.dto.teacherAttendanceDtos.AddTeacherAttendanceDto;
import school.attendance.model.dto.teacherAttendanceDtos.TeacherAttendanceDto;
import school.attendance.model.entity.TeacherAttendance;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherAttendanceMapper {
    TeacherAttendanceDto toDto(TeacherAttendance teacherAttendance);
    void updateEntityFromDto(@MappingTarget TeacherAttendance attendance, TeacherAttendanceDto attendanceDto);

    List<TeacherAttendanceDto> entitiestoDtos(List<TeacherAttendance> attendances);

    TeacherAttendance toEntity(AddTeacherAttendanceDto attendanceDto);
}
