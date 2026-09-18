package com.DB.SpringDB.dto;

public class RegisterUserResponseDto {
    private Long id;
    private String name;


    public RegisterUserResponseDto(Long id,String name){
        this.id = id;
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}
