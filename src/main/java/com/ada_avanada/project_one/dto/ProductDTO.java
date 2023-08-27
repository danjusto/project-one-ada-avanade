package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record ProductDTO(
        Long id,
        @NotBlank
        String title,
        String description,
        @NotNull
        @Min(1)
        BigInteger price,
        @NotNull
        @Min(1)
        Integer stock,
        String brand,
        @NotBlank
        String category) {
}
