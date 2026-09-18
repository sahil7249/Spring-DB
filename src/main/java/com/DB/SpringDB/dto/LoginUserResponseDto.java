package com.DB.SpringDB.dto;

public class LoginUserResponseDto {
    private String jwt;

    public LoginUserResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
    
    public void setJwt(String jwt){
        this.jwt = jwt;
    }
}
