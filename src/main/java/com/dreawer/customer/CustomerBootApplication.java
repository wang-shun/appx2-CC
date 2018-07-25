package com.dreawer.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class CustomerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBootApplication.class, args);
	}
	
}
