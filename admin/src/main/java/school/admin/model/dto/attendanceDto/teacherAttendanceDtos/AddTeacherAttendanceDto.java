package school.admin.model.dto.attendanceDto.teacherAttendanceDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTeacherAttendanceDto {
    private LocalDate date;
    private boolean present;
    private String teacherId;
    private LocalTime earlyLeave;
    private LocalTime lateArrive;
    private String note;



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public LocalTime getEarlyLeave() {
        return earlyLeave;
    }

    public void setEarlyLeave(LocalTime earlyLeave) {
        this.earlyLeave = earlyLeave;
    }

    public LocalTime getLateArrive() {
        return lateArrive;
    }

    public void setLateArrive(LocalTime lateArrive) {
        this.lateArrive = lateArrive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
