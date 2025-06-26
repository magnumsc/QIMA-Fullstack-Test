package com.springboot.fullstack.challenge.demo.config.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@authenticationService.hasRole(T(com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles).ADMIN, authentication.principal)")
public @interface IsAdmin {
    // Annotation used to validate if user have the "Admin" role
}