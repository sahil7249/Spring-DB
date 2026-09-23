package com.DB.SpringDB.repository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.DB.SpringDB.AbstractIntegretionTest;
import com.DB.SpringDB.dto.CreateProductDto;
import com.DB.SpringDB.dto.ProductDto;
import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.exception.DuplicateProductNameException;
import com.DB.SpringDB.exception.ProductNotFoundException;
import com.DB.SpringDB.repositories.ProductRepository;
import com.DB.SpringDB.services.ProductService;

import jakarta.transaction.Transactional;

@Transactional 
public class ProductIntegrationTest extends AbstractIntegretionTest {
    @Autowired 
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("Should create product")
    void shouldCreateProduct() {
        CreateProductDto dto = new CreateProductDto();

        dto.setName("Keyboard");
        dto.setPrice(BigDecimal.valueOf(4000.00));
        ProductDto result = productService.create(dto);

        assertNotNull(result);
        assertEquals("Keyboard",result.getName());
    }


    @Test 
    @DisplayName("Should throw duplicate product exception")
    void shouldThrowDuplicateProductException() {
        Product product = new Product();
        product.setName("Phone");
        product.setPrice(BigDecimal.valueOf(40000.00));
        
        productRepository.save(product);

        CreateProductDto dto = new CreateProductDto();
        dto.setName("Phone");
        dto.setPrice(BigDecimal.valueOf(40000.00));
        
        assertThrows(
            DuplicateProductNameException.class, () -> productService.create(dto));
    }

    @Test 
    @DisplayName("Should save product")
    void shouldSaveProduct() {
        Product product = new Product();
        product.setName("Phone");
        product.setActive(true);
        product.setPrice(BigDecimal.valueOf(50000.00));
        
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
        assertEquals("Phone", savedProduct.getName());
    }
    
    @Test
    @DisplayName("Should return active produts")
    void shouldReturnActiveProducts() {
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setActive(true);
        p1.setPrice(BigDecimal.valueOf(50000.00));

        Product p2 = new Product(); 
        p2.setName("Keyboard");
        p2.setActive(false);
        p2.setPrice(BigDecimal.valueOf(5000.00));

        productRepository.saveAll(List.of(p1,p2));
        List<Product> products = productRepository.findAllByActiveTrueOrderByNameAsc();

        assertEquals(1,products.size());
        assertEquals("Mouse",products.get(0).getName());
    }

    @Test 
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setPrice(BigDecimal.valueOf(50000.00));

        Product savedProduct = productRepository.save(p1);
        productService.delete(savedProduct.getId());

        assertThrows(
            ProductNotFoundException.class,
            () -> productService.fetchProductById(savedProduct.getId())
        );
    }
}   