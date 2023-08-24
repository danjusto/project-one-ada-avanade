package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.AddressRequestDTO;
import com.ada_avanada.project_one.dto.AddressResponseDTO;
import com.ada_avanada.project_one.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService service;
    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDTO create(@PathVariable Long userId, @RequestBody AddressRequestDTO body) {
        return service.create(userId, body);
    }

    @GetMapping("/{id}")
    public AddressResponseDTO getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @PutMapping("/{id}")
    public AddressResponseDTO edit(@PathVariable Long id, @RequestBody AddressRequestDTO body) {
        return service.edit(id, body);
    }
}