package com.ada_avanada.project_one.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 8, message = "The password must have at least 8 characters")
        String password,
        @CPF
        String cpf,
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$")
        String phone,
        @NotNull
        @Valid
        AddressRequestDTO address) {
}
