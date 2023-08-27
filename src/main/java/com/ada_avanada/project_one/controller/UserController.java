package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.UserEditDTO;
import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@RequestBody @Valid UserRequestDTO body) {
        return service.create(body);
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @PatchMapping("/{id}")
    public UserResponseDTO edit(@PathVariable Long id, @RequestBody @Valid UserEditDTO body) {
        return service.edit(id, body);
    }
}
