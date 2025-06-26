package com.springboot.fullstack.challenge.demo.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Mock
    private ServletOutputStream servletOutputStream;

    @InjectMocks
    private UserAuthenticationEntryPoint entryPoint;

    @Test
    @DisplayName("commence sets response status to 401 and content type to application/json")
    void commenceSetsResponseStatusTo401AndContentTypeToApplicationJson() throws IOException, ServletException {
        when(response.getOutputStream()).thenReturn(servletOutputStream);
        entryPoint.commence(request, response, authException);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("commence writes AuthErrorDTO to response output stream")
    void commenceWritesAuthErrorDTOToResponseOutputStream() throws IOException, ServletException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        entryPoint.commence(request, response, authException);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeader(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getContentAsString().contains("User not authorized"));
    }
}