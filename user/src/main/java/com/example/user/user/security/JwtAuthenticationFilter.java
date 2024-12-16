package com.example.user.user.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Extract token from Authorization header
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);  // Extract the token from the Authorization header
            logger.info("token in user filter " + token);

            try {
                username = jwtHelper.getUsernameFromToken(token);
                logger.info("After try username: " + username);

            } catch (IllegalArgumentException e) {
                logger.error("Illegal Argument while fetching username: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
               
                logger.error("JWT token is expired: {}", e.getMessage());

                // Extract claims from expired token to get the username
                Claims claims = jwtHelper.getAllClaimsFromToken(token);
                logger.info("claims: " , claims);
                username = claims.getSubject();  // Get the username from the claims



                // Handle token expiration
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (token != null && jwtHelper.validateToken(token, userDetails)) {
                        // Generate a new access token using the refresh token
                        String newAccessToken = jwtHelper.generateToken(userDetails);
                        response.setHeader("New-Access-Token", newAccessToken);
                        logger.info("New access token generated and sent in the response: " + newAccessToken);
                    } else {
                        logger.error("Invalid or expired refresh token.");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired refresh token");
                        return;  // Do not proceed if refresh token is also invalid
                    }
                } else {
                    logger.info("username is null");

                }
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Error while parsing JWT token: {}", e.getMessage());
            }
        }

        // If username is valid and no authentication context is set, authenticate the user
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("Token validation failed for user: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
