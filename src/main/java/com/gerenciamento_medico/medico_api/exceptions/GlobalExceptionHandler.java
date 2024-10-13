package com.gerenciamento_medico.medico_api.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                LocalDateTime.now(),
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Adicione mais métodos @ExceptionHandler para outras exceções específicas conforme necessário
}

@Setter
@Getter
class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Constructor, getters, and setters
    // ...
}

@Getter
@Setter
class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
        super(status, message, timestamp);
        this.errors = errors;
    }

    // Constructor, getters, and setters
    // ...
}