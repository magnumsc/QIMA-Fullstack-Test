package com.springboot.fullstack.challenge.demo.config.exception;

import com.springboot.fullstack.challenge.demo.config.dto.BadRequestDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BasicExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var responseDTO = new BadRequestDTO();
        responseDTO.setMessage("Validation failed for request object");
        responseDTO.setErrors(new ArrayList<>());
        for (FieldError error : ex.getFieldErrors()) {
            responseDTO.getErrors().add(String.format(
                    "%s: Value '%s' rejected. %s",
                    error.getField(),
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        }
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(httpStatus)
                .headers(newHeaders)
                .body(responseDTO);
    }
}
