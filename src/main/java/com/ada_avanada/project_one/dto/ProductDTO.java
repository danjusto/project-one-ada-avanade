package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,
        @NotBlank
        String title,
        String description,
        @NotNull
        @Min(1)
        Long price,
        @NotNull
        @Min(1)
        Long stock,
        String brand,
        @NotBlank
        String category) {
}
