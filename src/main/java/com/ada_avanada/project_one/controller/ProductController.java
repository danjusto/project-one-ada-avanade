package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.DecrementStockDTO;
import com.ada_avanada.project_one.dto.ProductDTO;
import com.ada_avanada.project_one.dto.SearchDTO;
import com.ada_avanada.project_one.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductDTO body) {
        return productService.create(body);
    }

    @GetMapping
    public List<ProductDTO> getAll(@RequestParam(required = false) SearchDTO search) {
        return productService.getAll(search);
    }

    @GetMapping("/{id}")
    public ProductDTO getOne(@PathVariable Long id) {
        return productService.getOne(id);
    }

    @PutMapping("/{id}")
    public ProductDTO edit(@PathVariable Long id, @RequestBody ProductDTO body) {
        return productService.edit(id, body);
    }

    @PatchMapping("/{id}/stock")
    public ProductDTO edit(@PathVariable Long id, @RequestBody DecrementStockDTO body) {
        return productService.decrementStock(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        productService.remove(id);
    }
}
