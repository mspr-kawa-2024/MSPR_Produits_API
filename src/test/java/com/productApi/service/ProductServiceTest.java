package com.productApi.service;

import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByName() {
        String productName = "Test Product";
        Product product = new Product(null, productName, null, 0);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        Optional<Product> foundProductOptional = productService.findByName(productName);

        // Assert that the product is present in the Optional returned by findByName
        assertEquals(product, foundProductOptional.orElse(null));
    }
}
