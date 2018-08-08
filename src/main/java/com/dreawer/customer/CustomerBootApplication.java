package com.dreawer.customer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dreawer.customer.persistence")
public class CustomerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBootApplication.class, args);
	}
	
}
