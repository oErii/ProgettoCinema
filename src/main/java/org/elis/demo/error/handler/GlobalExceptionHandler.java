package org.elis.demo.error.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.elis.demo.DTO.response.ValidationErrorMessageDTO;
import org.elis.demo.error.exceptions.ConflictException;
import org.elis.demo.error.exceptions.NessunRisultatoException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
        body.put("error", HttpStatus.BAD_REQUEST.name());
        body.put("errorMessage", ex.getMessage());
        body.put("path", request.getDescription(false));
        return ResponseEntity.badRequest().body(body);
    }
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorMessageDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest request) {
		
		ValidationErrorMessageDTO responseBody = new ValidationErrorMessageDTO();
		
		responseBody.setTimestamp(LocalDateTime.now());
		responseBody.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		responseBody.setStatusName(HttpStatus.NOT_ACCEPTABLE.name());
		responseBody.setTargetUrl(request.getDescription(false));
		responseBody.setFieldErrors(
					e.getFieldErrors()
					.stream()
					.collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
				);
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseBody);
		
	}
    
    @ExceptionHandler(NessunRisultatoException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(RuntimeException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", HttpStatus.BAD_REQUEST.name());
        body.put("errorMessage", ex.getMessage());
        body.put("path", request.getDescription(false));
        return  ResponseEntity.badRequest().body(body);
    }
}