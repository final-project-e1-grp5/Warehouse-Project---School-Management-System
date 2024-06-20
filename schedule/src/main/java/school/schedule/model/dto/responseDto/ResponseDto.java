package school.schedule.model.dto.responseDto;


public record ResponseDto(
            int status,
            String error,
            String message,
            long timestamp,
            String path
    ) {}

