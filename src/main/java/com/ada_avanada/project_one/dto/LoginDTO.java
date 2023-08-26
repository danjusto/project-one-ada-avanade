package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(@NotBlank String username, @NotBlank String password) {
}
