package com.DB.SpringDB.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DB.SpringDB.dto.CreateOrderDto;
import com.DB.SpringDB.dto.OrderDto;
import com.DB.SpringDB.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ADMIN - GET order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderByIdAdmin(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.fetchOrderByIdAdmin(id));
    }

    // ADMIN - GET all orders
    @GetMapping 
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.fetchAllOrders());
    }

    // USER - GET my orders
    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.fetchMyOrders());
    }

    // USER - GET order by id
    @GetMapping("/my/{id}")
    public ResponseEntity<OrderDto> getOrderByIdUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.fetchOrderByIdUser(id));
    }

    // USER - Create order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(createOrderDto));
    }

    // ADMIN - Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // USER - update order
    @PutMapping("/my/{id}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable Long id,@RequestBody CreateOrderDto updateOrderDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.update(id,updateOrderDto));
    }

    // USER - patch order
    @PatchMapping("/my/{id}")
    public ResponseEntity<OrderDto> patchOrder(@PathVariable Long id,@RequestBody CreateOrderDto patchOrderDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.patch(id,patchOrderDto));
    }

}
