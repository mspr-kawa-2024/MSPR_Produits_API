package com.productApi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductTest {

    @Test
    public void testNoArgsConstructor() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Product product = new Product(1L, date, "Product Name", "Product Details", 100);

        assertEquals(1L, product.getId());
        assertEquals(date, product.getCreationDate());
        assertEquals("Product Name", product.getName());
        assertEquals("Product Details", product.getDetails());
        assertEquals(100, product.getStock());
    }

    @Test
    public void testPartialArgsConstructor() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Product product = new Product(date, "Product Name", "Product Details", 100);

        assertEquals(date, product.getCreationDate());
        assertEquals("Product Name", product.getName());
        assertEquals("Product Details", product.getDetails());
        assertEquals(100, product.getStock());
    }

    @Test
    public void testSettersAndGetters() {
        Product product = new Product();
        product.setId(1L);
        LocalDate date = LocalDate.of(2023, 1, 1);
        product.setCreationDate(date);
        product.setName("Product Name");
        product.setDetails("Product Details");
        product.setStock(100);

        assertEquals(1L, product.getId());
        assertEquals(date, product.getCreationDate());
        assertEquals("Product Name", product.getName());
        assertEquals("Product Details", product.getDetails());
        assertEquals(100, product.getStock());
    }
}
