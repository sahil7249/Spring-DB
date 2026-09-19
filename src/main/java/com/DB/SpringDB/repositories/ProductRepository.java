package com.DB.SpringDB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DB.SpringDB.entities.Product;

@Repository 
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findAllByActiveTrueOrderByNameAsc();
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name,Long id);
}
