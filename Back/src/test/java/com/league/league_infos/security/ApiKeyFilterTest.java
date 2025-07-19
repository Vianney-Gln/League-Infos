package com.league.league_infos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
public class ApiKeyFilterTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;


    @Test
    @DisplayName("should send an error if origin is not allowed with null api-key")
    void doFilterInternal_success_1() throws ServletException, IOException {
        // GIVEN
        ApiKeyFilter apiKeyFilter = new ApiKeyFilter();
        setField(apiKeyFilter, "allowedOrigin", "https://my-front.com");
        setField(apiKeyFilter, "backendApiKey", "secret");

        when(request.getHeader("Origin")).thenReturn("https://postman.com");
        when(request.getHeader("Referer")).thenReturn("https://postman.com/");
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        // WHEN
        apiKeyFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(response, times(1)).sendError(401, "Unauthorized: Missing or invalid API key");
    }

    @Test
    @DisplayName("should send an error if origin is not allowed with wrong api-key")
    void doFilterInternal_success_2() throws ServletException, IOException {
        // GIVEN
        ApiKeyFilter apiKeyFilter = new ApiKeyFilter();
        setField(apiKeyFilter, "allowedOrigin", "https://my-front.com");
        setField(apiKeyFilter, "backendApiKey", "secret");

        when(request.getHeader("Origin")).thenReturn("https://postman.com");
        when(request.getHeader("Referer")).thenReturn("https://postman.com/");
        when(request.getHeader("X-API-KEY")).thenReturn("test");

        // WHEN
        apiKeyFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(response, times(1)).sendError(401, "Unauthorized: Missing or invalid API key");
    }

    @Test
    @DisplayName("should not send an error if origin is not allowed with valid api-key, and should call doFilter")
    void doFilterInternal_success_3() throws ServletException, IOException {
        // GIVEN
        ApiKeyFilter apiKeyFilter = new ApiKeyFilter();
        setField(apiKeyFilter, "allowedOrigin", "https://my-front.com");
        setField(apiKeyFilter, "backendApiKey", "secret");

        when(request.getHeader("Origin")).thenReturn("https://postman.com");
        when(request.getHeader("Referer")).thenReturn("https://postman.com/");
        when(request.getHeader("X-API-KEY")).thenReturn("secret");

        // WHEN
        apiKeyFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(response, never()).sendError(anyInt(), any());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
