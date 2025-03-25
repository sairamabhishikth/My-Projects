package com.tradeclover;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF if not needed
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/api/maintenance-status").permitAll()  // Allow access to maintenance status API without authentication
                .requestMatchers(HttpMethod.GET, "/maintenance").permitAll() // Allow access to the maintenance page
                .anyRequest().authenticated() // Any other request requires authentication
            )
            .httpBasic();  // Optional, if you're using basic authentication for testing

        return http.build();
    }
}
