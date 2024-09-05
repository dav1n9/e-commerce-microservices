package com.example.apigatewayservice.config;

import com.example.apigatewayservice.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-1", predicateSpec -> predicateSpec
                        .path("/api/v2/users/**", "/api/login")
                        .and().method(HttpMethod.POST)
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .removeRequestHeader(HttpHeaders.COOKIE)
                        )
                        .uri("http://localhost:8080")
                )
                .route("user-service-2", predicateSpec -> predicateSpec
                        .path("/api/v2/users/password", "/api/logout")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .filter(jwtAuthorizationFilter)
                        )
                        .uri("http://localhost:8080")
                )
                .route("order-service", predicateSpec -> predicateSpec
                        .path("/api/v2/orders/**", "/api/v2/wishlists/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .filter(jwtAuthorizationFilter)
                        )
                        .uri("http://localhost:8081")
                )
                .route("item-service", predicateSpec -> predicateSpec
                        .path("/api/v2/items/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                        )
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
