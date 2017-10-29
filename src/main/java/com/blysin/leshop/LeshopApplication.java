package com.blysin.leshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class LeshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeshopApplication.class, args);
	}
}
