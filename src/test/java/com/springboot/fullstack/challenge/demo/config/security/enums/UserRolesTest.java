package com.springboot.fullstack.challenge.demo.config.security.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserRolesTest {

    @Test
    @DisplayName("fromId returns correct role for valid id")
    void fromIdReturnsCorrectRoleForValidId() {
        assertEquals(UserRoles.ADMIN, UserRoles.fromId(2));
        assertEquals(UserRoles.USER, UserRoles.fromId(1));
    }

    @Test
    @DisplayName("fromId returns USER for invalid id")
    void fromIdReturnsUserForInvalidId() {
        assertEquals(UserRoles.USER, UserRoles.fromId(999));
    }

    @Test
    @DisplayName("getRole returns correct role name")
    void getRoleReturnsCorrectRoleName() {
        assertEquals("ADMIN", UserRoles.ADMIN.getRole());
        assertEquals("USER", UserRoles.USER.getRole());
    }

    @Test
    @DisplayName("getId returns correct id")
    void getIdReturnsCorrectId() {
        assertEquals(2, UserRoles.ADMIN.getId());
        assertEquals(1, UserRoles.USER.getId());
    }
}