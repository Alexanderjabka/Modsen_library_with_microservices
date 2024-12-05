package org.example.libraryservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaLibraryExceptionHandler {
    @ExceptionHandler(RecordNotFound.class)
    public ResponseEntity<String> handleBookNotFoundException(RecordNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(AlreadyTakenRecord.class)
    public ResponseEntity<String> handleBookNotFoundException(AlreadyTakenRecord ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
