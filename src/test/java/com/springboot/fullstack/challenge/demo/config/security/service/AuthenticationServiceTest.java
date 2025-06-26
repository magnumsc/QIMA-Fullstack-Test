package com.springboot.fullstack.challenge.demo.config.security.service;

import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.model.UserModel;
import com.springboot.fullstack.challenge.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("authenticate returns AuthenticationDTO for valid credentials")
    void authenticateReturnsAuthenticationDTOForValidCredentials() {
        UserModel user = new UserModel();
        user.setUsername("user");
        user.setPassword("encodedPass");
        user.setGuid("1234");
        user.setRole(1);

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(passwordEncoder.matches("pass", "encodedPass")).thenReturn(true);

        AuthenticationDTO result = authenticationService.authenticate("user", "pass");

        assertEquals("user", result.getUsername());
        assertEquals("encodedPass", result.getPassword());
        assertEquals("1234", result.getGuid());
        assertEquals(UserRoles.USER, result.getRole());
    }

    @Test
    @DisplayName("authenticate throws exception for invalid credentials")
    void authenticateThrowsExceptionForInvalidCredentials() {
        UserModel user = new UserModel();
        user.setUsername("user");
        user.setPassword("encodedPass");

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.authenticate("user", "wrongPass");
        });
    }

    @Test
    @DisplayName("findByToken returns AuthPrincipal for valid token")
    void findByTokenReturnsAuthPrincipalForValidToken() {
        ReflectionTestUtils.setField(authenticationService, "secretKey", "secret-key");

        UserModel user = new UserModel();
        user.setUsername("user");
        user.setPassword("encodedPass");
        user.setGuid("1234");
        user.setRole(1);

        when(userRepository.findByUsername("user")).thenReturn(user);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername("user");
        authenticationDTO.setPassword("encodedPass");
        authenticationDTO.setGuid("1234");

        String token = authenticationService.createToken(authenticationDTO);

        AuthPrincipal result = authenticationService.findByToken(token);

        assertEquals("user", result.getUsername());
        assertEquals("1234", result.getGuid());
        assertEquals(UserRoles.USER, result.getRole());
    }

    @Test
    @DisplayName("findByToken throws exception for invalid token")
    void findByTokenThrowsExceptionForInvalidToken() {
        ReflectionTestUtils.setField(authenticationService, "secretKey", "secret-key");

        UserModel user = new UserModel();
        user.setUsername("user");
        user.setPassword("encodedPass");

        when(userRepository.findByUsername("user")).thenReturn(user);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.findByToken("user:invalidToken");
        });
    }

    @Test
    @DisplayName("createToken returns valid token")
    void createTokenReturnsValidToken() {
        ReflectionTestUtils.setField(authenticationService, "secretKey", "secret-key");

        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername("user");
        authenticationDTO.setPassword("encodedPass");
        authenticationDTO.setGuid("1234");

        String token = authenticationService.createToken(authenticationDTO);

        assertNotNull(token);
        assertTrue(token.startsWith("user:"));
    }

    @Test
    @DisplayName("register saves user and returns true")
    void registerSavesUserAndReturnsTrue() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername("user");
        authenticationDTO.setPassword("pass");

        UserModel userModel = new UserModel();
        userModel.setUsername("user");
        userModel.setPassword("encodedPass");

        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        Boolean result = authenticationService.register(authenticationDTO);

        assertTrue(result);
        verify(userRepository).save(any(UserModel.class));
    }

    @Test
    @DisplayName("hasRole returns true for matching role")
    void hasRoleReturnsTrueForMatchingRole() {
        AuthPrincipal authPrincipal = AuthPrincipal.builder()
                .username("user")
                .role(UserRoles.ADMIN)
                .build();

        Boolean result = authenticationService.hasRole(UserRoles.ADMIN, authPrincipal);

        assertTrue(result);
    }

    @Test
    @DisplayName("hasRole returns false for non-matching role")
    void hasRoleReturnsFalseForNonMatchingRole() {
        AuthPrincipal authPrincipal = AuthPrincipal.builder()
                .username("user")
                .role(UserRoles.USER)
                .build();

        Boolean result = authenticationService.hasRole(UserRoles.ADMIN, authPrincipal);

        assertFalse(result);
    }

    @Test
    @DisplayName("hasRole returns false for null authPrincipal")
    void hasRoleReturnsFalseForNullAuthPrincipal() {
        Boolean result = authenticationService.hasRole(UserRoles.ADMIN, (AuthPrincipal) null);

        assertFalse(result);
    }
}