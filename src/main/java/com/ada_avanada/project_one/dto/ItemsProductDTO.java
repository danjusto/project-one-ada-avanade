package com.ada_avanada.project_one.dto;

import com.ada_avanada.project_one.entity.Order;
import com.ada_avanada.project_one.entity.Product;

public record ItemsProductDTO(Order order, Product product, Integer qty) {
}
