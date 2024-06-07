package com.epsi.MSPR.service;

import com.epsi.MSPR.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    boolean deleteProduct(Long id);

    void validateProduct(Product product);
}
