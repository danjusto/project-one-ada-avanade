package com.ada_avanada.project_one.dto;

public record UserRequestDTO(Long id, String name, String username, String password, String cpf, String email, String phone, AddressRequestDTO address) {
}