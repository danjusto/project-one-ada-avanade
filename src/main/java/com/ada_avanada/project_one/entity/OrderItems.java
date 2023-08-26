package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.OrderItemsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Getter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer qty;

    public OrderItems(OrderItemsDTO dto, Product product, Order order) {
        this.qty = dto.qty();
        this.product = product;
        this.order = order;
    }

    public OrderItemsDTO dto() {
        return new OrderItemsDTO(this.id, this.order.getId(), this.product.getId(), this.qty);
    }
}
