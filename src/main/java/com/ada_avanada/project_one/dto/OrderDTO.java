package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        @NotNull
        Long userId,
        LocalDateTime orderDate,
        @NotNull
        @Min(1)
        Long totalPrice,
        @NotEmpty
        List<OrderItemsDTO> orderItems) {
}
