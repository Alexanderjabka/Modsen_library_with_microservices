package org.example.libraryservice.exceptions;

public class AlreadyTakenRecord extends LibraryServiceException{
    public AlreadyTakenRecord(String message) {
        super(message);
    }
}
