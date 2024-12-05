package org.example.bookservice.exceptions;

public class BookNotFoundException extends BookServiceException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
