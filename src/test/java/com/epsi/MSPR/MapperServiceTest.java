package com.epsi.MSPR;

import com.epsi.MSPR.dto.ProductDTO;
import com.epsi.MSPR.mapper.MapperService;
import com.epsi.MSPR.model.Product;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapperServiceTest {

    @Test
    void testConvertToDTO() {
        // Créer un produit de test
        Product product = new Product(1L, "Test Product", 10.0, "miam miam");

        // Créer un mock de ModelMapper
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(new ProductDTO(1L, "Test Product", 10.0, "miam miam"));

        // Créer un MapperService avec le mock de ModelMapper
        MapperService mapperService = new MapperService(modelMapper);

        // Appeler la méthode à tester
        ProductDTO productDTO = mapperService.convertToDTO(product);

        // Vérifier si le DTO converti est correct
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getPrice(), productDTO.getPrice());
    }

    @Test
    void testConvertToEntity() {
        // Créer un DTO de test
        ProductDTO productDTO = new ProductDTO(1L, "Test Product", 10.0, "miam miam");

        // Créer un mock de ModelMapper
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(new Product(1L, "Test Product", 10.0, "miam miam"));

        // Créer un MapperService avec le mock de ModelMapper
        MapperService mapperService = new MapperService(modelMapper);

        // Appeler la méthode à tester
        Product product = mapperService.convertToEntity(productDTO);

        // Vérifier si l'entité convertie est correcte
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getPrice(), product.getPrice());
    }
}

