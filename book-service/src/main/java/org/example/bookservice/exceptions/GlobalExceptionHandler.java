package org.example.bookservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<String> handleExternalServiceException(ExternalServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidBookOperationException.class)
    public ResponseEntity<String> handleInvalidBookOperationException(InvalidBookOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<String> handleBookServiceException(BookServiceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
