package com.DB.SpringDB.dto;

public class ErrorDto {
    private String code;
    private String message;

    public ErrorDto(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

}
