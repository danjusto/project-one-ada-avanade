package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemsDTO(
        Long id,
        @NotNull
        Long orderId,
        @NotNull
        Long productId,
        @NotNull
        @Min(1)
        Integer qty) {}
