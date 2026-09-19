package com.DB.SpringDB.exception;

public class DuplicateProductNameException extends RuntimeException{
    public DuplicateProductNameException(String errMssg) {
        super(errMssg);
    }
}
