package com.springboot.fullstack.challenge.demo.config.security.principal;

import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthPrincipal {
    private String guid;
    private String username;
    private UserRoles role;
}
