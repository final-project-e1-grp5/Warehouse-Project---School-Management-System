package school.schedule.model.dto;

import java.time.LocalTime;

public class ScheduleDto {
    private Integer id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer classId;
    //private Integer teacherId;
    //private Integer lessonNumber;
    //private LocalDate date;


    public ScheduleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}

