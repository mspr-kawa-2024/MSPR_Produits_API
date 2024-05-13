package com.epsi.MSPR.controller;

import com.epsi.MSPR.model.Product;
import com.epsi.MSPR.service.ProductService;
import com.epsi.MSPR.dto.ProductDTO;
import com.epsi.MSPR.mapper.MapperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;


@Validated
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final MapperService mapperService;

    public ProductController(ProductService productService, MapperService mapperService) {
        this.productService = productService;
        this.mapperService = mapperService;
    }


    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(mapperService::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapperService.convertToDTO(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = mapperService.convertToEntity(productDTO);
        productService.validateProduct(product);
        Product createdProduct = productService.createProduct(product);
        ProductDTO createdProductDTO = mapperService.convertToDTO(createdProduct);
        return new ResponseEntity<>(createdProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product existingProduct = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Mettre à jour les propriétés du produit existant avec les données du DTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());

        productService.validateProduct(existingProduct);

        Product updatedProduct = productService.updateProduct(id, existingProduct);
        ProductDTO updatedProductDTO = mapperService.convertToDTO(updatedProduct); // Convertir Product en ProductDTO
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String errorMessage = result.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

