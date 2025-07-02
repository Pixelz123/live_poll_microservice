package com.live_poll.api_gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.live_poll.api_gateway.services.JwtServices;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtServices jwt_services;
    
    public JwtAuthenticationFilter(JwtServices jwt_services) {
        this.jwt_services = jwt_services;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       
        String token = extractToken(exchange.getRequest());
        if (token == null || !jwt_services.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String username = jwt_services.extractUsername(token);
        ServerHttpRequest modified = exchange
                .getRequest()
                .mutate()
                .header("X-Authenticated-User", username)
                .build();
        return chain.filter(exchange.mutate().request(modified).build());
    }

    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return (authHeader != null && authHeader.startsWith("Bearer "))
                ? authHeader.substring(7)
                : null;
    }

}
