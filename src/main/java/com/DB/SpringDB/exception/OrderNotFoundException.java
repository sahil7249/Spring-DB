package com.DB.SpringDB.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String errMessage){
        super(errMessage);
    }
}