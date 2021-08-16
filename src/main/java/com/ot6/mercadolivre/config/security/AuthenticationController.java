package com.ot6.mercadolivre.config.security;

import com.ot6.mercadolivre.config.security.dtos.LoginRequest;
import com.ot6.mercadolivre.config.security.dtos.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    @Transactional
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken loginCredentials = loginRequest.toLoginCredentials();

        try {
            Authentication auth = authManager.authenticate(loginCredentials);
            String token = tokenService.generateToken(auth);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
