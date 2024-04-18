package com.pinguinera.provider.services;

import com.pinguinera.provider.model.dto.authentication.request.RegisterRequest;
import com.pinguinera.provider.model.persistence.UserEntity;
import com.pinguinera.provider.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }



    public void register(RegisterRequest registerRequest){
        var user = UserEntity.builder()
    }
}
