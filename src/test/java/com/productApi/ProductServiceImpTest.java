package com.productApi;



import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import com.productApi.service.imp.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Créer des données de test
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Product 1", 10.0, "miam miam"));
        productList.add(new Product(2L, "Product 2", 20.0, "miam miam"));

        // Définir le comportement du mock
        when(productRepository.findAll()).thenReturn(productList);

        // Appeler la méthode à tester
        List<Product> result = productService.getAllProducts();

        // Vérifier le résultat
        assertEquals(2, result.size());
    }

    @Test
    void testGetProductById() {
        // Créer des données de test
        Product product = new Product(1L, "Product 1", 10.0, "miam miam");

        // Définir le comportement du mock
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Appeler la méthode à tester
        Optional<Product> result = productService.getProductById(1L);

        // Vérifier le résultat
        assertEquals(product, result.get());
    }

    // Écrire des tests similaires pour les autres méthodes (createProduct, updateProduct, deleteProduct)
}

