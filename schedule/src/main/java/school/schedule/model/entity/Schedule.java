package school.schedule.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;


    @Column(name = "class_id")
    private Long classId;

    @Column(name = "teacher_id")
    private String teacherId;

    @Size(max = 20)
    @Column(name = "day_of_week", length = 20)
    private String dayOfWeek;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }


    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public @Size(max = 20) String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(@Size(max = 20) String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}