package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.OrderDTO;
import com.ada_avanada.project_one.entity.Order;
import com.ada_avanada.project_one.repository.OrderRepository;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderItemsService orderItemsService;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderItemsService orderItemsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemsService = orderItemsService;
    }

    @Transactional
    public OrderDTO create(OrderDTO dto) {
        var userOp = this.userRepository.findById(dto.userId());
        if (userOp.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var order = new Order(dto, userOp.get());
        var orderCreated = this.orderRepository.save(order);
        return orderCreated.dto();
    }

    public OrderDTO getOne(Long id) {
        var orderOp = this.orderRepository.findById(id);
        if(orderOp.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        return orderOp.get().dto();
    }

    public List<OrderDTO> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc().stream().map(Order::dto).toList();
    }

    @Transactional
    public void remove(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDTO edit(Long id, OrderDTO dto) {
        var orderOp = this.orderRepository.findById(id);
        if(orderOp.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        orderOp.get().edit(dto);
        this.orderRepository.save(orderOp.get());
        return orderOp.get().dto();
    }
}