package com.example.events.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests()
                .requestMatchers("/events/test", "/events/createEvent","/events/deleteEvent**", "events/getEvents","/events/addParticipant","/events/updateEvent**").permitAll() // Allow both endpoints
                .anyRequest().authenticated();
        return http.build();
    }
    
}
