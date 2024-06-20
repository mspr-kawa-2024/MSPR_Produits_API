package com.produitApi;

<<<<<<< HEAD
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
=======
import com.productApi.ProductApplication;
import com.productApi.controller.ProductController;
import com.productApi.model.Product;
import com.productApi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts() throws Exception {
        Product product1 = new Product(1L, "Product1", 100);
        Product product2 = new Product(2L, "Product2", 200);
        given(productService.getProducts()).willReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(productService).getProducts();
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product(1L, "Product1", 100);
        given(productService.getProductById(1L)).willReturn(product);

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product1"));

        verify(productService).getProductById(1L);
    }

    @Test
    void registerNewProduct() throws Exception {
        Product product = new Product(1L, "Product1", 100);
        doNothing().when(productService).addNewProduct(any(Product.class));

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\",\"price\":100}"))
                .andExpect(status().isOk());

        verify(productService).addNewProduct(any(Product.class));
    }

    @Test
    void updateProduct() throws Exception {
        doNothing().when(productService).updateProduct(anyLong(), any(String.class));

        mockMvc.perform(put("/api/v1/product/1?name=UpdatedName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).updateProduct(1L, "UpdatedName");
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
>>>>>>> anthony
    }
}
