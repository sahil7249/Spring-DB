package com.DB.SpringDB.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="products")
public class Product {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true,length=200)
    private String name;

    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal price;

    @Column(nullable=false)
    private Boolean active =  true;

    public Product(){}

    public Product(Long id,String name,BigDecimal price,Boolean active){
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public Boolean getActive(){
        return active;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    public boolean isActive() {
        return this.active == true;
    }
}
