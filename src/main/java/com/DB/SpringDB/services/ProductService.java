package com.DB.SpringDB.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.DB.SpringDB.dto.CreateProductDto;
import com.DB.SpringDB.dto.ProductDto;
import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.exception.DuplicateProductNameException;
import com.DB.SpringDB.exception.ProductNotFoundException;
import com.DB.SpringDB.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service 
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDto create(CreateProductDto createProductDto){
        String name = createProductDto.getName().trim();
        if(productRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateProductNameException("A product with this name already exists");
        }
        Product product = new Product();
        if(createProductDto.getActive() != null) {
            product.setActive(createProductDto.getActive());
        }
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        Product savedProduct = productRepository.save(product);
        return map(savedProduct);
    }

    public List<ProductDto> fetchAllProducts() {
        return productRepository.findAll()
               .stream()
               .map(this::map)
               .toList();
    }

    public List<ProductDto> fetchAllActiveProducts(){
        return productRepository.findAll()
               .stream()
               .filter(product -> product.getActive() == true)
               .map(this::map)
               .toList();
    }

    @Cacheable(value= "products",key="#id")
    public ProductDto fetchProductById(Long id){
        log.info("Getting product from DB for id {}",id);
        Product product = productRepository.findById(id).orElseThrow(() -> 
            new ProductNotFoundException("Product not found with id : " + id)
            );
            
            return map(product);
        }

    @Transactional 
    @CacheEvict(value = "products" ,key = "#id")
    public ProductDto update(Long id,CreateProductDto updateProductDto) {
        Product prodcut = productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product not found with id : " + id)
            );
        String name = updateProductDto.getName().trim();
        if(productRepository.existsByNameIgnoreCaseAndIdNot(name,id)) {
            throw new ProductNotFoundException("Product not found with id : " + id);
        }
        prodcut.setName(name);
        prodcut.setPrice(updateProductDto.getPrice());
        if(updateProductDto.getActive() != null) {

        }
        log.info("Getting product from DB for id {}",id);

        return map(productRepository.save(prodcut));
    }   

    public List<ProductDto> getActiveCatalog() {
        return productRepository.findAllByActiveTrueOrderByNameAsc()
                                .stream()
                                .map(this::map)
                                .toList();
    }

    public void deactivate(Long id) {
        Product prodcut = productRepository.findById(id).orElseThrow(() -> 
            new ProductNotFoundException("Product not found with id : " + id)
        );
        prodcut.setActive(false);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }


    public ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getActive());
    }

}
