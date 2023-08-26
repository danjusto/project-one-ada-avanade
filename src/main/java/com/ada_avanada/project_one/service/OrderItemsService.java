package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.ItemsProductDTO;
import com.ada_avanada.project_one.dto.OrderItemsDTO;
import com.ada_avanada.project_one.entity.Order;
import com.ada_avanada.project_one.entity.OrderItems;
import com.ada_avanada.project_one.repository.OrderItemsRepository;
import com.ada_avanada.project_one.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsService {
    private ProductRepository productRepository;
    private OrderItemsRepository orderItemsRepository;
    public OrderItemsService(ProductRepository productRepository, OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ItemsProductDTO create(OrderItemsDTO dto, Order order) {
        var productOp = this.productRepository.findById(dto.productId());
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        return new ItemsProductDTO(order, productOp.get(), dto.qty());
    }
}
