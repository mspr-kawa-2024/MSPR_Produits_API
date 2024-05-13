package com.epsi.MSPR;

import com.epsi.MSPR.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductTest {

    @Test
    void testConstructorAndGetters() {
        // Créer un produit de test
        Product product = new Product(1L, "Test Product", 10.0, "Test Description");

        // Vérifier si le constructeur et les getters fonctionnent correctement
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals("Test Description", product.getDescription());
    }

    @Test
    void testNoArgsConstructor() {
        // Créer un produit avec le constructeur par défaut
        Product product = new Product();

        // Vérifier si le produit est non nul
        assertNotNull(product);
    }

}

