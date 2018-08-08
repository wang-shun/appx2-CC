package com.dreawer.customer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <CODE>Swagger2</CODE>
 *
 * @author fenrir
 * @Date 18-7-23
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 创建用户API文档
     * @return
     */
    @Bean
    public Docket createRestUserApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dreawer.customer"))
                .paths(PathSelectors.any())
                .build();

    }



}