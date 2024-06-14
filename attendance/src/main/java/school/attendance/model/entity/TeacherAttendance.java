package school.attendance.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name= "teacher_attendance")
public class TeacherAttendance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long attendanceId;

    @NotNull
    @Column(name = "teacher_id")
    private String teacherId;

    @NotNull
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @NotNull
    @Column(name = "is_present", nullable = false)
    private Boolean isPresent = false;

    @Column(name = "early_leave")
    private LocalTime earlyLeave;

    @Column(name = "late_arrive")
    private LocalTime lateArrive;

    @Size(max = 150)
    @Column(name = "note")
    private String note;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public @NotNull String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(@NotNull String teacherId) {
        this.teacherId = teacherId;
    }

    public @NotNull LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(@NotNull LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public @NotNull Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(@NotNull Boolean present) {
        isPresent = present;
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

    public @Size(max = 150) String getNote() {
        return note;
    }

    public void setNote(@Size(max = 150) String note) {
        this.note = note;
    }
}
