package com.productApi.controller;


import com.productApi.service.ProductService;
import com.productApi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
@CrossOrigin(origins = "http://localhost:3002", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        System.out.println("Received request to get product by id: " + productId);
        Optional<Product> product = Optional.ofNullable(productService.getProductById(productId));
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long producttId,
                             @RequestParam(required = false) String name) {
        productService.updateProduct(producttId, name);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

}
