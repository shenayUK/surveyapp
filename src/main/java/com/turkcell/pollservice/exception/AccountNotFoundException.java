package com.turkcell.pollservice.exception;

public class AccountNotFoundException extends AuthException {

    public AccountNotFoundException() {
        super("ACCOUNT_NOT_FOUND");
    }
}
