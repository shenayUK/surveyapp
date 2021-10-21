package com.turkcell.pollservice.exception;

public class BadCredentialsException extends AuthException {

    public BadCredentialsException() {
        super("BAD_CREDENTIALS");
    }
}
