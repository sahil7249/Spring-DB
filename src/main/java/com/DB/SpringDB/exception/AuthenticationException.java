package com.DB.SpringDB.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String errMsg) {
        super(errMsg);
    }
}
