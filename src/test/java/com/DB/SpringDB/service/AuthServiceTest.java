package com.DB.SpringDB.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.RegisterUserResponseDto;
import com.DB.SpringDB.entities.User;
import com.DB.SpringDB.exception.DuplicateUserException;
import com.DB.SpringDB.repositories.UserRepository;
import com.DB.SpringDB.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock 
    private UserRepository userRepository;

    @Mock 
    private PasswordEncoder encoder;

    @InjectMocks 
    private AuthService authService;

    @Test 
    @DisplayName("Should create user")
    void shouldCreateUser() {
        CreateUserDto dto = new CreateUserDto("User", "user@gmail.com", "user123");
        when(userRepository.existsByEmailIgnoreCase("user@gmail.com"))
        .thenReturn(false);
        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setEmail("user@gmail.com");
        user.setPassword(encoder.encode("user123"));
        
        when(userRepository.save(any(User.class)))
        .thenReturn(user);
        
        RegisterUserResponseDto result = authService.register(dto);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("user@gmail.com");
        
        verify(userRepository)
        .existsByEmailIgnoreCase("user@gmail.com");
        verify(userRepository)
        .save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw exception for duplicate email")
    void shouldThrowExceptionWhenUserEmailAlreadyExists() {
        CreateUserDto dto = new CreateUserDto("User", "user@gmail.com", "user123");
        when(userRepository.existsByEmailIgnoreCase("user@gmail.com"))
            .thenReturn(true);
        
        assertThatThrownBy(() -> 
            authService.register(dto))
            .isInstanceOf(DuplicateUserException.class)
            .hasMessage("User exists with email user@gmail.com , try using another email: ");

        verify(userRepository)
            .existsByEmailIgnoreCase("user@gmail.com");
        
        verify(userRepository,never())
        .save(any());
    }

}