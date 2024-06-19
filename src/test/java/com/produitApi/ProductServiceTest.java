package com.produitApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.config.RabbitMQSender;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import com.productApi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
        productService.setRabbitMQSender(rabbitMQSender);
        productService.setObjectMapper(objectMapper);
    }

    @Test
    void testGetProducts() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.getProducts();
        assertEquals(1, products.size());
        assertEquals("Product1", products.get(0).getName());
    }

    @Test
    void testGetProductById() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);
        assertNotNull(foundProduct);
        assertEquals("Product1", foundProduct.getName());
    }

    @Test
    void testAddNewProduct() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());

        productService.addNewProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.updateProduct(1L, "UpdatedName");
        verify(productRepository, times(1)).save(product);
        assertEquals("UpdatedName", product.getName());
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(anyLong())).thenReturn(true);

        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testHandleProductsRequest() throws JsonProcessingException {
        Product product = new Product(LocalDate.now(), "Product1", "Details1", 10);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(objectMapper.writeValueAsString(any(Product.class))).thenReturn("{\"id\":1,\"name\":\"Product1\"}");

        productService.handleProductsRequest("1");
        verify(rabbitMQSender, times(1)).sendProductToOrder(anyString());
    }

    @Test
    void testVerificationOfProductsToAddInOrder() {
        when(productRepository.existsById(anyLong())).thenReturn(true);

        productService.verificationOfProductsToAddInOrder("1,2,3");
        verify(rabbitMQSender, times(1)).sendResponseOfIdsVerification("ok");
    }
}
