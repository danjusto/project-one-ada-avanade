package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.ItemsProductDTO;
import com.ada_avanada.project_one.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnoreProperties({"orders", "addresses"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime orderDate;
    private Long totalPrice;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<OrderItems>();

    public Order(OrderDTO dto, User user) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = dto.totalPrice();
    }

    public void setOrderItems(List<ItemsProductDTO> items) {
        for (var item : items) {
            this.orderItems.add(new OrderItems(item));
        }
    }

    public OrderDTO dto() {
        return new OrderDTO(this.id, this.user.getId(), this.orderDate, this.totalPrice, this.orderItems.stream().map(OrderItems::dto).toList());
    }

    public void edit(OrderDTO dto) {
        this.totalPrice = dto.totalPrice();
    }

    @Override
    public String toString() {
        return "Order #" + this.id + " successfully registered";
    }
}
