package com.produitApi;

import com.productApi.Product;
import com.productApi.ProductController;
import com.productApi.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    void getProduct_ReturnsListOfProducts() {
        // Arrange
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getProducts()).thenReturn(products);

        // Act
        List<Product> result = productController.getProducts();

        // Assert
        assertEquals(products.size(), result.size());
    }

    @Test
    void registerNewProduct_ProductIsAdded() {
        // Arrange
        Product product = new Product();

        // Act
        productController.registerNewProduct(product);

        // Assert
        verify(productService, times(1)).addNewProduct(product);
    }

    @Test
    void deleteProductTest() {
        // Arrange
        Long productId = 1L;

        // Act
        productController.deleteProduct(productId);

        // Assert
        verify(productService, times(1)).deleteProduct(productId);
    }
}
