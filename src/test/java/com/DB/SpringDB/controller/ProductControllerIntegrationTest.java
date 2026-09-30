package com.DB.SpringDB.controller;



import java.math.BigDecimal;

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
import com.DB.SpringDB.dto.ProductDto;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Transactional 
@AutoConfigureMockMvc(addFilters=false)
public class ProductControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create product(API)")
    void shouldCreateProductApi() throws Exception {
        ProductDto dto = new ProductDto();
        dto.setName("phone");
        dto.setPrice(BigDecimal.valueOf(40000.00));
        mockMvc.perform(

            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("phone"));
    }
}