package com.springboot.fullstack.challenge.demo.config.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRoles {
    USER("USER", 1),
    ADMIN("ADMIN", 2);

    private final String role;
    private final Integer id;

    public static UserRoles fromId(Integer id) {
        return Arrays.stream(UserRoles.values())
                .filter(role -> role.getId().equals(id))
                .findFirst()
                .orElse(UserRoles.USER);
    }
}
