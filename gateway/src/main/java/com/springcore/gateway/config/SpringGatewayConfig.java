package com.springcore.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth_route", r -> r.path("/api/v1/auth/**")
                        .uri("http://localhost:8081/api/v1/auth"))
                .route("user_route", r -> r.path("/api/v1/users/**")
                        .uri("http://localhost:8081/api/v1/users"))
                .route("product_route", r -> r.path("/api/v1/products/**")
                        .uri("http://localhost:8082/api/v1/products"))
                .route("merchant_route", r -> r.path("/api/v1/merchants/**")
                        .uri("http://localhost:8082/api/v1/merchants"))
                .route("order_route", r -> r.path("/api/v1/orders/**")
                        .uri("http://localhost:8082/api/v1/orders"))
                .build();
    }
}
