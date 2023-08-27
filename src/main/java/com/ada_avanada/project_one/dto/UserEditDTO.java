package com.ada_avanada.project_one.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserEditDTO(String name, @Email String email, @Pattern(regexp = "^\\d{11}$") String phone) {
}
