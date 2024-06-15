package com.produitApi;

import com.productApi.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {



    @Test
    void testToString() {

        Product product = new Product();
        product.setId(1L);
        product.setName("John");
        product.setDetails("Doe");
        LocalDate currentDate = LocalDate.now();
        product.setCreationDate(currentDate);
        product.setStock(1234);

        // Création de l'objet Client avec les valeurs attendues
        Product expectedProduct = new Product(currentDate, "John", "Dos", 1234);

        // Assert
        assertEquals(expectedProduct.toString(), product.toString());
    }
}
