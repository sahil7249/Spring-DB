package com.DB.SpringDB.dto;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean active;

    public ProductDto(){}

    public ProductDto(Long id,String name,BigDecimal price,Boolean active){
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
}
