package com.epsi.MSPR.controller;

import com.epsi.MSPR.model.Product;
import com.epsi.MSPR.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.debug("Getting all products");
        try {
            List<Product> products = productService.getAllProducts();
            logger.debug("Products retrieved: {}", products);
            if (products.isEmpty()) {
                logger.debug("No products found");
            } else {
                for (Product product : products) {
                    logger.debug("Product: {}", product);
                }
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error getting all products", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.debug("Getting product with ID: {}", id);
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error getting product by ID", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        logger.debug("Creating product: {}", product);
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(201).body(createdProduct);
        } catch (Exception e) {
            logger.error("Error creating product", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        logger.debug("Updating product with ID: {}", id);
        try {
            Optional<Product> updatedProduct = Optional.ofNullable(productService.updateProduct(id, product));
            return updatedProduct.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error updating product", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.debug("Deleting product with ID: {}", id);
        try {
            if (productService.deleteProduct(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error deleting product", e);
            return ResponseEntity.status(500).build();
        }
    }
}

