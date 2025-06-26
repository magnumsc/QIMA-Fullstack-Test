package com.springboot.fullstack.challenge.demo.config.exception;

import com.springboot.fullstack.challenge.demo.config.dto.BadRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicExceptionHandlerTest {

    @Test
    @DisplayName("handleMethodArgumentNotValid returns BadRequestDTO with validation errors")
    void handleMethodArgumentNotValidReturnsBadRequestDTOWithValidationErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        WebRequest request = mock(WebRequest.class);
        FieldError fieldError = new FieldError("objectName", "field", "rejectedValue", false, null, null, "defaultMessage");
        when(ex.getFieldErrors()).thenReturn(List.of(fieldError));

        BasicExceptionHandler handler = new BasicExceptionHandler();
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        BadRequestDTO responseBody = (BadRequestDTO) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed for request object", responseBody.getMessage());
        assertEquals(1, responseBody.getErrors().size());
        assertEquals("field: Value 'rejectedValue' rejected. defaultMessage", responseBody.getErrors().get(0));
    }

    @Test
    @DisplayName("handleMethodArgumentNotValid returns empty errors list when no field errors")
    void handleMethodArgumentNotValidReturnsEmptyErrorsListWhenNoFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        WebRequest request = mock(WebRequest.class);
        when(ex.getFieldErrors()).thenReturn(List.of());

        BasicExceptionHandler handler = new BasicExceptionHandler();
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        BadRequestDTO responseBody = (BadRequestDTO) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed for request object", responseBody.getMessage());
        assertEquals(0, responseBody.getErrors().size());
    }
}