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

    // Secret key for signing the JWT
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("Xk7dfh92@#Fgsh87*Lkjw3201!NcvbM92#Asdgh239!Mqw32%Wkd89*Fdlwq".getBytes());

    /**
     * Validates the given JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Invalid token signature: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getMessage());
        }
        return false;
    }
    
 

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    public Claims getClaims(String token) {
        logInfo("Extracting claims from token: " + token);
        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(SECRET_KEY)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            logInfo("Claims extracted successfully: " + claims);
            return claims;
        } catch (Exception e) {
            logError("Failed to extract claims: " + e.getMessage());
            throw e; // Optionally rethrow the exception to handle it higher up
        }
    }

    /**
     * Extracts the username (or subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username (subject) contained in the token
     */
    public String getUsername(String token) {
        logInfo("Extracting username from token.");
        String username = getClaims(token).getSubject();
        logInfo("Username extracted: " + username);
        return username;
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which to generate the token
     * @param expirationMs the expiration time in milliseconds
     * @return the generated JWT token
     */
    public String generateToken(String username, long expirationMs) {
        logInfo("Generating token for username: " + username + ", expiration: " + expirationMs + " ms.");
        String token = Jwts.builder()
                           .setSubject(username)
                           .setIssuedAt(new java.util.Date())
                           .setExpiration(new java.util.Date(System.currentTimeMillis() + 5000))
                           .signWith(SECRET_KEY)
                           .compact();
        logInfo("Token generated successfully: " + token);
        return token;
    }

    // Helper methods for logging
    private void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    private void logWarning(String message) {
        System.out.println("[WARN] " + message);
    }

    private void logError(String message) {
        System.err.println("[ERROR] " + message);
    }
}
