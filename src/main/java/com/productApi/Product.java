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
    private String orderId;

    public Product() {
    }

    public Product(LocalDate creationDate,
                  String name,
                  String details,
                  int stock,
                  String orderId) {
        this.creationDate= creationDate;
        this.name = name;
        this.details = details;
        this.stock = stock;
        this.orderId = orderId;
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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getOrderId() {
        return orderId;
    }

    /*
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", fristName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }*/
}

