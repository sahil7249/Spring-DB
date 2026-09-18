package com.DB.SpringDB.dto;

import java.math.BigDecimal;

import com.DB.SpringDB.entities.Product;
import com.DB.SpringDB.entities.User;

public class OrderDto {
    private Long id;
    private BigDecimal priceAtPurchase;
    private User user;
    private Product product;

    public OrderDto(Long id,User user,BigDecimal priceAtPurchase,Product product) {
        this.id = id;
        this.user = user;
        this.priceAtPurchase = priceAtPurchase;
        this.product = product;
    }

    public Long getId(){
        return this.id;
    } 

    public UserDto getUser(){
        return new UserDto(this.user.getId(), this.user.getName(), this.user.getEmail());
    }

    public BigDecimal getPriceAtPurchase(){
        return this.priceAtPurchase;
    }

    public Product getProduct(){
        return this.product;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}
