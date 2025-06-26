package com.springboot.fullstack.challenge.demo.config.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class BadRequestDTOTest {
    @Test
    @DisplayName("constructorWithMessageAndErrors sets message and errors correctly")
    void constructorWithMessageAndErrorsSetsMessageAndErrorsCorrectly() {
        List<String> errors = List.of("Error 1", "Error 2");
        BadRequestDTO badRequestDTO = new BadRequestDTO("Test message", errors);

        assertEquals("Test message", badRequestDTO.getMessage());
        assertEquals(errors, badRequestDTO.getErrors());
    }

    @Test
    @DisplayName("constructorWithMessage sets message correctly and errors to null")
    void constructorWithMessageSetsMessageCorrectlyAndErrorsToNull() {
        BadRequestDTO badRequestDTO = new BadRequestDTO("Test message");

        assertEquals("Test message", badRequestDTO.getMessage());
        assertNull(badRequestDTO.getErrors());
    }

    @Test
    @DisplayName("defaultConstructor sets message and errors to null")
    void defaultConstructorSetsMessageAndErrorsToNull() {
        BadRequestDTO badRequestDTO = new BadRequestDTO();

        assertNull(badRequestDTO.getMessage());
        assertNull(badRequestDTO.getErrors());
    }

    @Test
    @DisplayName("setMessage sets message correctly")
    void setMessageSetsMessageCorrectly() {
        BadRequestDTO badRequestDTO = new BadRequestDTO();
        badRequestDTO.setMessage("New message");

        assertEquals("New message", badRequestDTO.getMessage());
    }

    @Test
    @DisplayName("setErrors sets errors correctly")
    void setErrorsSetsErrorsCorrectly() {
        List<String> errors = List.of("Error 1", "Error 2");
        BadRequestDTO badRequestDTO = new BadRequestDTO();
        badRequestDTO.setErrors(errors);

        assertEquals(errors, badRequestDTO.getErrors());
    }
}
