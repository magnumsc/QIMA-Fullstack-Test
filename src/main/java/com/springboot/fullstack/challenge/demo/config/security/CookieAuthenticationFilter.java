package com.springboot.fullstack.challenge.demo.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CookieAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTH_COOKIE_NAME = "magnific-auth";
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var cookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(ck -> AUTH_COOKIE_NAME.equals(ck.getName()))
                .findFirst();
        cookie.ifPresent(value -> SecurityContextHolder.getContext().setAuthentication(userAuthenticationProvider.authenticate(
                new PreAuthenticatedAuthenticationToken(value.getValue(), null)
        )));
        filterChain.doFilter(request, response);
    }
}
