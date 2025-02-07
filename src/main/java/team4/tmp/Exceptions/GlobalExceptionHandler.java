package team4.tmp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessages = new StringBuilder();
        for (FieldError error : result.getFieldErrors()) {
            errorMessages.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
    }
}