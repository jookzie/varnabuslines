package com.varnabuslines.configuration;

import com.varnabuslines.domain.exceptions.ForbiddenAccessException;
import com.varnabuslines.domain.exceptions.InvalidAccessTokenException;
import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler
{
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleArgumentException(final ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException()
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("One or more fields already exist.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenAccessException.class)
    public void handleForbiddenAccessException()
    {
        // Nothing to do
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<String> handleInvalidAccessTokenException()
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token.");
    }
}
