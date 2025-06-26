package com.springboot.fullstack.challenge.demo.config.security;

import com.springboot.fullstack.challenge.demo.config.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object auth = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            auth = authenticationService.authenticate(
                    authentication.getName(),
                    (String) authentication.getCredentials()
            );

        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            auth = authenticationService.findByToken((String) authentication.getPrincipal());
        }

        if (auth == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(auth, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
