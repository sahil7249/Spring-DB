package com.DB.SpringDB.dto;

public class RegisterUserResponseDto {
    private Long id;
    private String email;


    public RegisterUserResponseDto(Long id,String email){
        this.id = id;
        this.email = email;
    }

    public Long getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
