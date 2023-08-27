package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.OrderDTO;
import com.ada_avanada.project_one.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody @Valid OrderDTO body) {
        return orderService.create(body);
    }

    @GetMapping("/{id}")
    public OrderDTO getOne(@PathVariable Long id) {
        return orderService.getOne(id);
    }

    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        orderService.remove(id);
    }
}
