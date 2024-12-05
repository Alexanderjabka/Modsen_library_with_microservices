package org.example.libraryservice.exceptions;

public class RecordNotFound extends LibraryServiceException{
    public RecordNotFound(String message){
        super(message);
    }
}
