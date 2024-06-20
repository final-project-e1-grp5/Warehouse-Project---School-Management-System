package school.admin.model.dto.attendanceDto.studentAttendanceDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class StudentAttendanceDto {
    private Long attendanceId;
    private LocalDate date;
    private boolean present;
    private String classId;
    private String studentId;
    private LocalTime earlyLeave;
    private LocalTime lateArrive;
    private String note;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }



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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }



    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
