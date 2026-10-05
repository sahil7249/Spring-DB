package com.DB.SpringDB.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import org.springframework.http.HttpHeaders;

import com.DB.SpringDB.AbstractIntegrationTest;
import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.LoginUserDto;
import com.DB.SpringDB.dto.LoginUserResponseDto;
import com.DB.SpringDB.dto.RegisterUserResponseDto;


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Test 
    @DisplayName("Should register a new user")
    void shouldRegisterANewUser() {
        RestClient restClient = restClientBuilder("/api/v1");
        
        CreateUserDto dto = new CreateUserDto();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPassword(passwordEncoder.encode("password"));


        RegisterUserResponseDto response = 
            restClient.post()
                .uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(RegisterUserResponseDto.class);

        assertNotNull(response);
        assertEquals(dto.getEmail(), response.getEmail());
    }

    private RestClient restClientBuilder(String uri) {
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl(uri))
            .build();
        return restClient;
    }

    private String baseUrl(String uri) {
        return "http://localhost:" + port + uri; 
    }


    @Test
    @DisplayName("Should reject the duplicate email")
    void shouldRejectDuplicateEmail() {
        RestClient restClient = restClientBuilder("/api/v1");

        CreateUserDto dto = new CreateUserDto();
        dto.setName("John doe");
        dto.setEmail("john@gmail.com");
        dto.setPassword(passwordEncoder.encode("password"));


        // First time register.
        restClient.post()
            .uri("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .retrieve()
            .body(RegisterUserResponseDto.class);
        
        // Second time should throw the error and reject the duplicate email.
        assertThrows(
            HttpClientErrorException.BadRequest.class, 
            () -> restClient.post()
                            .uri("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(dto)
                            .retrieve()
                            .body(String.class)
        );
    }

    @Test
    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully() {
        CreateUserDto registerUser = new CreateUserDto("John Doe","john1@gmail.com","john@123");
        RestClient restClient = restClientBuilder("/api/v1");
        
        RegisterUserResponseDto registerResponse = restClient.post()
            .uri("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(registerUser)
            .retrieve()
            .body(RegisterUserResponseDto.class);

        assertNotNull(registerResponse);
        
        LoginUserDto loginDto = new LoginUserDto("john1@gmail.com","john@123");
        
        LoginUserResponseDto response = restClient.post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginDto)
            .retrieve()
            .body(LoginUserResponseDto.class);
            
            assertNotNull(response);
            assertNotNull(response.getJwt());
        }
        
        
        @Test
        @DisplayName("Should access the protected route")
        void shouldAccessProtectedRoute() {
            CreateUserDto registerUser = new CreateUserDto("John Doe","john2@gmail.com","john@123");
            RestClient restClient = restClientBuilder("/api/v1");
            
            RegisterUserResponseDto registerResponse = restClient.post()
                .uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(registerUser)
                .retrieve()
                .body(RegisterUserResponseDto.class);
            assertNotNull(registerResponse);

            LoginUserDto loginDto = new LoginUserDto("john2@gmail.com","john@123");
            
            LoginUserResponseDto loginResponse = restClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(loginDto)
                .retrieve()
                .body(LoginUserResponseDto.class);

            String token = loginResponse.getJwt();
            assertNotNull(token);

            String response = 
                restClient.get()
                    .uri("/products")
                    .header(
                        HttpHeaders.AUTHORIZATION, "Bearer "+token
                    )
                    .retrieve()
                    .body(String.class);
            assertNotNull(response);
    }
}