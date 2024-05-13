package com.epsi.MSPR;

import com.epsi.MSPR.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Créer un DTO de test
        ProductDTO productDTO = new ProductDTO(1L, "Test Product", 10.0, "Test Description");

        // Vérifier si le constructeur et les getters fonctionnent correctement
        assertEquals(1L, productDTO.getId());
        assertEquals("Test Product", productDTO.getName());
        assertEquals(10.0, productDTO.getPrice());
        assertEquals("Test Description", productDTO.getDescription());
    }

    @Test
    void testNoArgsConstructor() {
        // Créer un DTO avec le constructeur par défaut
        ProductDTO productDTO = new ProductDTO(1L, "Test Product", 10.0, "Test Description");

        // Vérifier si le DTO est non nul
        assertNotNull(productDTO);
    }

}

