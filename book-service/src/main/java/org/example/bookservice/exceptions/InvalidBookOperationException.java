package org.example.bookservice.exceptions;

public class InvalidBookOperationException extends BookServiceException {
    public InvalidBookOperationException(String message) {
        super(message);
    }
}
