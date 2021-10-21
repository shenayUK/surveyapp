package com.turkcell.pollservice.exception;

public abstract class AuthException extends RuntimeException {

    private String code;

    public AuthException() {
        super();
    }

    public AuthException(String code) {
        super(String.join("ERROR CODE: ", code));
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
