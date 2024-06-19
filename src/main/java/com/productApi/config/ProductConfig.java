package com.productApi.config;

import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner productLineRunner(ProductRepository repository)  {
        // Have access to our repository
        return args -> {
            Product product1 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    "product1",
                    "details1",
                    73253
            );

            Product product2 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 2),
                    "product2",
                    "details2",
                    111
            );

            Product product3 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product3",
                    "details3",
                    456
            );

            Product product4 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product4",
                    "details4",
                    73253
            );

            Product product5 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product5",
                    "details5",
                    345
            );

            Product product6 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product6",
                    "details6",
                    954
            );

            Product product7 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product7",
                    "details7",
                    123
            );

            Product product8 = new Product(
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product8",
                    "details8",
                    3434
            );

            // Save Clients into Database
            repository.saveAll(
                    List.of(product1, product2, product3, product4, product5, product6, product7, product8)
            );
        };
    }

    // Configuration de RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
