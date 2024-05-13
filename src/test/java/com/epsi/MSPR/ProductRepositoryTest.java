package com.epsi.MSPR;

import com.epsi.MSPR.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.epsi.MSPR.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        // Créer un produit de test
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);

        // Sauvegarder le produit dans la base de données
        Product savedProduct = productRepository.save(product);

        // Récupérer le produit sauvegardé de la base de données
        Product retrievedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Vérifier si le produit récupéré est égal au produit sauvegardé
        assertEquals(savedProduct, retrievedProduct);
    }

    @Test
    void testFindAllProducts() {
        // Créer des produits de test
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(20.0);

        // Sauvegarder les produits dans la base de données
        productRepository.save(product1);
        productRepository.save(product2);

        // Récupérer tous les produits de la base de données
        List<Product> productList = productRepository.findAll();

        // Vérifier si la liste des produits contient les produits sauvegardés
        assertEquals(2, productList.size());
        assertTrue(productList.contains(product1));
        assertTrue(productList.contains(product2));
    }

    @Test
    void testUpdateProduct() {
        // Créer un produit de test
        Product product = new Product();
        product.setName("Original Product");
        product.setPrice(10.0);

        // Sauvegarder le produit dans la base de données
        Product savedProduct = productRepository.save(product);

        // Mettre à jour le produit
        savedProduct.setName("Updated Product");
        savedProduct.setPrice(20.0);
        productRepository.save(savedProduct);

        // Récupérer le produit mis à jour de la base de données
        Product updatedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Vérifier si le produit récupéré est égal au produit mis à jour
        assertEquals(savedProduct, updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        // Créer un produit de test
        Product product = new Product();
        product.setName("Product to Delete");
        product.setPrice(10.0);

        // Sauvegarder le produit dans la base de données
        Product savedProduct = productRepository.save(product);

        // Supprimer le produit de la base de données
        productRepository.deleteById(savedProduct.getId());

        // Vérifier que le produit a été supprimé en essayant de le récupérer de la base de données
        boolean exists = productRepository.existsById(savedProduct.getId());
        assertFalse(exists);
    }
}

