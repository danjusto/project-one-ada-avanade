package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.*;
import com.ada_avanada.project_one.entity.Product;
import com.ada_avanada.project_one.exception.AppException;
import com.ada_avanada.project_one.repository.ProductFilterRepository;
import com.ada_avanada.project_one.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {
    public static final long ID = 1L;
    public static final String TITLE = "Produto One";
    public static final String DESCRIPTION = "Descrição do Produto One";
    public static final BigInteger PRICE = BigInteger.valueOf(1000);
    public static final int STOCK = 20;
    public static final String BRAND = "Marca One";
    public static final String CATEGORY = "Categoria One";
    public static final String TITLE_EDIT = "Produto Two";
    public static final BigInteger PRICE_EDIT = BigInteger.valueOf(2000);
    public static final int STOCK_EDIT = 40;
    public static final String CATEGORY_EDIT = "Categoria Two";
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductFilterRepository filterRepository;
    private Product product;
    private Optional<Product> productOptional;
    private ProductDTO dto;
    private ProductEditDTO editDTO;
    private Product productEdit;
    private SearchDTO searchOne;
    private SearchDTO searchTwo;
    private SearchDTO searchThree;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProductTester();
    }

    @Test
    @DisplayName("When create Then return a created product")
    void create() {
        when(productRepository.findByTitleAndBrandAndCategory(any(), any(), any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(product);

        var response = service.create(dto);

        assertNotNull(response);
        assertEquals(ProductDTO.class, response.getClass());
        assertEquals(TITLE, response.title());
        assertEquals(PRICE, response.price());
        assertEquals(CATEGORY, response.category());
    }

    @Test
    @DisplayName("When create with existing combination of data Then return a app exception")
    void createFail() {
        when(productRepository.findByTitleAndBrandAndCategory(any(), any(), any())).thenReturn(productOptional);
        try {
            service.create(dto);
        } catch (Exception e) {
            assertEquals(AppException.class, e.getClass());
            assertEquals("Product already exist.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When get all Then return as list of products")
    void getAll() {
        when(filterRepository.filter(any())).thenReturn(List.of(product));
        var response = service.getAll(searchOne);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ProductDTO.class, response.get(0).getClass());
        assertEquals(TITLE, response.get(0).title());
        assertEquals(BRAND, response.get(0).brand());
        assertEquals(PRICE, response.get(0).price());
    }

    @Test
    @DisplayName("When get one Then return an productDTO instance")
    void getOne() {
        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        var response = service.getOne(ID);

        assertNotNull(response);
        assertEquals(ProductDTO.class, response.getClass());
        assertEquals(TITLE, response.title());
        assertEquals(STOCK, response.stock());
    }

    @Test
    @DisplayName("When get one Then return an productDTO instance")
    void getOneFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            service.getOne(ID);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals("Product not found.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When update Then return an updated product")
    void edit() {
        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        when(productRepository.save(any())).thenReturn(productEdit);

        var response = service.edit(ID, editDTO);

        assertNotNull(response);
        assertEquals(ProductDTO.class, response.getClass());
        assertEquals(TITLE_EDIT, response.title());
        assertEquals(PRICE_EDIT, response.price());
        assertEquals(BRAND, response.brand());
        assertEquals(CATEGORY_EDIT, response.category());
    }

    @Test
    @DisplayName("When update with inexist id Then return an entity not found exception")
    void editFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            service.edit(ID, editDTO);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals("Product not found.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When delete Then return success")
    void remove() {
        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        doNothing().when(productRepository).deleteById(anyLong());
        service.remove(ID);
        verify(productRepository, times(1)).deleteById(anyLong());
    }

    private void startProductTester() {
        this.dto = new ProductDTO(ID, TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY);
        this.product = new Product(this.dto);
        this.productOptional = Optional.of(this.product);
        this.editDTO = new ProductEditDTO(TITLE_EDIT, null, PRICE_EDIT, STOCK_EDIT, null, CATEGORY_EDIT);
        this.productEdit = new Product(new ProductDTO(ID, TITLE_EDIT, DESCRIPTION, PRICE_EDIT, STOCK_EDIT, BRAND, CATEGORY_EDIT));
        this.searchOne = new SearchDTO(null, null, null);
        this.searchTwo = new SearchDTO(null, BRAND, null);
        this.searchThree = new SearchDTO("Prod", null, CATEGORY);
    }
}