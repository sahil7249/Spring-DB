package com.DB.SpringDB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DB.SpringDB.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByUserId(Long userId);
    public Optional<Order> findByIdAndUserId(Long id,Long userId);
    public void deleteByIdAndUserId(Long orderId,Long userId);
}
