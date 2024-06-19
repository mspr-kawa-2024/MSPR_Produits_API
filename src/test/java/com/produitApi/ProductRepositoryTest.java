package com.produitApi;

import com.productApi.ProductApplication;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = ProductApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testSaveProduct() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Product1", savedProduct.getName());
    }

    @Test
    void testFindById() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId());
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Product1", retrievedProduct.get().getName());
    }

    @Test
    void testFindByName() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        productRepository.save(product);

        Optional<Product> retrievedProduct = productRepository.findByName("Product1");
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Product1", retrievedProduct.get().getName());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId());
        assertFalse(retrievedProduct.isPresent());
    }
}
