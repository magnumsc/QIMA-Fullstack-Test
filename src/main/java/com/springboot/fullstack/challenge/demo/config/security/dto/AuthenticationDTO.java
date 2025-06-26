package com.springboot.fullstack.challenge.demo.config.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {
    private String guid;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserRoles role;

    public AuthenticationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
