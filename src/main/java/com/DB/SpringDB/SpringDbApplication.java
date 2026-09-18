package com.DB.SpringDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching 
@SpringBootApplication
public class SpringDbApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringDbApplication.class, args);
	}
}