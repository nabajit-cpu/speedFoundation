package com.example.api_gateway.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {


    @Autowired
    private JwtUtil jwtUtil;

  

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logInfo("Filtering request for authentication.");

        // Extract the token from the request
        String token = extractTokenFromRequest(exchange);
        if (token != null) {
            logInfo("Token found in the request: " + token);

            // Validate the token
            if (jwtUtil.validateToken(token)) {
                logInfo("Token is valid. Extracting username.");
                String username = jwtUtil.getUsername(token);

                logInfo("Username extracted from token: " + username);

                // Create a SecurityContext and set it in ReactiveSecurityContextHolder
                SecurityContext context = new SecurityContextImpl(
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList())
                );
                logInfo("SecurityContext created for username: " + username);

                return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
            } else {
                logWarning("Token validation failed.");
            }
        } else {
            logWarning("No token found in the request.");
        }

        return chain.filter(exchange);
    }

    private String extractTokenFromRequest(ServerWebExchange exchange) {
        logInfo("Extracting token from Authorization header.");
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null) {
            logInfo("Authorization header found: " + bearerToken);

            if (bearerToken.startsWith("Bearer ")) {
                logInfo("Bearer token format is valid.");
                return bearerToken.substring(7);
            } else {
                logWarning("Authorization header does not start with 'Bearer '.");
            }
        } else {
            logWarning("No Authorization header present in the request.");
        }

        return null;
    }

    private void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    private void logWarning(String message) {
        System.out.println("[WARN] " + message);
    }
}
