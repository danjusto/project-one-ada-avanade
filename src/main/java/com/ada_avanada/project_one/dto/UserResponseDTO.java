package com.ada_avanada.project_one.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO(Long id, String name, String username, String cpf, String email, String phone, LocalDateTime registerDate, List<AddressResponseDTO> addresses, List<OrderDTO> orders) {
    public UserResponseDTO(Long id, String name, String username, String cpf, String email, String phone, LocalDateTime registerDate) {
        this(id, name, username, cpf, email, phone, registerDate, null, null);
    }
}
