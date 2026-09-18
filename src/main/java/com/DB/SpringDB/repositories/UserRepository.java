package com.DB.SpringDB.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DB.SpringDB.entities.User;

public interface  UserRepository extends JpaRepository<User, Long> {
    @Override 
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
}
