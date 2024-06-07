package com.epsi.MSPR;

import com.epsi.MSPR.controller.ProductController;
import com.epsi.MSPR.model.Product;
import com.epsi.MSPR.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", 10.0, "Description1"),
                new Product(2L, "Product2", 20.0, "Description2")
        );

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product(1L, "Product1", 10.0, "Description1");

        when(productService.getProductById(anyLong())).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(null, "Product1", 10.0, "Description1");
        Product createdProduct = new Product(1L, "Product1", 10.0, "Description1");

        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(1L, "Product1", 10.0, "Description1");
        Product updatedProduct = new Product(1L, "UpdatedProduct", 15.0, "UpdatedDescription");

        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(1L, product);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    public void testDeleteProduct() {
        when(productService.deleteProduct(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
