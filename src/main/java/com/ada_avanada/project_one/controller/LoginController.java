package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.LoginDTO;
import com.ada_avanada.project_one.dto.TokenDTO;
import com.ada_avanada.project_one.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private LoginService loginService;
    public LoginController(LoginService service) {
        this.loginService = service;
    }

    @PostMapping
    public TokenDTO login(@RequestBody LoginDTO dto) {
        return loginService.login(dto);
    }
}
