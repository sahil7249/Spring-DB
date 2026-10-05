package com.DB.SpringDB.repository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.DB.SpringDB.AbstractIntegrationTest;
import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.repositories.ProductRepository;

import jakarta.transaction.Transactional;


@Transactional
public class ProductRepositoryIntegrationTest extends AbstractIntegrationTest{

    @Autowired 
    private ProductRepository productRepository;

    @Test 
    @DisplayName("Should return active products")
    void shouldReturnActiveProducts() {
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setPrice(BigDecimal.valueOf(50000.00));
        p1.setActive(true);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Keyboard");
        p2.setPrice(BigDecimal.valueOf(10000.00));
        p2.setActive(false);
        productRepository.save(p2);

        List<Product> products = productRepository.findAllByActiveTrueOrderByNameAsc();
        assertEquals(1, products.size());
        assertEquals("Mouse", products.get(0).getName());
    }

    @Test 
    @DisplayName("Should return true if product name is already exists")
    void shouldReturnTrueIfProductNameIsAlreadyExists() {
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setPrice(BigDecimal.valueOf(50000.00));
        p1.setActive(true);
        productRepository.save(p1);
        
        boolean exists = productRepository.existsByNameIgnoreCase("Mouse");
        assertTrue(exists);
    }

    @Test 
    @DisplayName("Should return false if product name is not already exists")
    void shouldReturnFalseIfProductNameIsNotAlreadyExists() {
        boolean exists = productRepository.existsByNameIgnoreCase("Mouse");
        assertFalse(exists);
    }

    @Test 
    @DisplayName("Should return true if product name is already exists but with different id")
    void shouldReturnTrueIfProductNameIsAlreadyExistsButWithDifferentId() {
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setPrice(BigDecimal.valueOf(50000.00));
        p1.setActive(true);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Mouse");
        p2.setPrice(BigDecimal.valueOf(10000.00));
        p2.setActive(true);
        p2.setId(4L);
        productRepository.save(p2);

        boolean exists = productRepository.existsByNameIgnoreCaseAndIdNot("Mouse", 1L);
        assertTrue(exists);
    }
}