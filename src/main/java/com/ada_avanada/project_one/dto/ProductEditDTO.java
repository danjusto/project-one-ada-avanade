package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record ProductEditDTO(

        String title,
        String description,
        @Min(1)
        BigInteger price,
        @Min(1)
        Integer stock,
        String brand,
        String category) {
}
