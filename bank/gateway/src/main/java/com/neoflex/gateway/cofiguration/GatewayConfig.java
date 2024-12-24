package com.neoflex.gateway.cofiguration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("statementRoute", r -> r.path("/statement/**")
                        .and()
                        .method("POST")
                        .uri("http://localhost:8080"))
                .route("dealRoute", r -> r.path("/deal/**")
                        .and()
                        .method("POST")
                        .uri("http://localhost:8081"))
                .route("adminRoute", r -> r.path("/deal/admin/**")
                        .and()
                        .query("type=admin")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8081"))
                .build();
    }
}
