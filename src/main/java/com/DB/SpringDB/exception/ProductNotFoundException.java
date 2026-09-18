package com.DB.SpringDB.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String errMsg){
        super(errMsg);
    }
}
