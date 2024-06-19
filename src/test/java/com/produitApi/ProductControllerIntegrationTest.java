package com.produitApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.ProductApplication;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductApplication.class)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void getProducts() throws Exception {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        productRepository.save(product);

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Product1")));
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        mockMvc.perform(get("/api/v1/product/{id}", savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product1")));
    }

    @Test
    void registerNewProduct() throws Exception {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        String jsonProduct = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isOk());

        Optional<Product> optionalProduct = productRepository.findByName("Product1");
        assertTrue(optionalProduct.isPresent());
        assertEquals("Product1", optionalProduct.get().getName());
    }

    @Test
    void updateProduct() throws Exception {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        mockMvc.perform(put("/api/v1/product/{id}", savedProduct.getId())
                        .param("name", "UpdatedName"))
                .andExpect(status().isOk());

        Optional<Product> optionalProduct = productRepository.findById(savedProduct.getId());
        assertTrue(optionalProduct.isPresent());
        assertEquals("UpdatedName", optionalProduct.get().getName());
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        mockMvc.perform(delete("/api/v1/product/{id}", savedProduct.getId()))
                .andExpect(status().isOk());

        Optional<Product> optionalProduct = productRepository.findById(savedProduct.getId());
        assertFalse(optionalProduct.isPresent());
    }
}
