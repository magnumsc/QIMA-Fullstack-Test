package com.springboot.fullstack.challenge.demo.config.security.controller;

import com.springboot.fullstack.challenge.demo.config.security.CookieAuthenticationFilter;
import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.config.security.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    @DisplayName("login sets authentication cookie and returns authentication DTO")
    void loginSetsAuthenticationCookieAndReturnsAuthenticationDTO() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        when(authenticationService.createToken(authenticationDTO)).thenReturn("token");
        when(servletRequest.getServerName()).thenReturn("localhost");

        ResponseEntity<AuthenticationDTO> response = authenticationController.login(authenticationDTO, servletRequest, servletResponse);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authenticationDTO, response.getBody());
        verify(servletResponse).addCookie(argThat(cookie ->
                CookieAuthenticationFilter.AUTH_COOKIE_NAME.equals(cookie.getName()) &&
                        "token".equals(cookie.getValue()) &&
                        !cookie.isHttpOnly() &&
                        cookie.getSecure() &&
                        cookie.getMaxAge() == (int) Duration.of(1, ChronoUnit.HOURS).toSeconds() &&
                        "/".equals(cookie.getPath()) &&
                        "localhost".equals(cookie.getDomain())
        ));
    }

    @Test
    @DisplayName("logout clears security context and removes authentication cookie")
    void logoutClearsSecurityContextAndRemovesAuthenticationCookie() {
        Cookie authCookie = new Cookie(CookieAuthenticationFilter.AUTH_COOKIE_NAME, "token");
        Cookie[] cookies = {authCookie};
        when(servletRequest.getCookies()).thenReturn(cookies);
        when(servletRequest.getServerName()).thenReturn("localhost");

        ResponseEntity<Void> response = authenticationController.logout(mock(AuthPrincipal.class), servletRequest, servletResponse);

        assertEquals(204, response.getStatusCodeValue());
        verify(servletResponse).addCookie(argThat(cookie ->
                CookieAuthenticationFilter.AUTH_COOKIE_NAME.equals(cookie.getName()) &&
                        cookie.getMaxAge() == 0 &&
                        "/".equals(cookie.getPath()) &&
                        cookie.isHttpOnly() &&
                        cookie.getSecure() &&
                        "localhost".equals(cookie.getDomain())
        ));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("register calls authentication service and returns authentication DTO")
    void registerCallsAuthenticationServiceAndReturnsAuthenticationDTO() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        when(authenticationService.register(authenticationDTO)).thenReturn(Boolean.TRUE);

        ResponseEntity<AuthenticationDTO> response = authenticationController.register(authenticationDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authenticationDTO, response.getBody());
        verify(authenticationService).register(authenticationDTO);
    }
}