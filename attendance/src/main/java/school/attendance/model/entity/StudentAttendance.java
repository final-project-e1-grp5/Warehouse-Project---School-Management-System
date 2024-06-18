package school.attendance.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "student_attendance")
public class StudentAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long attendanceId;

    @NotNull
    @Column(name = "student_id")
    private String studentId;

    @NotNull
    @Column(name = "class_id")
    private Long classId;

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

    public @NotNull String getStudentId() {
        return studentId;
    }

    public void setStudentId( String studentId) {
        this.studentId = studentId;
    }

    public @NotNull Long getClassId() {
        return classId;
    }

    public void setClassId( Long classId) {
        this.classId = classId;
    }


    public  LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate( LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public  Boolean getPresent() {
        return isPresent;
    }

    public void setPresent( Boolean present) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}