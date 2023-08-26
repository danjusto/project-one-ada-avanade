package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DecrementStockDTO(@NotNull @Min(1) Long qty) {
}
