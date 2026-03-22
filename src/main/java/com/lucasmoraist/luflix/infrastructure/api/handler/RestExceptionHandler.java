package com.lucasmoraist.luflix.infrastructure.api.handler;

import com.lucasmoraist.luflix.domain.exceptions.JwtException;
import com.lucasmoraist.luflix.domain.exceptions.UniqueException;
import com.lucasmoraist.luflix.infrastructure.api.handler.dto.DataValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.h2.security.auth.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Void> handleEntityNotFound(EntityNotFoundException ex) {
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Void> handleIllegalArgument(IllegalArgumentException ex) {
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UniqueException.class)
    protected ResponseEntity<String> handleUniqueException(UniqueException ex) {
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<DataValidationException>> handleDataRequestException(MethodArgumentNotValidException ex) {
        List<DataValidationException> errors = ex.getFieldErrors()
                .stream()
                .map(DataValidationException::new)
                .toList();
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({AuthenticationException.class})
    protected ResponseEntity<Void> handleAuthenticationException(AuthenticationException ex) {
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler({JwtException.class})
    protected ResponseEntity<Void> handleJwtException(JwtException ex) {
        logException("warn", ex.getMessage(), ex);
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Void> handleGenericException(Exception ex) {
        logException("error", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().build();
    }

    private void logException(String logLevel, String message, Throwable ex) {
        String logMessage = "Message: [{}]";
        switch (logLevel.toLowerCase()) {
            case "warn" -> log.warn(logMessage, message, ex);
            case "error" -> log.error(logMessage, message, ex);
            default -> log.debug(logMessage, message, ex);
        }
    }

}
