package com.ada_avanada.project_one.config;

import com.ada_avanada.project_one.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthenticationFilter authenticationFilter;
    public SecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.requestMatchers(new AntPathRequestMatcher("/auth", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user", HttpMethod.GET.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/user/**", HttpMethod.DELETE.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/product/**", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/product", HttpMethod.POST.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/product/**", HttpMethod.PUT.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/product/**", HttpMethod.DELETE.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/order", HttpMethod.GET.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/order/**", HttpMethod.DELETE.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .anyRequest().authenticated())
                .headers(header -> header.frameOptions(frame -> frame.disable()))
                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        try {
            return authConfig.getAuthenticationManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
