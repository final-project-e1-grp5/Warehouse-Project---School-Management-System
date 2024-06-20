package school.student.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.student.exceptions.ConflictException;
import school.student.exceptions.StudentNotFoundException;
import school.student.model.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ResponseDto> handleStudentNotFoundException(StudentNotFoundException ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Student Not Found Exception",
                ex.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDto> handleConflictException(ConflictException ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.CONFLICT.value(),
                "Conflict Exception",
                ex.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),

                System.currentTimeMillis(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}