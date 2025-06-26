package com.springboot.fullstack.challenge.demo.config.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadRequestDTO {
    private String message;
    private List<String> errors;

    public BadRequestDTO(String message) {
        this.message = message;
    }
}
