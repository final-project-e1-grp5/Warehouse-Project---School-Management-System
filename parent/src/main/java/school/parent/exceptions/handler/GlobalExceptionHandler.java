package school.parent.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.parent.exceptions.ParentNotFoundException;
import school.parent.model.dto.responseDto.ResponseDto;
import school.parent.exceptions.ConflictException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParentNotFoundException.class)
    public ResponseEntity<ResponseDto> handleParentNotFoundException(ParentNotFoundException ex, HttpServletRequest request) {
        ResponseDto errorResponse = new ResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Parent Not Found Exception",
                ex.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()
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
                request.getRequestURI()
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
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}