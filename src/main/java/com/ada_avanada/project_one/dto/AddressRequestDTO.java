package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequestDTO(
        Long userId,
        String street,
        @NotBlank
        String number,
        @NotBlank
        @Pattern(regexp = "^\\d{8}$")
        String postalCode,
        String city,
        String state,
        String country) {}
