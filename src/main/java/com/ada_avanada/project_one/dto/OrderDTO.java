package com.ada_avanada.project_one.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id, Long userId, LocalDateTime orderDate, Long totalPrice, List<OrderItemsDTO> orderItems) {
}
