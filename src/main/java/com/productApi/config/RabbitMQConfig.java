package com.productApi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue productsIdQueue() {
        return new Queue("productsIdQueue", false);
    }
    @Bean
    public Queue productToSendQueue() {
        return new Queue("productToSendQueue", false);
    }

    @Bean
    public Queue productIdsIdQueue() {
        return new Queue("productIdsToProductQueue", false);
    }

    @Bean
    public Queue responseProductIdsVerificationQueue() {
        return new Queue("responseProductIdsVerificationQueue", false);
    }
}
