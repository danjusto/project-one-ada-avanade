package com.ada_avanada.project_one.dto;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO(Long id, String name, String username, String cpf, String email, String phone, LocalDate registerDate, List<AddressResponseDTO> addresses) {
}
