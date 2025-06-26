package com.springboot.fullstack.challenge.demo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsernamePasswordAuthFilterTest {

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletOutputStream servletOutputStream;

    private UsernamePasswordAuthFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new UsernamePasswordAuthFilter(userAuthenticationProvider);
    }

    @Test
    @DisplayName("doFilterInternal authenticates and sets security context on valid credentials")
    void doFilterInternalAuthenticatesAndSetsSecurityContextOnValidCredentials() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/auth/login");
        when(request.getMethod()).thenReturn("POST");
        AuthenticationDTO credentials = new AuthenticationDTO("user", "password");
        when(request.getInputStream()).thenReturn(new MockServletInputStream(new ObjectMapper().writeValueAsBytes(credentials)));
        when(userAuthenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("user", "password"));

        filter.doFilterInternal(request, response, filterChain);

        assertEquals("user", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal returns unauthorized on invalid credentials")
    void doFilterInternalReturnsUnauthorizedOnInvalidCredentials() throws ServletException, IOException {
        when(response.getOutputStream()).thenReturn(servletOutputStream);
        when(request.getServletPath()).thenReturn("/auth/login");
        when(request.getMethod()).thenReturn("POST");
        AuthenticationDTO credentials = new AuthenticationDTO("user", "wrongpassword");
        when(request.getInputStream()).thenReturn(new MockServletInputStream(new ObjectMapper().writeValueAsBytes(credentials)));
        when(userAuthenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationCredentialsNotFoundException("Invalid credentials"));

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal continues filter chain on non-login path")
    void doFilterInternalContinuesFilterChainOnNonLoginPath() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/other/path");
        when(request.getMethod()).thenReturn("GET");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal continues filter chain on non-POST method")
    void doFilterInternalContinuesFilterChainOnNonPOSTMethod() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/auth/login");
        when(request.getMethod()).thenReturn("GET");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}