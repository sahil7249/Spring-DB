package com.DB.SpringDB;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.DB.SpringDB.dto.CreateProductDto;
import com.DB.SpringDB.dto.ProductDto;
import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.exception.DuplicateProductNameException;
import com.DB.SpringDB.exception.ProductNotFoundException;
import com.DB.SpringDB.repositories.ProductRepository;
import com.DB.SpringDB.services.ProductService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock 
    private ProductRepository productRepository;

    @InjectMocks 
    private ProductService productService;
    
    @Test 
    void shouldReturnProductById(){
        Product product = new Product();    
        product.setActive(true);
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(30000.00));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto actualProduct = productService.fetchProductById(1l);

        assertThat(actualProduct.getName()).isEqualTo("Laptop");

        verify(productRepository).findById(1L);
        verify(productRepository,times(1)).findById(1L);
    }

    @Test 
    void shouldThrowExceptionWhenProductNotFound(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.fetchProductById(1L))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage("Product not found with id : 1");

        verify(productRepository,times(1)).findById(1L);
    }

    @Test 
    void shouldReturnActiveCatalog() {
        Product p1 = new Product();
        p1.setId(1L);

        
        p1.setName("Laptop");
        p1.setPrice(BigDecimal.valueOf(30000.00));
        p1.setActive(true);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Phone");
        p2.setPrice(BigDecimal.valueOf(30000.00));
        p2.setActive(true);


        when(productRepository.findAllByActiveTrueOrderByNameAsc())
                .thenReturn(List.of(p1,p2));
        List<ProductDto> result = productService.getActiveCatalog();
        
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName())
                .isEqualTo("Laptop");
        verify(productRepository).findAllByActiveTrueOrderByNameAsc();
    }      

    @Test 
    void shouldCreateProduct() {
        CreateProductDto dto = new CreateProductDto("Laptop", BigDecimal.valueOf(40000.00), true);
        
        when(productRepository.existsByNameIgnoreCase("Laptop"))
                .thenReturn(false);
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setPrice(BigDecimal.valueOf(40000.00));
        savedProduct.setActive(true);

        when(productRepository.save(any(Product.class)))
            .thenReturn(savedProduct);
        
        ProductDto result = productService.create(dto);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop");

        verify(productRepository)
            .existsByNameIgnoreCase("Laptop");
        
        verify(productRepository)
            .save(any(Product.class));
    }

    @Test 
    void shouldThrowExceptionWhenProductNameAlreadyExists() {
        CreateProductDto dto = new CreateProductDto("Laptop", BigDecimal.valueOf(40000.00), true);
        when(productRepository.existsByNameIgnoreCase("Laptop"))
            .thenReturn(true);
        
        assertThatThrownBy(() ->
            productService.create(dto))
            .isInstanceOf(DuplicateProductNameException.class)
            .hasMessage("A product with this name already exists");

        verify(productRepository)
            .existsByNameIgnoreCase("Laptop");
        
        verify(productRepository,never())
            .save(any());
    }


    @Test 
    void shouldUpdateProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Laptop");
        existingProduct.setPrice(BigDecimal.valueOf(30000.00));
        existingProduct.setActive(false);

        CreateProductDto dto = new CreateProductDto("New Laptop", BigDecimal.valueOf(50000.00), false);
        when(productRepository.findById(1L))
            .thenReturn(Optional.of(existingProduct));
        
        when(productRepository.existsByNameIgnoreCaseAndIdNot("New Laptop",1L))
            .thenReturn(false);
        
        when(productRepository.save(any(Product.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
    
        ProductDto result = productService.update(1L, dto);
        assertThat(result.getName())
            .isEqualTo("New Laptop");
        assertThat(result.getPrice())
            .isEqualTo(BigDecimal.valueOf(50000.00));
        assertThat(result.getActive())
            .isFalse();
    }

    @Test 
    void shouldDeactivateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setActive(true);

        when(productRepository.findById(1L))
            .thenReturn(Optional.of(product));
        
        productService.deactivate(1L);

        assertThat(product.isActive())
            .isFalse();
        
        verify(productRepository).findById(1L);
    }
}
