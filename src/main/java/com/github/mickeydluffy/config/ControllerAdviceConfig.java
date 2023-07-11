package com.github.mickeydluffy.config;

import com.github.mickeydluffy.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**a
 * @see <a href="https://www.toptal.com/java/spring-boot-rest-api-error-handling">...</a>
 * @see <a href="https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">...</a>
 */
@RestControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleEntityValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {

        Map<String, String> errorBody = methodArgumentNotValidException.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Field validations failed")
                .details(errorBody)
                .status(methodArgumentNotValidException.getStatusCode().value())
                .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundExceptions(ResponseStatusException responseStatusException) {
        HttpStatusCode statusCode = responseStatusException.getStatusCode();
        return ResponseEntity.status(statusCode)
            .body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(responseStatusException.getReason())
                .status(responseStatusException.getBody().getStatus())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> constraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
    }

}
