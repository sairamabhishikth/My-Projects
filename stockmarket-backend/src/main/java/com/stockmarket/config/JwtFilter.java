package com.stockmarket.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import com.stockmarket.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") @NotNull HttpServletRequest request, @SuppressWarnings("null") @NotNull HttpServletResponse response,@SuppressWarnings("null") @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract token
            System.out.println("Authorization Header: " + authorizationHeader);
            System.out.println("Extracted Token: " + token);

            try {
                String username = jwtUtil.extractUsername(token);

                // If username exists and no authentication is already set
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Validate the token
                    if (jwtUtil.validateToken(token)) {
                        UserDetails userDetails = User.builder()
                                .username(username)
                                .password("") // No password needed for this validation
                                .roles("USER") // Assign roles if needed
                                .build();

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in JwtFilter: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
