package com.example.api_gateway.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage()); // Log the exception for debugging
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage()); // Log the exception for debugging
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage()); // Log any other validation issues
        }
        return false;
    }

    // Get Claims from JWT Token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Get username (or any other claim) from the token
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}
