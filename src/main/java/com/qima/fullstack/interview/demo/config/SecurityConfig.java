package com.qima.fullstack.interview.demo.config;

import com.qima.fullstack.interview.demo.config.security.CookieAuthenticationFilter;
import com.qima.fullstack.interview.demo.config.security.UserAuthenticationEntryPoint;
import com.qima.fullstack.interview.demo.config.security.UserAuthenticationProvider;
import com.qima.fullstack.interview.demo.config.security.UsernamePasswordAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new UsernamePasswordAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthenticationFilter(userAuthenticationProvider), UsernamePasswordAuthFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .logout((logout) -> {
                    logout.deleteCookies(CookieAuthenticationFilter.AUTH_COOKIE_NAME);
                })
                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll();
//                    authorize.anyRequest().authenticated();
                    authorize.anyRequest().permitAll();
                });
        return httpSecurity.build();
    }

}
