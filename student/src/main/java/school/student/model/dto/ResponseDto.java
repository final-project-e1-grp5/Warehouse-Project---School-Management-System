package school.student.model.dto;

import java.util.List;
import java.util.Map;


public record ResponseDto(
            int status,
            String error,
            String message,
            long timestamp,
            String path,
            List<Map<String,String>> payload
    ) {}

