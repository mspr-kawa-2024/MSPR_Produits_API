package com.epsi.MSPR.mapper;

import com.epsi.MSPR.dto.ProductDTO;
import com.epsi.MSPR.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class MapperService {

    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
