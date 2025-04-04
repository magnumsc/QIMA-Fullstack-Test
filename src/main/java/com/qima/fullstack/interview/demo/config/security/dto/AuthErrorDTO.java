package com.qima.fullstack.interview.demo.config.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthErrorDTO {
    private final String error;
    private final String message;
}
