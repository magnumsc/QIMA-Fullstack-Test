package com.springboot.fullstack.challenge.demo.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CookieAuthenticationFilterTest {

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private CookieAuthenticationFilter filter;

    CookieAuthenticationFilterTest() {
        MockitoAnnotations.openMocks(this);
        filter = new CookieAuthenticationFilter(userAuthenticationProvider);
    }

    @Test
    @DisplayName("doFilterInternal authenticates when auth cookie is present")
    void doFilterInternalAuthenticatesWhenAuthCookieIsPresent() throws ServletException, IOException {
        Cookie authCookie = new Cookie(CookieAuthenticationFilter.AUTH_COOKIE_NAME, "authToken");
        when(request.getCookies()).thenReturn(new Cookie[]{authCookie});
        when(userAuthenticationProvider.authenticate(any(PreAuthenticatedAuthenticationToken.class)))
                .thenReturn(mock(PreAuthenticatedAuthenticationToken.class));

        filter.doFilterInternal(request, response, filterChain);

        verify(userAuthenticationProvider).authenticate(any(PreAuthenticatedAuthenticationToken.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal does not authenticate when auth cookie is absent")
    void doFilterInternalDoesNotAuthenticateWhenAuthCookieIsAbsent() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(new Cookie[]{});

        filter.doFilterInternal(request, response, filterChain);

        verify(userAuthenticationProvider, never()).authenticate(any(PreAuthenticatedAuthenticationToken.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal does not authenticate when cookies are null")
    void doFilterInternalDoesNotAuthenticateWhenCookiesAreNull() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(userAuthenticationProvider, never()).authenticate(any(PreAuthenticatedAuthenticationToken.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal continues filter chain when auth cookie is present")
    void doFilterInternalContinuesFilterChainWhenAuthCookieIsPresent() throws ServletException, IOException {
        Cookie authCookie = new Cookie(CookieAuthenticationFilter.AUTH_COOKIE_NAME, "authToken");
        when(request.getCookies()).thenReturn(new Cookie[]{authCookie});
        when(userAuthenticationProvider.authenticate(any(PreAuthenticatedAuthenticationToken.class)))
                .thenReturn(mock(PreAuthenticatedAuthenticationToken.class));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
