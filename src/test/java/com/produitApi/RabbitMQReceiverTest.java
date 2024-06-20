package com.produitApi;

import com.productApi.config.RabbitMQReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RabbitMQReceiverTest {

    @InjectMocks
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage() {
        String message = "{\"id\":1,\"name\":\"Product1\"}";
        rabbitMQReceiver.getReceivedMessage(message);
        assertEquals(message, rabbitMQReceiver.getReceivedMessage(message));
    }
}
