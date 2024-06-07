package com.epsi.MSPR.service.imp;

import com.epsi.MSPR.model.Product;

import com.epsi.MSPR.repository.ProductRepository;
import com.epsi.MSPR.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        System.out.println("Retrieved products: " + productRepository.findAll());

        return productRepository.findAll();

    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        product.setId(id); // Assure que l'ID est correctement défini pour la mise à jour
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return false;
    }

    @Override
    public void validateProduct(Product product) {

    }
}

