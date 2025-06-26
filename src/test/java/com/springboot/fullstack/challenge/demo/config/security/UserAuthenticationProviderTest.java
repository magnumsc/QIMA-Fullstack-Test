package com.springboot.fullstack.challenge.demo.config.security;

import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.config.security.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationProviderTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserAuthenticationProvider userAuthenticationProvider;

    @Test
    @DisplayName("authenticate returns token for valid username and password")
    void authenticateReturnsTokenForValidUsernameAndPassword() {
        AuthenticationDTO authDTO = new AuthenticationDTO();
        when(authenticationService.authenticate("user", "pass")).thenReturn(authDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass");
        Authentication result = userAuthenticationProvider.authenticate(authentication);

        assertNotNull(result);
        assertEquals(authDTO, result.getPrincipal());
    }

    @Test
    @DisplayName("authenticate returns null for invalid username and password")
    void authenticateReturnsNullForInvalidUsernameAndPassword() {
        when(authenticationService.authenticate("user", "wrongPass")).thenReturn(null);

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "wrongPass");
        Authentication result = userAuthenticationProvider.authenticate(authentication);

        assertNull(result);
    }

    @Test
    @DisplayName("authenticate returns token for valid pre-authenticated token")
    void authenticateReturnsTokenForValidPreAuthenticatedToken() {
        AuthPrincipal authPrincipal = new AuthPrincipal();
        when(authenticationService.findByToken("validToken")).thenReturn(authPrincipal);

        Authentication authentication = new PreAuthenticatedAuthenticationToken("validToken", null);
        Authentication result = userAuthenticationProvider.authenticate(authentication);

        assertNotNull(result);
        assertEquals(authPrincipal, result.getPrincipal());
    }

    @Test
    @DisplayName("authenticate returns null for invalid pre-authenticated token")
    void authenticateReturnsNullForInvalidPreAuthenticatedToken() {
        when(authenticationService.findByToken("invalidToken")).thenReturn(null);

        Authentication authentication = new PreAuthenticatedAuthenticationToken("invalidToken", null);
        Authentication result = userAuthenticationProvider.authenticate(authentication);

        assertNull(result);
    }

    @Test
    @DisplayName("supports returns true for any authentication class")
    void supportsReturnsTrueForAnyAuthenticationClass() {
        assertTrue(userAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
        assertTrue(userAuthenticationProvider.supports(PreAuthenticatedAuthenticationToken.class));
    }
}