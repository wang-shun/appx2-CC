package com.dreawer.customer;

import com.dreawer.responsecode.rcdt.ResponseCode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dreawer.customer.persistence")
public class CustomerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBootApplication.class, args);
		ResponseCode.initNamespace("cc");
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
