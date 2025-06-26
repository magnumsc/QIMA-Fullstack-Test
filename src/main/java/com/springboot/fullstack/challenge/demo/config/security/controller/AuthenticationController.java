package com.springboot.fullstack.challenge.demo.config.security.controller;

import com.springboot.fullstack.challenge.demo.config.security.CookieAuthenticationFilter;
import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.config.security.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(
            @AuthenticationPrincipal AuthenticationDTO authenticationDTO,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse
    ) {
        var cookie = new Cookie(
                CookieAuthenticationFilter.AUTH_COOKIE_NAME,
                authenticationService.createToken(authenticationDTO)
        );
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setMaxAge((int) Duration.of(1, ChronoUnit.HOURS).toSeconds());
        cookie.setPath("/");
        cookie.setDomain(servletRequest.getServerName());
        servletResponse.addCookie(cookie);

        return ResponseEntity.ok(authenticationDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse
    ) {
        SecurityContextHolder.clearContext();
        Stream.of(Optional.ofNullable(servletRequest.getCookies()).orElse(new Cookie[0]))
                .filter(ck -> CookieAuthenticationFilter.AUTH_COOKIE_NAME.equals(ck.getName()))
                .forEach(cookie -> {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setDomain(servletRequest.getServerName());
                    servletResponse.addCookie(cookie);
                });
        return ResponseEntity.noContent().build();
    }

    // TODO: Implement proper method
    @PostMapping("/register")
    public ResponseEntity<AuthenticationDTO> register(AuthenticationDTO authenticationDTO) {
        authenticationService.register(authenticationDTO);
        return ResponseEntity.ok(authenticationDTO);
    }
}
