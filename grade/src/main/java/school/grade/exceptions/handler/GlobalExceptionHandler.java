package school.grade.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.grade.exceptions.GradeNotFoundException;
import school.grade.model.dto.responseDto.ResponseDto;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GradeNotFoundException.class)
    public ResponseEntity<ResponseDto> handleGradeNotFoundException(GradeNotFoundException ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Grade Not Found Exception",
                ex.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),

                System.currentTimeMillis(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}