package com.productApi.service;

import com.productApi.config.RabbitMQSender;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProducts() {
        List<Product> productList = Arrays.asList(
                new Product(1L, LocalDate.now(), "Product 1", "Description 1", 10),
                new Product(2L, LocalDate.now(), "Product 2", "Description 2", 20)
        );

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> returnedProducts = productService.getProducts();

        assertEquals(2, returnedProducts.size());
        assertEquals("Product 1", returnedProducts.get(0).getName());
        assertEquals("Product 2", returnedProducts.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        String productName = "Test Product";
        Product product = new Product(productId, LocalDate.now(), productName, "Description", 15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product returnedProduct = productService.getProductById(productId);

        assertEquals(productName, returnedProduct.getName());
    }

    @Test
    public void testFindByName() {
        String productName = "Test Product";
        Product product = new Product(1L, LocalDate.now(), productName, null, 0);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        Optional<Product> foundProductOptional = productService.findByName(productName);

        assertTrue(foundProductOptional.isPresent());
        assertEquals(productName, foundProductOptional.get().getName());
    }

    @Test
    public void testAddNewProduct() {
        Product newProduct = new Product(null, LocalDate.now(), "New Product", "Description", 25);

        when(productRepository.findByName(newProduct.getName())).thenReturn(Optional.empty());

        productService.addNewProduct(newProduct);

        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        String newName = "Updated Product";
        Product existingProduct = new Product(productId, LocalDate.now(), "Old Product", "Description", 30);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, newName);

        assertEquals(newName, existingProduct.getName());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        Product existingProduct = new Product(productId, LocalDate.now(), "Product to delete", "Description", 40);

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testConvertirEnLongs() {
        String input = "1,2,3,5";

        List<Long> expected = new ArrayList<>();
        expected.add(1L);
        expected.add(2L);
        expected.add(3L);
        expected.add(5L);

        List<Long> result = ProductService.convertirEnLongs(input);

        assertEquals(expected, result);
    }

    @Test
    public void testAddNewProduct_NameTaken() {
        Product existingProduct = new Product(1L, LocalDate.now(), "Existing Product", "Description", 25);

        when(productRepository.findByName(existingProduct.getName())).thenReturn(Optional.of(existingProduct));

        Product newProduct = new Product(null, LocalDate.now(), "Existing Product", "Description", 30);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            productService.addNewProduct(newProduct);
        });

        assertEquals("name taken", exception.getMessage());

        verify(productRepository, never()).save(any());
    }

    @Test
    public void testUpdateProductWithNameChange() {
        Long productId = 1L;
        String newName = "Updated Product";
        Product existingProduct = new Product(productId, LocalDate.now(), "Old Product", "Description", 30);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, newName);

        assertEquals(newName, existingProduct.getName());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testUpdateProductWithNoNameChange() {
        Long productId = 1L;
        String existingName = "Old Product";
        Product existingProduct = new Product(productId, LocalDate.now(), existingName, "Description", 30);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, existingName);

        assertEquals(existingName, existingProduct.getName());
        verify(productRepository, never()).save(existingProduct);
    }

    @Test
    public void testExtractProductsIds() {
        String input = "1,2,3,5";

        List<Long> expected = List.of(1L, 2L, 3L, 5L);
        List<Long> result = ProductService.extractProductsIds(input);

        assertEquals(expected, result);
    }

    @Test
    public void testConvertirEnLongs_InvalidInput() {
        String input = "1,2,3,invalid,5";

        List<Long> result = ProductService.convertirEnLongs(input);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testVerificationOfProductsToAddInOrder() {
        String productIdsReceived = "1,2,3";
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsById(2L)).thenReturn(false);
        when(productRepository.existsById(3L)).thenReturn(true);

        String result = productService.processVerificationOfProducts(productIdsReceived);

        assertEquals("2", result);
    }


}

