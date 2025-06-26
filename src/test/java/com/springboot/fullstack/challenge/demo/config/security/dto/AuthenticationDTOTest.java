package com.springboot.fullstack.challenge.demo.config.security.dto;

import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AuthenticationDTOTest {

    @Test
    @DisplayName("constructorWithUsernameAndPassword sets username and password correctly")
    void constructorWithUsernameAndPasswordSetsUsernameAndPasswordCorrectly() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("user", "pass");

        assertEquals("user", authenticationDTO.getUsername());
        assertEquals("pass", authenticationDTO.getPassword());
    }

    @Test
    @DisplayName("defaultConstructor sets all fields to null")
    void defaultConstructorSetsAllFieldsToNull() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();

        assertNull(authenticationDTO.getGuid());
        assertNull(authenticationDTO.getUsername());
        assertNull(authenticationDTO.getPassword());
        assertNull(authenticationDTO.getRole());
    }

    @Test
    @DisplayName("setGuid sets guid correctly")
    void setGuidSetsGuidCorrectly() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setGuid("1234");

        assertEquals("1234", authenticationDTO.getGuid());
    }

    @Test
    @DisplayName("setUsername sets username correctly")
    void setUsernameSetsUsernameCorrectly() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername("user");

        assertEquals("user", authenticationDTO.getUsername());
    }

    @Test
    @DisplayName("setPassword sets password correctly")
    void setPasswordSetsPasswordCorrectly() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setPassword("pass");

        assertEquals("pass", authenticationDTO.getPassword());
    }

    @Test
    @DisplayName("setRole sets role correctly")
    void setRoleSetsRoleCorrectly() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setRole(UserRoles.ADMIN);

        assertEquals(UserRoles.ADMIN, authenticationDTO.getRole());
    }
}