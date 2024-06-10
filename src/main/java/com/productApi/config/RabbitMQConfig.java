package com.productApi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    public static final String PRODUCT_QUEUE_TO_SEND = "productToSendQueue";
    public static final String PRODUCT_QUEUE = "orderProductQueue";

    @Bean
    public Queue productToSendQueue() {
        return new Queue(PRODUCT_QUEUE_TO_SEND, false);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE, false);
    }
}
