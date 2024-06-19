package com.produitApi;

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
    }
}
