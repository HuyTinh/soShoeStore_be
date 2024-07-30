package com.personal.soshoestore_be.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiException> handleApiException(RuntimeException ex) {
        String message = ex.getMessage();
        HttpStatus status = null;
        if(ex instanceof  DataNotFoundException){
            status = HttpStatus.NOT_FOUND;    
        } else if (ex instanceof PermissionDennyException){
            status = HttpStatus.BAD_REQUEST;    
        } else if(ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.BAD_REQUEST;
        }
        return exceptionResponse(message, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
//        ex.getBindingResult().getFieldError().getDefaultMessage()
//        Map<String, List<String>> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return exceptionResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
    
    private ResponseEntity<ApiException> exceptionResponse(Object ex, HttpStatus status) {
        ApiException apiException = ApiException.builder()
                .message(ex)
                .date(new Date())
                .build();
        return ResponseEntity.status(status).body(apiException);
    }
}
