package com.example.user.user.configuration;

import java.beans.Customizer;

import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                .requestMatchers("/user/register", "/user/login").permitAll()
                .anyRequest().authenticated()
                );
                // .formLogin(form -> form
                // .loginPage("/user/login") // Redirect to a custom login page if unauthorized
                // .permitAll() // Allow all users to access the login page
                // )
                // .logout(logout -> logout
                // .logoutUrl("/user/logout") // URL for logout
                // .logoutSuccessUrl("/user/login?logout") // Redirect to login page after logout
                // .permitAll()
                // );

        return http.build();
    }

}
