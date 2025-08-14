package com.cricketacademy.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Simplified Security Configuration
 * Clear separation between public and protected endpoints
 */
@Configuration
@EnableWebSecurity
public class SimpleSecurityConfig {

    private final SimpleJwtFilter jwtFilter;

    public SimpleSecurityConfig(SimpleJwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - NO AUTHENTICATION REQUIRED
                        .requestMatchers(
                                // CORS preflight
                                "OPTIONS", "/**")
                        .permitAll()
                        .requestMatchers(
                                // Career registration - PUBLIC ACCESS
                                "/career/public/**",
                                // Authentication endpoints
                                "/auth/**",
                                // Registration endpoints
                                "/register/**",
                                // Health check
                                "/health/**",
                                // Payment endpoints
                                "/payment/**",
                                "/enrollments/**",
                                // Static resources
                                "/static/**",
                                "/*.html",
                                "/*.js",
                                "/*.css",
                                "/*.ico",
                                "/assets/**",
                                // Public API
                                "/public/**")
                        .permitAll()
                        // OTP endpoints
                        .requestMatchers("/users/*/send-otp", "/users/*/verify-otp").permitAll()
                        // Admin endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Coach endpoints
                        .requestMatchers("/coach/**").hasRole("COACH")
                        // User endpoints - require authentication
                        .requestMatchers("/user/**").authenticated()
                        // All other requests require authentication
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow specific origins for development
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:3000",
                "http://127.0.0.1:5173",
                "http://127.0.0.1:5174"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}