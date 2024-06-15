package com.productApi;

import jakarta.persistence.*;

import java.time.LocalDate;

//for hibernate
@Entity
//for database
@Table
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private LocalDate creationDate;
    private String name;
    private String details;
    private int stock;

    public Product() {
    }

    public Product(LocalDate creationDate,
                  String name,
                  String details,
                  int stock) {
        this.creationDate= creationDate;
        this.name = name;
        this.details = details;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getStock() {
        return stock;
    }
}


