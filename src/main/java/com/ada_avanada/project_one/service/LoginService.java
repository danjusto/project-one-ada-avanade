package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.LoginDTO;
import com.ada_avanada.project_one.dto.TokenDTO;
import com.ada_avanada.project_one.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {
    private AuthenticationManager authManager;
    private JwtService jwtService;
    public LoginService(AuthenticationManager authManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public TokenDTO login(LoginDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = authManager.authenticate(authToken);
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed.");
        }
        var token = jwtService.generateToken((User) authentication.getPrincipal());
        return new TokenDTO("Bearer", token);
    }
}
