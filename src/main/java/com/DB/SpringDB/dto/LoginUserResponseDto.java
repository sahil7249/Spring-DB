package com.DB.SpringDB.dto;

public class LoginUserResponseDto {
    private String jwt;

    private String email;

    public LoginUserResponseDto(String jwt, String email) {
        this.jwt = jwt;
        this.email = email;
    }

    public String getJwt(){
        return jwt;
    }
    
    public void setJwt(String jwt){
        this.jwt = jwt;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }   
}
