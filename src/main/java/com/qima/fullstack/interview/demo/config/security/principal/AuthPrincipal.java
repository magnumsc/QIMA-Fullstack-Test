package com.qima.fullstack.interview.demo.config.security.principal;

import com.qima.fullstack.interview.demo.config.security.enums.UserRoles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthPrincipal {
    private String guid;
    private String username;
    private UserRoles role;
}
