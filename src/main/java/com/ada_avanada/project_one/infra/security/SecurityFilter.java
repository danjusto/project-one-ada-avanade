package com.ada_avanada.project_one.infra.security;

import com.ada_avanada.project_one.repository.UserRepository;
import com.ada_avanada.project_one.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserRepository userRepository;

    public SecurityFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        if (token != null) {
            var subject = jwtService.getSubject(token);
            var user = userRepository.findByUsername(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
