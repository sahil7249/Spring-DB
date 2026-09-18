package com.DB.SpringDB.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DB.SpringDB.dto.CreateOrderDto;
import com.DB.SpringDB.dto.OrderDto;
import com.DB.SpringDB.entities.Order;
import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.entities.User;
import com.DB.SpringDB.exception.OrderNotFoundException;
import com.DB.SpringDB.exception.ProductNotFoundException;
import com.DB.SpringDB.repositories.OrderRepository;
import com.DB.SpringDB.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service 
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    
    public OrderService(OrderRepository orderRepository,UserService userService,ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDto create(CreateOrderDto createOrderDto) {
        User user = userService.getLoggedInUser();
        Order order = new Order();
        Product product = productRepository.findById(createOrderDto.getProduct()).orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + createOrderDto.getProduct()));
        order.setPriceAtPurchase(createOrderDto.getPriceAtPurchase());
        order.setProduct(product);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        return map(savedOrder);
    }


    public OrderDto fetchOrderByIdUser(Long orderId) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findByIdAndUserId(orderId,user.getId())
            .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId));
        return map(order);
    }

    public OrderDto fetchOrderByIdAdmin(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId));
        return map(order);
    }

    public List<OrderDto> fetchMyOrders() {
        User user = userService.getLoggedInUser();
        return orderRepository.findByUserId(user.getId())
               .stream()
               .map(this::map)
               .toList();
    }

    public List<OrderDto> fetchAllOrders() {
        return orderRepository.findAll()
               .stream()
               .map(this::map)
               .toList();
    }

    @Transactional 
    public OrderDto update(Long orderId, CreateOrderDto updateOrderDto) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
            .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId));
        if(!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        order.setUser(order.getUser());
        return  map(order);
    }

    @Transactional 
    public OrderDto patch(Long orderId, CreateOrderDto patchOrderDto) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
            .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId));
        order.setUser(order.getUser());
        return map(order);        
    }

    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDto map(Order order){
        return new OrderDto(order.getId(),order.getUser(),order.getPriceAtPurchase(),order.product);
    }
}
