package org.example.bookservice.exceptions;

public class ExternalServiceException extends BookServiceException {
    public ExternalServiceException(String message) {
        super(message);
    }
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
