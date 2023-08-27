package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.DecrementStockDTO;
import com.ada_avanada.project_one.dto.ProductDTO;
import com.ada_avanada.project_one.dto.ProductEditDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigInteger price;
    private Integer stock;
    private String brand;
    private String category;

    public Product(ProductDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.price = dto.price();
        this.stock = dto.stock();
        this.brand = dto.brand();
        this.category = dto.category();
    }

    public ProductDTO dto() {
        return new ProductDTO(this.id, this.title, this.description, this.price, this.stock, this.brand, this.category);
    }
    public void edit(ProductEditDTO dto) {
        if (dto.title() != null) {
            this.title = dto.title();
        }
        if (dto.description() != null) {
            this.description = dto.description();
        }
        if (dto.price() != null) {
            this.price = dto.price();
        }
        if (dto.stock() != null) {
            this.stock = dto.stock();
        }
        if (dto.brand() != null) {
            this.brand = dto.brand();
        }
        if (dto.category() != null) {
            this.category = dto.category();
        }
    }
    public void decrementStock(Integer qty) {
        this.stock -= qty;
    }
}
