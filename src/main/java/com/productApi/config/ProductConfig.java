package com.productApi.config;

import com.productApi.Product;
import com.productApi.ProductRepository;
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
                    1L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    "product1",
                    "details1",
                    73253
            );

            Product product2 = new Product(
                    2L,
                    LocalDate.of(2023, Month.DECEMBER, 2),
                    "product2",
                    "details2",
                    73253
            );

            Product product3 = new Product(
                    3L,
                    LocalDate.of(2023, Month.DECEMBER, 3),
                    "product3",
                    "details3",
                    73253
            );

            // Save Clients into Database
            repository.saveAll(
                    List.of(product1, product2, product3)
            );
        };
    }

    // Configuration de RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
