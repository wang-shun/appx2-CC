package com.dreawer.customer;

import com.dreawer.responsecode.rcdt.ResponseCode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dreawer.customer.persistence")
public class CustomerBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBootApplication.class, args);
		ResponseCode.initNamespace("cc");
	}

	@Bean // 定义REST客户端，RestTemplate实例
	@LoadBalanced // 开启负载均衡的能力
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(15000);// 设置超时
		requestFactory.setReadTimeout(20000);
		return new RestTemplate(requestFactory);
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.dreawer.customer.web"))//api接口包扫描路径
				.paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
				.build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("客户中心接口文档")//设置文档的标题
				.build();
	}



}
