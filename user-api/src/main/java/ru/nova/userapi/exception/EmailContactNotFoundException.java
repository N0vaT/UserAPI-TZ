package ru.nova.userapi.exception;

public class EmailContactNotFoundException extends RuntimeException{
    public EmailContactNotFoundException(String message) {
        super(message);
    }
}
