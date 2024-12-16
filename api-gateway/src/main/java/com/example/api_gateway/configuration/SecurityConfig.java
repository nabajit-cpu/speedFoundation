package com.example.api_gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.example.api_gateway.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http ) {
        http.csrf(csrf -> csrf.disable())
                .addFilterAt(filter, SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated());
        return http.build();
    }

}


