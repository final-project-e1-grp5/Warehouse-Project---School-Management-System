package school.attendance.model.dto;


public record ResponseDto(
            int status,
            String error,
            String message,
            long timestamp,
            String path
    ) {}

