package com.DB.SpringDB.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserDto {

    @NotBlank (message = "name must not be blank")
    private String name;

    @NotBlank (message = "email must not be blank")
    @Email (message = "provide a valid email address")
    private String email;
    
    
    @NotBlank (message = "Password must not be blank")
    private String password;

    public CreateUserDto(){}

    public CreateUserDto(String name,String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
}
