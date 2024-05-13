package com.epsi.MSPR;

import com.epsi.MSPR.dto.ProductDTO;
import com.epsi.MSPR.model.Product;
import com.epsi.MSPR.service.ProductService;
import com.epsi.MSPR.mapper.MapperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private MapperService mapperService;



    @Test
    void testGetAllProducts() throws Exception {
        // Créer des produits de test
        Product product1 = new Product(1L, "Product 1", 10.0, "miam");
        Product product2 = new Product(2L, "Product 2", 20.0, "miam");
        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        ;
    }

    @Test
    void testGetProductById() throws Exception {
        // Créer un produit de test
        Product product = new Product(1L, "Test Product", 10.0, "miam");
        when(productService.getProductById(1L)).thenReturn(java.util.Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        ;
    }

    @Test
    void testCreateProduct() throws Exception {
        // Créer un DTO de test
        ProductDTO productDTO = new ProductDTO(null, "Test Product", 10.0, "miam");

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Product\", \"price\": 10.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    void testUpdateProduct() throws Exception {
        // Créer un produit de test
        Product product = new Product(1L, "Test Product", 10.0, "miam");
        when(productService.getProductById(1L)).thenReturn(java.util.Optional.of(product));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Product\", \"price\": 20.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        // Ajouter d'autres vérifications si nécessaire
        ;
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent())
        // Ajouter d'autres vérifications si nécessaire
        ;
    }
}

