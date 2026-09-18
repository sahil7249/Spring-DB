package com.DB.SpringDB.services;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.LoginUserDto;
import com.DB.SpringDB.dto.LoginUserResponseDto;
import com.DB.SpringDB.dto.RegisterUserResponseDto;
import com.DB.SpringDB.entities.Role;
import com.DB.SpringDB.entities.User;
import com.DB.SpringDB.repositories.UserRepository;
import com.DB.SpringDB.security.JwtService;

import jakarta.transaction.Transactional;

@Service 
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional 
    public RegisterUserResponseDto register(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.ADMIN);
        
        User savedUser = userRepository.save(user);
        return new RegisterUserResponseDto(savedUser.getId(),savedUser.getName());
    }   

    public LoginUserResponseDto loginUser(LoginUserDto loginUserDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
        );

        String jwToken = jwtService.generateJWToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));   
        return new LoginUserResponseDto(jwToken);
    }
}
