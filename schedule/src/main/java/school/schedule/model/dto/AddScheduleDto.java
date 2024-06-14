package school.schedule.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public class AddScheduleDto {

    @NotNull(message = "The day of the week must be provided")
    @NotBlank(message = "Invalid dayOfWeek: Day of week should not be blank")
    @Size(min = 6, message = "Invalid dayOfWeek: It should have at least 6 characters")
    private String dayOfWeek;

    @NotNull(message = "A start time was not provided, please provide one")
    @NotBlank(message = "Start time should not be blank")
    private LocalTime startTime;

    @NotNull(message = "An end time was not provided, please provide one")
    @NotBlank(message = "End time should not be blank")
    private LocalTime endTime;

    @Min(value = 1)
    private Integer classId;

    public @NotNull(message = "The day of the week must be provided") @NotBlank(message = "Invalid dayOfWeek: Day of week should not be blank") @Size(min = 6, message = "Invalid dayOfWeek: It should have at least 6 characters") String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(@NotNull(message = "The day of the week must be provided") @NotBlank(message = "Invalid dayOfWeek: Day of week should not be blank") @Size(min = 6, message = "Invalid dayOfWeek: It should have at least 6 characters") String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public @NotNull(message = "A start time was not provided, please provide one") @NotBlank(message = "Start time should not be blank") LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull(message = "A start time was not provided, please provide one") @NotBlank(message = "Start time should not be blank") LocalTime startTime) {
        this.startTime = startTime;
    }

    public @NotNull(message = "An end time was not provided, please provide one") @NotBlank(message = "End time should not be blank") LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull(message = "An end time was not provided, please provide one") @NotBlank(message = "End time should not be blank") LocalTime endTime) {
        this.endTime = endTime;
    }

    public @Min(value = 1) Integer getClassId() {
        return classId;
    }

    public void setClassId(@Min(value = 1) Integer classId) {
        this.classId = classId;
    }
}
