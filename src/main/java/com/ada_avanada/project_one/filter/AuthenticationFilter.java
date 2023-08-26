package com.ada_avanada.project_one.filter;

import com.ada_avanada.project_one.repository.UserRepository;
import com.ada_avanada.project_one.service.AuthenticationService;
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
public class AuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private AuthenticationService authService;

    public AuthenticationFilter(JwtService jwtService, AuthenticationService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        if (this.jwtService.validateToken(token)) {
            var subject = this.jwtService.getSubject(token);
            var user = this.authService.loadUserByUsername(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            return authHeader.replace("Bearer", "").trim();
        }
        return null;
    }
}
