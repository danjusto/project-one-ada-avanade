package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.OrderItemsDTO;
import com.ada_avanada.project_one.entity.OrderItems;
import com.ada_avanada.project_one.repository.OrderItemsRepository;
import com.ada_avanada.project_one.repository.OrderRepository;
import com.ada_avanada.project_one.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderItemsRepository orderItemsRepository;
    public OrderItemsService(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void create(OrderItemsDTO dto) {
        var productOp = this.productRepository.findById(dto.productId());
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        var order = this.orderRepository.getReferenceById(dto.orderId());
        var orderItems = new OrderItems(dto, productOp.get(), order);
        orderItemsRepository.save(orderItems);
    }
}
