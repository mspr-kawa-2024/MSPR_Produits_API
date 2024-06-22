package com.productApi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class RabbitMQSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendProductToOrder() {
        String product = "Sample product JSON";

        rabbitMQSender.sendProductToOrder(product);

        verify(rabbitTemplate, times(1)).convertAndSend("productToSendQueue", product);
    }

    @Test
    public void testSendResponseOfIdsVerification() {
        String response = "Sample response JSON";

        rabbitMQSender.sendResponseOfIdsVerification(response);

        verify(rabbitTemplate, times(1)).convertAndSend("responseProductIdsVerificationQueue", response);
    }
}
