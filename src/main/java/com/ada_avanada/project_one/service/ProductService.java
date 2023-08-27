package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.DecrementStockDTO;
import com.ada_avanada.project_one.dto.ProductDTO;
import com.ada_avanada.project_one.dto.ProductEditDTO;
import com.ada_avanada.project_one.dto.SearchDTO;
import com.ada_avanada.project_one.entity.Product;
import com.ada_avanada.project_one.exception.AppException;
import com.ada_avanada.project_one.repository.ProductFilterRepository;
import com.ada_avanada.project_one.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ProductFilterRepository filterRepository;
    public ProductService(ProductRepository productRepository, ProductFilterRepository filterRepository) {
        this.productRepository = productRepository;
        this.filterRepository = filterRepository;
    }

    @Transactional
    public ProductDTO create(ProductDTO dto) {
        var checkProductExists = this.productRepository.findByTitleAndBrandAndCategory(dto.title(), dto.brand(), dto.category());
        if (checkProductExists.isPresent()) {
            throw new AppException("Product already exist");
        }
        var newProduct = new Product(dto);
        var productCreated = this.productRepository.save(newProduct);
        return productCreated.dto();
    }

    public List<ProductDTO> getAll(SearchDTO searchDTO) {
        return this.filterRepository.filter(searchDTO).stream().map(Product::dto).toList();
    }

    public ProductDTO getOne(Long id) {
        var productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("Product not found.");
        }
        return productOp.get().dto();
    }

    @Transactional
    public ProductDTO edit(Long id, ProductEditDTO dto) {
        var productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("Product not found.");
        }
        productOp.get().edit(dto);
        productRepository.save(productOp.get());
        return productOp.get().dto();
    }

    @Transactional
    public void remove(Long id) {
        this.productRepository.deleteById(id);
    }

}
