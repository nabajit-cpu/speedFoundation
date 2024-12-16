package com.example.api_gateway.security;

import java.io.PrintWriter;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws java.io.IOException, ServletException {
        System.err.println("Inside commence");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        System.err.println("request: " + request.toString());
        System.err.println("response: " + response.toString());

        PrintWriter writer = response.getWriter();
        writer.println("Access Denied !! " + authException.getMessage());
    }
}

