package ru.nova.userapi.exception;

public class PhoneContactNotFoundException extends RuntimeException{
    public PhoneContactNotFoundException(String message) {
        super(message);
    }
}
