package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.ItemsProductDTO;
import com.ada_avanada.project_one.dto.OrderDTO;
import com.ada_avanada.project_one.entity.Order;
import com.ada_avanada.project_one.repository.OrderRepository;
import com.ada_avanada.project_one.repository.ProductRepository;
import com.ada_avanada.project_one.repository.UserRepository;
import com.ada_avanada.project_one.util.Mail;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderItemsService orderItemsService;
    private Mail mail;
    public OrderService(Mail mail, OrderRepository orderRepository, UserRepository userRepository, OrderItemsService orderItemsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemsService = orderItemsService;
        this.mail = mail;
    }

    @Transactional
    public OrderDTO create(OrderDTO dto) {
        var userOp = this.userRepository.findById(dto.userId());
        if (userOp.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var order = new Order(dto, userOp.get());
        var itemsList = new ArrayList<ItemsProductDTO>();
        for (var element : dto.orderItems()) {
            var item = this.orderItemsService.create(element, order);
            itemsList.add(item);
        }
        order.setOrderItems(itemsList);
        var createdOrder = this.orderRepository.save(order);
        this.mail.sendEmail(createdOrder.getUser().getEmail(), createdOrder.getUser().getName(), createdOrder.toString());
        this.mail.sendEmail("testador.email.sender@gmail.com", "Sales Department", createdOrder.toString());
        return createdOrder.dto();
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
