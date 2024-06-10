package com.epsi.MSPR.service.imp;

import com.epsi.MSPR.model.Product;

import com.epsi.MSPR.repository.ProductRepository;
import com.epsi.MSPR.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        System.out.println("Retrieved products: " + productRepository.findAll());

        return productRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        System.out.println("je suis dans le service");
        System.out.println("l'id est :" + id);
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        product.setId(id); // Assure que l'ID est correctement défini pour la mise à jour
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return false;
    }

    @Override
    @Transactional()
    public void validateProduct(Product product) {

    }
}

