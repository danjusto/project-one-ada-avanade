package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.LoginDTO;
import com.ada_avanada.project_one.dto.TokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    public TokenDTO login(LoginDTO dto) throws AuthenticationException {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        authManager.authenticate(authToken);
        var token = jwtService.generateToken(dto.username());
        return new TokenDTO("Bearer", token);
    }
}
