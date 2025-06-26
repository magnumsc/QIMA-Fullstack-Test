package com.springboot.fullstack.challenge.demo.config.security.service;

import com.springboot.fullstack.challenge.demo.config.security.dto.AuthenticationDTO;
import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.model.UserModel;
import com.springboot.fullstack.challenge.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Value("{auth.cookie.secret:secret-key}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationDTO authenticate(String username, String password) throws AuthenticationException {
        var user = retrieveUser(username);
        if (user.getUsername().equals(username) && passwordEncoder.matches(password, user.getPassword())) {
            return AuthenticationDTO.builder()
                    .guid(user.getGuid())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .role(UserRoles.fromId(user.getRole()))
                    .build();
        }
        throw new AuthenticationCredentialsNotFoundException("Invalid username or password");
    }

    public AuthPrincipal findByToken(String token) throws AuthenticationException {
        var tokenParts = token.split(":");
        var user = retrieveUser(tokenParts[0]);
        var userDTO = new AuthenticationDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setGuid(user.getGuid());
        if (calculateHmac(userDTO).equals(tokenParts[1])) {
            return AuthPrincipal.builder()
                    .guid(user.getGuid())
                    .username(user.getUsername())
                    .role(UserRoles.fromId(user.getRole()))
                    .build();
        }
        throw new AuthenticationCredentialsNotFoundException("Invalid token");
    }

    public String createToken(AuthenticationDTO authenticationDTO) {
        return authenticationDTO.getUsername() + ":" + calculateHmac(authenticationDTO);
    }

    private String calculateHmac(AuthenticationDTO authenticationDTO) {
        var secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        var valueBytes = Objects.requireNonNull(authenticationDTO.getUsername()).getBytes(StandardCharsets.UTF_8);
        try {
            var algorithm = "HmacSHA512";
            var mac = Mac.getInstance(algorithm);
            var sec = new SecretKeySpec(secretKeyBytes, algorithm);
            mac.init(sec);
            var hmac = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private UserModel retrieveUser(String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }
        return user;
    }

    public Boolean register(AuthenticationDTO authenticationDTO) {
        UserModel userModel = new UserModel();
        userModel.setRole(1);
        userModel.setUsername(authenticationDTO.getUsername());
        userModel.setPassword(passwordEncoder.encode(authenticationDTO.getPassword()));
        userModel = userRepository.save(userModel);
        return userModel != null;
    }

    public Boolean hasRole(UserRoles role, AuthPrincipal authPrincipal) {
        if (authPrincipal == null) {
            return false;
        }
        return role.equals(authPrincipal.getRole());
    }

    public Boolean hasRole(UserRoles role, String authPrincipal) {
        // anonymous user
        return false;
    }
}
