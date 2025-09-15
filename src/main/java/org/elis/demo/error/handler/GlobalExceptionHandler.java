package org.elis.demo.error.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.elis.demo.DTO.response.ValidationErrorMessageDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;

@Data
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> conflictHandler(ConflictException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", HttpStatus.CONFLICT.name());
        body.put("errorMessage", ex.getMessage());
        body.put("path", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessageDTO> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        // raccogli tutti gli errori di campo: fieldName -> messaggio
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (msg1, msg2) -> msg1  // se un campo ha più errori, tieni il primo
            ));

        // costruisci il DTO
        ValidationErrorMessageDTO dto = new ValidationErrorMessageDTO();
        dto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        dto.setStatusSpring(HttpStatus.BAD_REQUEST.name());
        dto.setTargetUrl(request.getDescription(false)); // es. "uri=/auth/register"
        dto.setFieldErrors(errors);

        return ResponseEntity.badRequest().body(dto);
    }
    
    @ExceptionHandler(NessunRisultatoException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(RuntimeException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", HttpStatus.UNAUTHORIZED.name());
        body.put("errorMessage", ex.getMessage());
        body.put("path", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}