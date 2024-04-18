package com.pinguinera.provider.controller;

import com.pinguinera.provider.model.dto.authentication.request.AuthenticationRequest;
import com.pinguinera.provider.model.dto.authentication.request.RegisterRequest;
import com.pinguinera.provider.model.dto.authentication.response.AuthenticationResponse;
import com.pinguinera.provider.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authenticationRequest));
    }
}
