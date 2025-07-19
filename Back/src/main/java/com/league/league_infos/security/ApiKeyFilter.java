package com.league.league_infos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Profile("prod")
public class ApiKeyFilter extends OncePerRequestFilter {
    @Value("${app.api.apiKey}")
    private String backendApiKey;

    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");
        String apiKey = request.getHeader("X-API-KEY");

        boolean fromAllowedOrigin = (origin != null && origin.equals(allowedOrigin)) || (referer != null && referer.startsWith(allowedOrigin));
        boolean hasValidApiKey = backendApiKey.equals(apiKey);

        if (!fromAllowedOrigin && !hasValidApiKey) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing or invalid API key");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
