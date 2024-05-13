package com.epsi.MSPR.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private double price;

    private String description;
}
