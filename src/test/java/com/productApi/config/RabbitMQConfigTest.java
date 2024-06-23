package com.productApi.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RabbitMQConfigTest {

    private final RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();

    @Test
    public void testProductsIdQueue() {
        Queue queue = rabbitMQConfig.productsIdQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productsIdQueue", queue.getName(), "Queue name should be 'productsIdQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testProductToSendQueue() {
        Queue queue = rabbitMQConfig.productToSendQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productToSendQueue", queue.getName(), "Queue name should be 'productToSendQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testProductIdsIdQueue() {
        Queue queue = rabbitMQConfig.productIdsIdQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productIdsToProductQueue", queue.getName(), "Queue name should be 'productIdsToProductQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testResponseProductIdsVerificationQueue() {
        Queue queue = rabbitMQConfig.responseProductIdsVerificationQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("responseProductIdsVerificationQueue", queue.getName(), "Queue name should be 'responseProductIdsVerificationQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }
}
