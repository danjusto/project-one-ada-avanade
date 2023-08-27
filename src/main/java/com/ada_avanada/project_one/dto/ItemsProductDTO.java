package com.ada_avanada.project_one.dto;

import com.ada_avanada.project_one.entity.Order;
import com.ada_avanada.project_one.entity.Product;

import java.math.BigInteger;

public record ItemsProductDTO(Order order, Product product, Integer qty) {
}
