package com.cricketacademy.api.config;

import com.cricketacademy.api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplified JWT Filter
 * Only processes JWT for non-public endpoints
 */
@Component
public class SimpleJwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJwtFilter.class);
    private final JwtUtil jwtUtil;

    // Public endpoints that should skip JWT processing entirely
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/career/public",
            "/api/auth",
            "/auth",
            "/api/register",
            "/register",
            "/api/health",
            "/health",
            "/api/payment",
            "/api/enrollments",
            "/api/public",
            "/static",
            "/assets");

    public SimpleJwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            @org.springframework.lang.NonNull HttpServletRequest request,
            @org.springframework.lang.NonNull HttpServletResponse response,
            @org.springframework.lang.NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        logger.debug("Processing request: {} {}", method, path);

        // Skip JWT processing for OPTIONS requests (CORS preflight)
        if ("OPTIONS".equals(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Skip JWT processing for public endpoints
        boolean isPublicEndpoint = PUBLIC_PATHS.stream().anyMatch(path::startsWith);
        if (isPublicEndpoint) {
            logger.debug("Skipping JWT processing for public endpoint: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        // Process JWT for protected endpoints
        String jwt = extractJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {
            try {
                if (jwtUtil.validateToken(jwt)) {
                    Claims claims = jwtUtil.getClaimsFromToken(jwt);
                    String email = claims.getSubject();

                    if (StringUtils.hasText(email)) {
                        List<String> roles = extractRoles(claims);

                        if (!roles.isEmpty()) {
                            List<SimpleGrantedAuthority> authorities = roles.stream()
                                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    email, null, authorities);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            logger.debug("Authentication set for user: {}", email);
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn("JWT processing failed: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<String> extractRoles(Claims claims) {
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        } else if (rolesObj instanceof String) {
            return Arrays.asList(((String) rolesObj).split(","));
        }
        return Arrays.asList("USER"); // Default role
    }
}