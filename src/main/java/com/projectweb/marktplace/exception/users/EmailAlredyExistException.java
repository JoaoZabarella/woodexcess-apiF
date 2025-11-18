package com.projectweb.marktplace.exception.users;

public class EmailAlredyExistException extends RuntimeException {
    public EmailAlredyExistException(String message) {
        super(message);
    }
}
