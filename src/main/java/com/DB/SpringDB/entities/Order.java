package com.DB.SpringDB.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FetchType.LAZY will fetch the data whenever user is needed and type EAGER will fetch data whenever the order instance is called.
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal priceAtPurchase;

    @ManyToOne 
    @JoinColumn(name="product_id")
    public Product product;

    public Order(){}

    public Order(Long id,BigDecimal priceAtPurchase,User user) {
        this.id = id;
        this.priceAtPurchase = priceAtPurchase;
        this.user = user;
    }

    public Long getId(){
        return this.id;
    }

    public Product getProduct(){
        return this.product;
    }

    public User getUser(){
        return this.user;
    }

    public BigDecimal getPriceAtPurchase(){
        return this.priceAtPurchase;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product){
        this.product = product;
    }

}
