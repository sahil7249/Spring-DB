package com.DB.SpringDB.dto;

import java.math.BigDecimal;

public class CreateProductDto {
    private String name;
    private BigDecimal price;
    private Boolean active;

    public CreateProductDto(){}

    public CreateProductDto(String name,BigDecimal price,Boolean active){
        this.name = name;
        this.price = price;
        this.active = active;
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
