package com.DB.SpringDB.dto;

import java.math.BigDecimal;

import com.DB.SpringDB.entities.User;

public class CreateOrderDto {
   
    private Long product;
    private User user;
    private BigDecimal priceAtPurchase;

    public CreateOrderDto(Long product,User user,BigDecimal priceAtPurchase) {
        this.product = product;
        this.user = user;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getProduct(){
        return this.product;
    }

    public User getUser(){
        return this.user;
    }

    public BigDecimal getPriceAtPurchase(){
        return this.priceAtPurchase;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Long product){
        this.product = product;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase){
        this.priceAtPurchase = priceAtPurchase;
    }
}
