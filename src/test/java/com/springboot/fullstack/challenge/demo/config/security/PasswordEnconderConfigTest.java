package com.springboot.fullstack.challenge.demo.config.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEnconderConfigTest {

    private final PasswordEnconderConfig passwordEnconderConfig = new PasswordEnconderConfig();

    @Test
    @DisplayName("passwordEncoder bean is not null")
    void passwordEncoderBeanIsNotNull() {
        PasswordEncoder passwordEncoder = passwordEnconderConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    @DisplayName("passwordEncoder bean encodes password correctly")
    void passwordEncoderBeanEncodesPasswordCorrectly() {
        PasswordEncoder passwordEncoder = passwordEnconderConfig.passwordEncoder();
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}