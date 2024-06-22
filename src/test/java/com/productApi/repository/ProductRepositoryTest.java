package com.productApi.repository;

import com.productApi.model.Product;
import com.productApi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductRepositoryTest {

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
        Product product = new Product(1L, null, productName, null, 0);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        Optional<Product> foundProductOptional = productService.findByName(productName);

        assertEquals(product, foundProductOptional.orElse(null));
        verify(productRepository, times(1)).findByName(productName);
    }
}
