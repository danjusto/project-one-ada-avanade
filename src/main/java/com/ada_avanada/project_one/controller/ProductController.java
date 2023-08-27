package com.ada_avanada.project_one.controller;

import com.ada_avanada.project_one.dto.DecrementStockDTO;
import com.ada_avanada.project_one.dto.ProductDTO;
import com.ada_avanada.project_one.dto.ProductEditDTO;
import com.ada_avanada.project_one.dto.SearchDTO;
import com.ada_avanada.project_one.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody @Valid ProductDTO body) {
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

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    public ProductDTO edit(@PathVariable Long id, @RequestBody @Valid ProductEditDTO body) {
        return productService.edit(id, body);
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        productService.remove(id);
    }
}
