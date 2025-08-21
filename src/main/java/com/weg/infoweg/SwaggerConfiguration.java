package com.weg.infoweg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpemAPI(){

        return OpemAPI();
    }
}
