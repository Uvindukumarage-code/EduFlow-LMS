package com.eduflow.enrollment.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    @Value("${api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.contains("swagger") || path.contains("api-docs") || path.contains("/health")) {
            filterChain.doFilter(request, response);
            return;
        }
        String key = request.getHeader("X-API-KEY");
        if (key == null || !key.equals(apiKey)) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid or missing API Key\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
