package com.DB.SpringDB.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DB.SpringDB.dto.CreateProductDto;
import com.DB.SpringDB.dto.ProductDto;
import com.DB.SpringDB.services.ProductService;

@RestController 
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getActiveProducts(){
        return ResponseEntity.ok().body(productService.fetchAllActiveProducts());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return  ResponseEntity.ok().body(productService.fetchAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.fetchProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto createProductDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(createProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id , @RequestBody CreateProductDto createProductDto){
        return ResponseEntity.ok().body(productService.update(id, createProductDto));
    }
}
