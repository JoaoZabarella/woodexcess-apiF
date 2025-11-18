package com.projectweb.marktplace.exception.auth;

public class TokenReuseDetectedException extends RuntimeException {
    public TokenReuseDetectedException(String message) {
        super(message);
    }
}
