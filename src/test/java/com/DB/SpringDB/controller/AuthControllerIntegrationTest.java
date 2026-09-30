package com.DB.SpringDB.controller;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.DB.SpringDB.AbstractIntegrationTest;
import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.LoginUserDto;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Transactional
@AutoConfigureMockMvc(addFilters = false) 
public class AuthControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should register the user(API)")
    void shouldRegisterUserApi() throws Exception {
        CreateUserDto user = new CreateUserDto();
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("user123");

        mockMvc.perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))

        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.email").value("user@gmail.com"));
    }

    @Test
    @DisplayName("Should login user successfully")
    void shouldLoginUserSuccessfully() throws Exception {
        CreateUserDto register = new CreateUserDto();
        register.setName("login-user");
        register.setEmail("user@email.com");
        register.setPassword("user123");
        mockMvc.perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(register))
        )
        .andExpect(status().isCreated());
        LoginUserDto login = new LoginUserDto();
        login.setEmail("user@email.com");
        login.setPassword("user123");

        mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jwt").exists())
        .andExpect(jsonPath("$.email").value("user@email.com"));
    }
}