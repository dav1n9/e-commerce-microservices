package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String tokenValue = jwtUtil.getToken(exchange);
            tokenValue = jwtUtil.substringToken(tokenValue);
            log.info(tokenValue);

            jwtUtil.validateToken(tokenValue);

        } catch (Exception e) {
            log.error("gateway 토큰 검증 실패", e);
            return handleUnauthorized(exchange.getResponse(), "gateway 토큰 검증 실패: " + e.getMessage());
        }

        return chain.filter(exchange);
    }

    private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json");
        String body = String.format("{\"error\": \"%s\", \"message\": \"%s\"}", HttpStatus.UNAUTHORIZED.getReasonPhrase(), message);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());
        return response.writeWith(Mono.just(buffer));
    }
}
