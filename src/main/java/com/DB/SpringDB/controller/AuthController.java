package com.DB.SpringDB.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.LoginUserDto;
import com.DB.SpringDB.dto.LoginUserResponseDto;
import com.DB.SpringDB.dto.RegisterUserResponseDto;
import com.DB.SpringDB.services.AuthService;

@RestController 
@RequestMapping ("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginUser(loginUserDto));
    }
}
