package com.epsi.MSPR;


import com.epsi.MSPR.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epsi.MSPR.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.epsi.MSPR.repository.ProductRepository;
import com.epsi.MSPR.service.imp.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetAllProducts() {
        // Définir le comportement du mock
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        // Appeler la méthode à tester
        List<Product> result = productService.getAllProducts();

        // Vérifier le résultat
        assertEquals(productList, result);
    }

    @Test
    void testGetProductById() {
        // Créer des données de test
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 10.0, "miam miam");

        // Définir le comportement du mock
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Appeler la méthode à tester
        Optional<Product> result = productService.getProductById(productId);

        // Vérifier le résultat
        assertEquals(Optional.of(product), result);
    }

    // Écrire des tests similaires pour les autres méthodes de l'interface ProductService
}
