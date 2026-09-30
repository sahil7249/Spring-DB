package com.DB.SpringDB.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String errMsg) {
        super(errMsg);
    }
}