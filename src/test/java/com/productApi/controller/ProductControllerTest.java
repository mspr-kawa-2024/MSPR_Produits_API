package com.productApi.controller;

import com.productApi.model.Product;
import com.productApi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // Register modules for Java 8 date/time types
    }

    @Test
    public void testGetProducts() throws Exception {
        Product product1 = new Product(1L, LocalDate.now(), "Product 1", "Details 1", 10);
        Product product2 = new Product(2L, LocalDate.now(), "Product 2", "Details 2", 20);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));

        verify(productService, times(1)).getProducts();
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product(1L, LocalDate.now(), "Product 1", "Details 1", 10);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testRegisterNewProduct() throws Exception {
        Product product = new Product(1L, LocalDate.now(), "Product 1", "Details 1", 10);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        verify(productService, times(1)).addNewProduct(any(Product.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        mockMvc.perform(put("/api/v1/product/1")
                        .param("name", "Updated Product"))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProduct(1L, "Updated Product");
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/product/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
