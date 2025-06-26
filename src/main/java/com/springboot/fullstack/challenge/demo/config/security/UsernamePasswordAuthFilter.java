package com.springboot.fullstack.challenge.demo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.fullstack.challenge.demo.config.security.dto.AuthErrorDTO;
import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
            if ("/auth/login".equals(request.getServletPath()) && HttpMethod.POST.name().equals(request.getMethod())) {
                var credentials = MAPPER.readValue(request.getInputStream(), AuthenticationDTO.class);
                try {
                    SecurityContextHolder.getContext().setAuthentication(userAuthenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    credentials.getUsername(),
                                    credentials.getPassword()
                            )
                    ));
                } catch(AuthenticationCredentialsNotFoundException e) {
                    var error = new AuthErrorDTO(
                            String.valueOf(HttpServletResponse.SC_UNAUTHORIZED),
                            "Invalid credentials"
                    );
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    MAPPER.writeValue(response.getOutputStream(), error);
                    return;
                }
            }
            filterChain.doFilter(request, response);
    }
}
